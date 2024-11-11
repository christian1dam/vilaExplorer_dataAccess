package app.VilaExplorer.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        System.out.println("Swagger OpenAPI Configuration Initialized...");
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("VilaExplorer API")
                        .description("API REST para la gestión de los endPoints de VilaExplorer.\n" +
                                "Contactos:\n" +
                                " - VilaExplorerAdmin: aangelcruzmx@gmail.com\n" +
                                " - VilaExplorerAdmin: ivan01092004@gmail.com\n" +
                                " - VilaExplorerAdmin: christianmamani.dam@gmail.com")
                        .contact(new Contact()
                                .name("VilaExplorerAdmin")
                                .email("aangelcruzmx@gmail.com")
                                .url("vilaexplorer.com"))
                        .version("1.0"));
    }

}
