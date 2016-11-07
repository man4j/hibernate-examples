package javacenter.hibernate.example.service;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javacenter.hibernate.example.config.DbConfig;
import javacenter.hibernate.example.model.Item;
import javacenter.hibernate.example.model.Menu;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DbConfig.class})
public class MenuServiceTest {
    @Autowired
    private MenuService menuService;
    
    @Autowired
    private ItemService itemService;
    
    @Test
    public void shouldWork2() {
        Menu menu = new Menu();
        
        menu.setName(UUID.randomUUID().toString().substring(0, 5));
        
        menuService.persist(menu);
        
        Item item = new Item();
        item.setContent("content");
        item.setMenu(menu);
        
        itemService.persist(item);
    }
    
    @Test
    public void shouldWork3() {
        //доступные FETCH стратегии: SELECT, SUBSELECT (JOIN не имеет смысла при работе с диапазонами)
        List<Menu> menus = menuService.getAllMenus1();
    }
    
    @Test
    public void shouldWorkWithJoin() {
        //доступные FETCH стратегии: SELECT, JOIN
        //SUBSELECT делает тоже самое, что и SELECT
        Menu menu = menuService.getMenu(1);
    }
}
