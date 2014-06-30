package org.esupportail.opi.web.candidat.config;

import net.sf.ehcache.CacheException;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import javax.inject.Inject;
import java.io.IOException;

@Configuration
@ImportResource({"classpath*:/META-INF/cache/cache.xml"})
public class Cache {

	@Inject
    private EhCacheManagerFactoryBean cacheManagerFactory;

    @Bean
    public org.springframework.cache.Cache mailCache() throws CacheException, IOException {
        return springCacheManager().getCache("mailCache");
    }

    private CacheManager springCacheManager() {
        return new EhCacheCacheManager(cacheManagerFactory.getObject());
    }

}
