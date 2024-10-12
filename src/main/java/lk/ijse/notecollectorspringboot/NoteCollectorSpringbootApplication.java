package lk.ijse.notecollectorspringboot;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/* api hadapu nathi beans application context eke register karanna one nm api danne mekata. */

@SpringBootApplication /* @EnableAutoConfiguration, @Component, @Configuration */
public class NoteCollectorSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(NoteCollectorSpringbootApplication.class, args);
    }
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
