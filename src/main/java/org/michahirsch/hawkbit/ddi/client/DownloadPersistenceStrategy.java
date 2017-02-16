package org.michahirsch.hawkbit.ddi.client;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public interface DownloadPersistenceStrategy {

    File handleInputStream(InputStream downloadStream, String filename) throws IOException;
    
}
