package com.illegalaccess.cache.refresh;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;

import java.util.Properties;

public class CacheEnvPostProcessor extends ConfigFileApplicationListener {
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String hostname = environment.getProperty("spring.cloud.client.hostname");
        System.out.println("old hostname>>>>>>>>>>>>>>>>" + hostname);
        hostname = hostname.replace(".", "-");
        System.out.println("new hostname>>>>>>>>>>>>>>>>" + hostname);

        Properties cacheProp = new Properties();
        cacheProp.setProperty("cacheLifecycle.listener.group", hostname);
        PropertiesPropertySource pps = new PropertiesPropertySource("cacheLifecycleEnv", cacheProp);

        environment.getPropertySources().remove("cacheLifecycleEnv");
        environment.getPropertySources().addLast(pps);
    }

    @Override
    public int getOrder() {
        return super.getOrder() + 5;
    }
}
