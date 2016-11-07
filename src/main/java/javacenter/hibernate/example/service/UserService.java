package javacenter.hibernate.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javacenter.hibernate.example.model.User;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void persist(User user) {
        em.persist(user);
    }
    
    /**
    select
        user0_.id as id1_1_,
        user0_.name as name2_1_ 
    from
        users user0_
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers1() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }
    
    /**
    select
        user0_.id as id1_1_0_,
        posts1_.id as id1_0_1_,
        user0_.name as name2_1_0_,
        posts1_.content as content2_0_1_,
        posts1_.user_id as user_id3_0_1_,
        posts1_.user_id as user_id3_0_0__,
        posts1_.id as id1_0_0__ 
    from
        users user0_ 
    inner join
        posts posts1_ 
            on user0_.id=posts1_.user_id
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers2() {
        return em.createQuery("SELECT u FROM User u JOIN FETCH u.posts", User.class).getResultList();
    }
    
    /**
    FetchMode.SELECT:
    
    select
        user0_.id as id1_1_,
        user0_.name as name2_1_ 
    from
        users user0_

    select
        posts0_.user_id as user_id3_0_0_,
        posts0_.id as id1_0_0_,
        posts0_.id as id1_0_1_,
        posts0_.content as content2_0_1_,
        posts0_.user_id as user_id3_0_1_ 
    from
        posts posts0_ 
    where
        posts0_.user_id=?
        
        ...etc...
    ================================================
    
    FetchMode.SUBSELECT:

    select
        user0_.id as id1_3_,
        user0_.name as name2_3_ 
    from
        users user0_

    select
        posts0_.user_id as user_id3_2_1_,
        posts0_.id as id1_2_1_,
        posts0_.id as id1_2_0_,
        posts0_.content as content2_2_0_,
        posts0_.user_id as user_id3_2_0_ 
    from
        posts posts0_ 
    where
        posts0_.user_id in (
            select
                user0_.id 
            from
                users user0_
        )
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers3() {
        List<User> users = em.createQuery("SELECT u FROM User u", User.class).getResultList();
        
        //Доступные FETCH стратегии: SELECT, SUBSELECT (JOIN не имеет смысла при работе с диапазонами)
        for (User u : users) {
            u.getPosts().size();
        }
        
        return users;
    }
    
    @Transactional(readOnly = true)
    public User getUser(int id) {
        User u = em.find(User.class, id);

        //Доступные FETCH стратегии: SELECT 
        //(SUBSELECT ведет себя также, как и SELECT) 
        //(JOIN отменяет ленивость)
        u.getPosts().size();
        
        return u;
    }
    
    /**
    select
        user0_.id as id1_1_,
        user0_.name as name2_1_ 
    from
        users user0_ 
    inner join
        posts posts1_ 
            on user0_.id=posts1_.user_id
            
     Join вроде делается, но данные в модель не попадают
     */
    @Transactional(readOnly = true)
    public List<User> getAllUsers4() {
        return em.createQuery("SELECT u FROM User u JOIN u.posts", User.class).getResultList();
    }
}
