package javacenter.hibernate.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javacenter.hibernate.example.model.Post;

@Service
public class PostService {
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void persist(Post post) {
        em.persist(post);
    }
    
    /**
    FetchMode.JOIN:(отменяет FetchType.LAZY)
    
    FetchType.LAZY меняет поведение FetchMode по умолчанию на FetchMode.SELECT
    
    select
        post0_.id as id1_2_0_,
        post0_.content as content2_2_0_,
        post0_.user_id as user_id3_2_0_,
        user1_.id as id1_3_1_,
        user1_.name as name2_3_1_ 
    from
        posts post0_ 
    left outer join
        users user1_ 
            on post0_.user_id=user1_.id 
    where
        post0_.id=?
        
    ==================================================
        
    FetchMode.SELECT:
    
    select
        post0_.id as id1_2_0_,
        post0_.content as content2_2_0_,
        post0_.user_id as user_id3_2_0_ 
    from
        posts post0_ 
    where
        post0_.id=?

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?
     */
    @Transactional(readOnly = true)
    public Post getById(int id) {
        Post post = em.find(Post.class, id);
        
        post.getUser().getName();

        return post;
    }
    
    /**
    select
        post0_.id as id1_2_,
        post0_.content as content2_2_,
        post0_.user_id as user_id3_2_ 
    from
        posts post0_

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?
     
     */
    @Transactional(readOnly = true)
    public List<Post> getAll() {//Hibernate ignores the FetchMode.JOIN annotation when you use the Query interface 
        return em.createQuery("SELECT p FROM Post p", Post.class).getResultList();
    }
    
    /**
    select
        post0_.id as id1_2_,
        post0_.content as content2_2_,
        post0_.user_id as user_id3_2_ 
    from
        posts post0_

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?

    select
        user0_.id as id1_3_0_,
        user0_.name as name2_3_0_ 
    from
        users user0_ 
    where
        user0_.id=?
     
     */
    @Transactional(readOnly = true)
    public List<Post> getAll2() {//Hibernate ignores the FetchMode.JOIN annotation when you use the Query interface 
        CriteriaQuery<Post> query = em.getCriteriaBuilder().createQuery(Post.class);
        Root<Post> root = query.from(Post.class);
        query.select(root);
        
        return em.createQuery(query).getResultList();
    }
    
    /**
    select
        post0_.id as id1_2_0_,
        user1_.id as id1_3_1_,
        post0_.content as content2_2_0_,
        post0_.user_id as user_id3_2_0_,
        user1_.name as name2_3_1_ 
    from
        posts post0_ 
    left outer join
        users user1_ 
            on post0_.user_id=user1_.id
     */
    @Transactional(readOnly = true)
    public List<Post> getAll3() {
        return em.createQuery("SELECT p FROM Post p LEFT JOIN FETCH p.user", Post.class).getResultList();
    }
}
