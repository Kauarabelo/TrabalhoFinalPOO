package br.com.estudoskaua.trabalhofinalpoo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication()
@EnableJpaRepositories // Habilita o suporte a repositórios JPA
public class TrabalhoFinalPooApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrabalhoFinalPooApplication.class, args);
    }

}


