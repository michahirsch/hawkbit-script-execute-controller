package org.michahirsch.hawkbit.ddi.api;

import feign.RequestLine;
import feign.Response;

public interface DdiClientDownloadInterface {

    @RequestLine("GET ")
    Response download();

}