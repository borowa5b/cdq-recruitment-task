package pl.borowa5b.cdq_recruitment_task.infrastructure.configuration;

import com.fasterxml.jackson.core.StreamReadFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.jackson.ProblemModule;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .registerModules(
                        new JavaTimeModule(),
                        new ProblemModule()
                )
                .enable(StreamReadFeature.INCLUDE_SOURCE_IN_LOCATION.mappedFeature())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }
}
