package io.github.idilantha.pos;

import lk.ijse.dep.crypto.DEPCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;


@Configuration
@PropertySource("")
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

    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getRequiredProperty("javax.persistence.jdbc.driver"));
        ds.setUsername(DEPCrypt.decode(env.getRequiredProperty("javax.persistence.jdbc.user"),"123"));
        ds.setPassword(DEPCrypt.decode(env.getRequiredProperty("javax.persistence.jdbc.password"),"123"));
        ds.setUrl(env.getRequiredProperty("javax.persistence.jdbc.url"));
        return ds;
    }

}
