package pl.borowa5b.cdq_recruitment_task.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openAPI() {
        final var server = new Server();
        server.url("/");
        return new OpenAPI()
                .info(new Info()
                        .title("CDQ Recruitment Task")
                        .description("Author: Mateusz Borowski <<borowa5b@gmail.com>>")
                        .version("1.0.0"))
                .servers(List.of(server));
    }

    @Bean
    public GroupedOpenApi allGroup() {
        return GroupedOpenApi.builder()
                .group("All endpoints")
                .packagesToScan("pl.borowa5b.cdq_recruitment_task")
                .build();
    }

    @Bean
    public GroupedOpenApi peopleGroup() {
        return GroupedOpenApi.builder()
                .group("People endpoints")
                .packagesToScan("pl.borowa5b.cdq_recruitment_task.application.endpoint.people")
                .build();
    }

    @Bean
    public GroupedOpenApi tasksGroup() {
        return GroupedOpenApi.builder()
                .group("Tasks endpoints")
                .packagesToScan("pl.borowa5b.cdq_recruitment_task.application.endpoint.tasks")
                .build();
    }
}
