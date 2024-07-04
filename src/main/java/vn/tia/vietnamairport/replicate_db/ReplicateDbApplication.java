package vn.tia.vietnamairport.replicate_db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"vn.tia.vietnamairport.replicate_db.repository","vn.tia.vietnamairport.replicate_db.config","vn.tia.vietnamairport.replicate_db.entity.primary"
,"vn.tia.vietnamairport.replicate_db.entity.secondary","vn.tia.vietnamairport.replicate_db.writer","vn.tia.vietnamairport.replicate_db.processor"
		,"vn.tia.vietnamairport.replicate_db.service","vn.tia.vietnamairport.replicate_db.service.imp"
})
//@EnableJpaRepositories(basePackages = "vn.tia.vietnamairport.replicate_db.repository") // them vao enable jpa repository
public class ReplicateDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReplicateDbApplication.class, args);
	}

}
