package javacenter.hibernate.example.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javacenter.hibernate.example.model.Menu;

@Service
public class MenuService {
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void persist(Menu menu) {
        em.persist(menu);
    }
    
    /**
    FetchMode.SELECT:
      
    select
        menu0_.id as id1_1_,
        menu0_.name as name2_1_ 
    from
        menus menu0_

    select
        items0_.menu_id as menu_id3_0_0_,
        items0_.id as id1_0_0_,
        items0_.id as id1_0_1_,
        items0_.content as content2_0_1_,
        items0_.menu_id as menu_id3_0_1_ 
    from
        items items0_ 
    where
        items0_.menu_id=? 
        
        ...etc...
    =================================================================
    FetchMode.SUBSELECT:
     
    select
        menu0_.id as id1_1_,
        menu0_.name as name2_1_ 
    from
        menus menu0_
        
    select
        items0_.menu_id as menu_id3_0_1_,
        items0_.id as id1_0_1_,
        items0_.id as id1_0_0_,
        items0_.content as content2_0_0_,
        items0_.menu_id as menu_id3_0_0_ 
    from
        items items0_ 
    where
        items0_.menu_id in (
            select
                menu0_.id 
            from
                menus menu0_
        )
     */
    @Transactional(readOnly = true)
    public List<Menu> getAllMenus1() {
        return em.createQuery("SELECT m FROM Menu m", Menu.class).getResultList();
    }
    
    @Transactional(readOnly = true)
    public Menu getMenu(int id) {
        return em.find(Menu.class, id);
    }
}
