package javacenter.hibernate.example.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javacenter.hibernate.example.model.Item;

@Service
public class ItemService {
    @PersistenceContext
    private EntityManager em;
    
    @Transactional
    public void persist(Item item) {
        em.persist(item);
    }
}
