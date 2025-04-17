package pl.borowa5b.cdq_recruitment_task.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "auditingDateTimeProvider")
public class MongoConfiguration {

    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of(
                new OffsetDateTimeReadConverter(),
                new OffsetDateTimeWriteConverter()
        ));
    }

    @Bean
    public DateTimeProvider auditingDateTimeProvider() {
        return () -> Optional.of(OffsetDateTime.now(ZoneOffset.UTC));
    }

    static class OffsetDateTimeWriteConverter implements Converter<OffsetDateTime, String> {
        @Override
        public String convert(final OffsetDateTime source) {
            return source.toInstant().atZone(ZoneOffset.UTC).toString();
        }
    }

    static class OffsetDateTimeReadConverter implements Converter<String, OffsetDateTime> {
        @Override
        public OffsetDateTime convert(final String source) {
            return OffsetDateTime.parse(source);
        }
    }
}
