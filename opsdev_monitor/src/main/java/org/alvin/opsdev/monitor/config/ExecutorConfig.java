package org.alvin.opsdev.monitor.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Created by Administrator on 2017/3/15.
 */
@Configuration
public class ExecutorConfig {

    @Bean
    public ThreadPoolTaskExecutor threadPoolTaskExecutor(@Value("${collector.pool.max}") int max, @Value("${collector.pool.max}") int core) {
        ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
        threadPoolTaskExecutor.setMaxPoolSize(max);
        threadPoolTaskExecutor.setCorePoolSize(core);
        return threadPoolTaskExecutor;
    }

}
