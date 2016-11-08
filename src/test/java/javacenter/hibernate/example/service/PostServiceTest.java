package javacenter.hibernate.example.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javacenter.hibernate.example.config.DbConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfig.class})
public class PostServiceTest {
    @Autowired
    private PostService postService;
    
    @Test
    public void shouldWork1() {
        postService.getById(1);
    }
    
    @Test
    public void shouldWork2() {
        postService.getAll();
    }
    
    @Test
    public void shouldWork3() {
        postService.getAll2();
    }
    
    @Test
    public void shouldWork4() {
        postService.getAll3();
    }
}
