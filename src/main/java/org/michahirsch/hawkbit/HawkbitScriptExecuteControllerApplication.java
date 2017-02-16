package org.michahirsch.hawkbit;

import java.io.IOException;

import org.michahirsch.hawkbit.ddi.api.DdiClientInterface;
import org.michahirsch.hawkbit.ddi.client.DdiClient;
import org.michahirsch.hawkbit.ddi.client.DdiClientProperties;
import org.michahirsch.hawkbit.ddi.client.DownloadPersistenceStrategy;
import org.michahirsch.hawkbit.ddi.client.FilePersistenceStrategy;
import org.michahirsch.hawkbit.ddi.client.script.ConfigurableScriptExecutorService;
import org.michahirsch.hawkbit.ddi.client.script.ScriptExecutorProperties;
import org.michahirsch.hawkbit.ddi.client.script.ScriptExecutorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.hal.Jackson2HalModule;

import com.google.common.collect.Lists;

import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

@SpringBootApplication
@EnableConfigurationProperties({ DdiClientProperties.class, ScriptExecutorProperties.class })
public class HawkbitScriptExecuteControllerApplication {

    public static void main(final String[] args) throws IOException {
        SpringApplication.run(HawkbitScriptExecuteControllerApplication.class, args);
        
    }

    @Bean
    DdiClientRunOnStartupApplicationListener ddiClientRunOnStartupApplicationListener(final DdiClient ddiClient) {
        return new DdiClientRunOnStartupApplicationListener(ddiClient);
    }

    @Bean
    DdiClientInterface ddiClientInterface(final DdiClientProperties ddiClientProperties) {
        return Feign.builder().logLevel(Level.FULL).decoder(new JacksonDecoder(Lists.newArrayList(new Jackson2HalModule()))).encoder(new JacksonEncoder())
                .target(DdiClientInterface.class, ddiClientProperties.getBaseUrl());
    }

    @Bean
    DownloadPersistenceStrategy downloadPersistenceStrategy() {
        return new FilePersistenceStrategy();
    }

    @Bean
    ScriptExecutorService scriptExecutorService(final ScriptExecutorProperties scriptExecutorProperties) {
        return new ConfigurableScriptExecutorService(scriptExecutorProperties);
    }

    @Bean
    DdiClient ddiClient(final DdiClientProperties clientProperties, final DdiClientInterface clientInterface,
            final DownloadPersistenceStrategy downloadPersistenceStrategy,
            final ScriptExecutorService scriptExecutorService) {
        return new DdiClient(clientProperties, clientInterface, downloadPersistenceStrategy, scriptExecutorService);
    }
}
