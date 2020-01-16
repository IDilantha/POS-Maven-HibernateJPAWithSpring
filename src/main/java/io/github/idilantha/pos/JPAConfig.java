package io.github.idilantha.pos;

import lk.ijse.dep.crypto.DEPCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;


@Configuration
@EnableTransactionManagement
@PropertySource("file:${user.dir}/src/main/resources/application.properties")
public class JPAConfig {

    @Autowired
    private Environment env;

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, JpaVendorAdapter jpaVendorAdapter){
        LocalContainerEntityManagerFactoryBean lcemfb = new LocalContainerEntityManagerFactoryBean();
        lcemfb.setDataSource(dataSource);
        lcemfb.setJpaVendorAdapter(jpaVendorAdapter);
        lcemfb.setPackagesToScan("io.github.idilantha.pos.entity");
        return lcemfb;
    }

    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("javax.persistence.jdbc.driver"));
        ds.setUsername(DEPCrypt.decode(env.getRequiredProperty("javax.persistence.jdbc.user"),"123"));
        ds.setPassword(DEPCrypt.decode(env.getRequiredProperty("javax.persistence.jdbc.password"),"123"));
        ds.setUrl(env.getRequiredProperty("javax.persistence.jdbc.url"));
        return ds;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        HibernateJpaVendorAdapter hjva = new HibernateJpaVendorAdapter();
        hjva.setDatabase(Database.MYSQL);
        hjva.setDatabasePlatform(env.getRequiredProperty("hibernate.dialect"));
        hjva.setGenerateDdl(env.getRequiredProperty("hibernate.show_sql",Boolean.class));
        hjva.setShowSql(env.getRequiredProperty("hibernate.hbm2ddl.auto").equals("update"));
        return hjva;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManager){
        return new JpaTransactionManager(entityManager);
    }


}
