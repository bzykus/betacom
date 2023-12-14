package pl.com.betacom.task.configs;

import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI myOpenAPI() {
        Contact contact = new Contact();
        contact.setEmail("mosito91@gmail.com");
        contact.setName("Karol Komar");
        contact.setUrl("https://www.linkedin.com/in/karol-komar/");


        Info info = new Info()
                .title("Recruitment task API")
                .version("1.0")
                .contact(contact)
                .description("This API exposes endpoints to 'Items'");

        return new OpenAPI().info(info);
    }
}