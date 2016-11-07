package javacenter.hibernate.example.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@ComponentScan({"javacenter.hibernate.example"})
public class DbConfig {
    @Bean(destroyMethod = "close")
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        
        ds.setJdbcUrl("jdbc:mysql://localhost:3306/hibernate");
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUsername("root");
        ds.setMaximumPoolSize(50);
        ds.setPassword("root");

        ds.addDataSourceProperty("useSSL", "false");
        ds.addDataSourceProperty("characterEncoding", "UTF-8");
        ds.addDataSourceProperty("cachePrepStmts", "true");
        ds.addDataSourceProperty("prepStmtCacheSize", "250");
        ds.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        
        return ds;
    }
    
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
       LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
       
       em.setDataSource(dataSource());
       HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
       jpaVendorAdapter.setShowSql(true);
       
       em.setJpaPropertyMap(new HashMap<String, Object>() {{put("hibernate.id.new_generator_mappings", true);
                                                            put("hibernate.dialect", "org.hibernate.dialect.MySQL57InnoDBDialect");
                                                            put("hibernate.format_sql", true);
                                                            put("hibernate.jdbc.fetch_size", 1);
                                                            put("hibernate.jdbc.batch_size", 1000);
                                                            put("hibernate.order_inserts", true);
                                                            put("hibernate.order_updates", true);
                                                          }});
       em.setJpaVendorAdapter(jpaVendorAdapter);
       em.setPackagesToScan(packagesToScan().toArray(new String[] {}));
       
       return em;
    }
    
    @Bean
    public PlatformTransactionManager txManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }
    
    private Set<String> packagesToScan() {
        return Collections.singleton("javacenter.hibernate.example");
    }
}
