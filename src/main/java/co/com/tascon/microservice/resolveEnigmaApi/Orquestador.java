package co.com.tascon.microservice.resolveEnigmaApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = { "co.com.tascon.microservice.resolveEnigmaApi", "co.com.tascon.microservice.resolveEnigmaApi.api" , "co.com.tascon.microservice.resolveEnigmaApi.config"})
public class Orquestador {

	public static void main(String[] args) throws Exception {
        new SpringApplication(Orquestador.class).run(args);
    }
}
