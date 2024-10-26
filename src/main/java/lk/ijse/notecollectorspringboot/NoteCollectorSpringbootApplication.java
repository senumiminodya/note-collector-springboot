package lk.ijse.notecollectorspringboot;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

/* api hadapu nathi beans application context eke register karanna one nm api danne mekata. */

@SpringBootApplication /* @EnableAutoConfiguration, @Component, @Configuration */
@EnableWebSecurity
@EnableMethodSecurity
public class NoteCollectorSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteCollectorSpringbootApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
