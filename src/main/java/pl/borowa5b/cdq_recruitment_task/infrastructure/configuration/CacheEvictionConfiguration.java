package pl.borowa5b.cdq_recruitment_task.infrastructure.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class CacheEvictionConfiguration {

    @Autowired
    private final CacheManager cacheManager;

    @Scheduled(fixedDelayString = "${cdq-recruitment-task.person-cache-time-in-seconds}", timeUnit = TimeUnit.SECONDS)
    public void evictCache() {
        cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
}
