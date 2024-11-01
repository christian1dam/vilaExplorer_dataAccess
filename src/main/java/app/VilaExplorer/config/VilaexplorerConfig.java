package app.VilaExplorer.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

public class VilaexplorerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info().title("Vila Explorer API")
                        .description("API REST")
                        .contact(new Contact()
                                .name("Christian")
                                .email("christianrmch@outlook.es")
                                .url("https://datos.codeandcoke.com"))
                        .version("1.0"));
    }
}
