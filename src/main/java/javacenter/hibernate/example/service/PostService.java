package javacenter.hibernate.example.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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
}
