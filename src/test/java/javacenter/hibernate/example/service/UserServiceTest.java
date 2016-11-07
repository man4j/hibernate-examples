package javacenter.hibernate.example.service;

import java.util.List;
import java.util.UUID;

import org.hibernate.LazyInitializationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javacenter.hibernate.example.config.DbConfig;
import javacenter.hibernate.example.model.Post;
import javacenter.hibernate.example.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfig.class})
public class UserServiceTest {
    @Autowired
    private UserService userService;
    
    @Autowired
    private PostService postService;
    
    @Test
    public void shouldWork1() {
        User user = new User();
        
        user.setName(UUID.randomUUID().toString().substring(0, 5));
        
        userService.persist(user);
    }
    
    @Test
    public void shouldWork2() {
        User user = new User();
        
        user.setName(UUID.randomUUID().toString().substring(0, 5));
        
        userService.persist(user);
        
        Post post = new Post();
        post.setContent("content");
        post.setUser(user);
        
        postService.persist(post);
    }
    
    @Test(expected = LazyInitializationException.class)
    public void shouldWork3() {
        List<User> users = userService.getAllUsers1();
        
        Assert.assertTrue(!users.isEmpty());
        
        users.get(0).getPosts().size();//LazyInitializationException
    }
    
    @Test
    public void shouldWork4() {
        List<User> users = userService.getAllUsers2();
        
        Assert.assertTrue(!users.isEmpty());
        
        users.get(0).getPosts().size();
    }
    
    @Test
    public void shouldWork5() {
        List<User> users = userService.getAllUsers3();
        
        Assert.assertTrue(!users.isEmpty());
        
        users.get(0).getPosts().size();
    }
    
    @Test
    public void shouldWork51() {
        User user = userService.getUser(4);
    }
    
    @Test(expected = LazyInitializationException.class)
    public void shouldWork6() {
        List<User> users = userService.getAllUsers4();
        
        Assert.assertTrue(!users.isEmpty());
        
        users.get(0).getPosts().size();
    }
}
