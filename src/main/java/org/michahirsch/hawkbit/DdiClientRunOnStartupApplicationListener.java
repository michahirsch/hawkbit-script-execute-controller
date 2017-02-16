package org.michahirsch.hawkbit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.michahirsch.hawkbit.ddi.client.DdiClient;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;

public class DdiClientRunOnStartupApplicationListener implements ApplicationListener<ApplicationReadyEvent> {

    private final DdiClient ddiClient;
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        
    public DdiClientRunOnStartupApplicationListener(final DdiClient ddiClient) {
        this.ddiClient = ddiClient;
    }

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent arg0) {
        singleThreadExecutor.execute(ddiClient::run);
    }

}
