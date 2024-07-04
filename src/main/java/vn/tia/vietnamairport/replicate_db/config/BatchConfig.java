package vn.tia.vietnamairport.replicate_db.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JpaCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import vn.tia.vietnamairport.replicate_db.entity.primary.FlightEntity;
import vn.tia.vietnamairport.replicate_db.processor.FlightProcessor;
import vn.tia.vietnamairport.replicate_db.repository.FlightSecondaryRepository;
import vn.tia.vietnamairport.replicate_db.writer.FlightItemWriter;

import javax.sql.DataSource;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class BatchConfig {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    @Qualifier("primaryTransactionManager")
    PlatformTransactionManager primaryTransactionManager;

    @Autowired
    @Qualifier("primaryDataSource")
    private DataSource primaryDataSource;

    @Autowired
    @Qualifier("secondaryDataSource")
    private DataSource secondaryDataSource;

    @Autowired
    @Qualifier("primaryEntityManagerFactory")
    private EntityManagerFactory primaryEntityManagerFactory;

    @Autowired
    private FlightProcessor flightProcessor;


    @Autowired
    private FlightItemWriter flightItemWriter;

    @Bean
    public Job migrateJob(){
        return new JobBuilder("Job is to migrate data",jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(migrateStep())
                .build();
    }
    private Step migrateStep(){
        return new StepBuilder("Read, process, write Flight",jobRepository)
                //.<FlightEntity, FlightEntity>chunk(10,primaryTransactionManager)
                .<FlightEntity, vn.tia.vietnamairport.replicate_db.entity.secondary.FlightEntity>chunk(10,primaryTransactionManager)
                //.<Student, Student>chunk(10,platformTransactionManager)
                .reader(jpaReader())
                .processor(flightProcessor)
                .writer(flightItemWriter)
                //.writer(studentItemWriter)
                .build();
    }

    public JpaCursorItemReader<FlightEntity> jpaReader(){
//      Date startDate = Date.valueOf(LocalDate.now().minusDays(2));

        Date startDate = Date.valueOf("2022-03-22");
//        Date endDate =  Date.valueOf("2022-03-02");
//        Map<String, Object> parameterValues = new HashMap<>();
//        parameterValues.put("startDateKey",startDate);

        JpaCursorItemReader<FlightEntity> jpaCursorItemReader = new JpaCursorItemReader<>();

        jpaCursorItemReader.setEntityManagerFactory(primaryEntityManagerFactory);
        jpaCursorItemReader.setQueryString("From FlightEntity f where f.flightDate > :startDateKey");
        //jpaCursorItemReader.setParameterValues(parameterValues);
        jpaCursorItemReader.setParameterValues(new HashMap<String,Object>(){
            {
                put("startDateKey",startDate);
            }
        });
        //jpaCursorItemReader.setMaxItemCount(5000);
        return jpaCursorItemReader;
    }


}
