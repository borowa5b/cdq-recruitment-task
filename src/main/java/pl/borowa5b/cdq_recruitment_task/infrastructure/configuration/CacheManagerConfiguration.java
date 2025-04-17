package pl.borowa5b.cdq_recruitment_task.infrastructure.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@EnableCaching
@Configuration
@Profile("!test")
public class CacheManagerConfiguration {

    @Bean
    public CacheManager cacheManager() {
        final var cacheManager = new SimpleCacheManager();
        final var postsCache = new ConcurrentMapCache("people");
        cacheManager.setCaches(List.of(postsCache));
        return cacheManager;
    }
}
