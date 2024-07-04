package vn.tia.vietnamairport.replicate_db.config;

import jakarta.persistence.EntityManagerFactory;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        //basePackages = "vn.tia.vietnamairport.replicate_db.entity.secondary",
        basePackages = "vn.tia.vietnamairport.replicate_db.repository",
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager"
)
public class SecondaryDataSourceConfig {

    @Bean
    @ConfigurationProperties(prefix = "spring.secondarydatasource")
    public DataSource secondaryDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name="secondaryEntityManagerFactory")
    public EntityManagerFactory secondaryEntityManagerFactory(){
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        localContainerEntityManagerFactoryBean.setDataSource(secondaryDataSource());
        localContainerEntityManagerFactoryBean.setPackagesToScan("vn.tia.vietnamairport.replicate_db.entity.secondary");
        localContainerEntityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        localContainerEntityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        localContainerEntityManagerFactoryBean.afterPropertiesSet();
        return localContainerEntityManagerFactoryBean.getObject();
    }

    @Bean(name="secondaryTransactionManager")
    public JpaTransactionManager secondaryTransactionManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setDataSource(secondaryDataSource());
        jpaTransactionManager.setEntityManagerFactory(secondaryEntityManagerFactory());
        return jpaTransactionManager;
    }

}
