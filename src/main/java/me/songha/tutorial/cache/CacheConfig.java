package me.songha.tutorial.cache;

import com.google.code.ssm.Cache;
import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AddressProvider;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.CacheConfiguration;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.spring.ExtendedSSMCacheManager;
import com.google.code.ssm.spring.SSMCache;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;

public class CacheConfig {

    @Value("${cache.address}")
    private String address;

    @Value("${cache.operationTimeout}")
    private String operationTimeout;

    @Value("${cache.cacheName}")
    private String cacheName;

    @Value("${cache.expiration}")
    private String expiration;

    @Bean
    public CacheManager cacheManager() {

        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setConsistentHashing(true);
        cacheConfiguration.setUseBinaryProtocol(true);
        cacheConfiguration.setOperationTimeout(Integer.getInteger(operationTimeout));
        cacheConfiguration.setUseNameAsKeyPrefix(true);
        cacheConfiguration.setKeyPrefixSeparator(":");

        MemcacheClientFactoryImpl cacheClientFactory = new MemcacheClientFactoryImpl();

        AddressProvider addressProvider = new DefaultAddressProvider(address);

        CacheFactory cacheFactory = new CacheFactory();
        cacheFactory.setCacheName(cacheName);
        cacheFactory.setCacheClientFactory(cacheClientFactory);
        cacheFactory.setAddressProvider(addressProvider);
        cacheFactory.setConfiguration(cacheConfiguration);

        Cache object = null;

        try {
            object = cacheFactory.getObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

        SSMCache ssmCache = new SSMCache(object, Integer.parseInt(expiration), true);

        ArrayList<SSMCache> ssmCaches = new ArrayList<>();
        ssmCaches.add(0, ssmCache);

        ExtendedSSMCacheManager ssmCacheManager = new ExtendedSSMCacheManager();
        ssmCacheManager.setCaches(ssmCaches);

        return ssmCacheManager;
    }
}
