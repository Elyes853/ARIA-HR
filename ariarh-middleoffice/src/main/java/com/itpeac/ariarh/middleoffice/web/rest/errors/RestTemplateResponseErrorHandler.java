package com.itpeac.ariarh.middleoffice.web.rest.errors;

import com.itpeac.ariarh.middleoffice.web.rest.ProfileController;
import com.itpeac.ariarh.middleoffice.web.rest.exception.NotFoundException;
import org.jboss.logging.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;


public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private static Logger LOGGER = LoggerFactory.getLogger(ProfileController.class);

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
        return (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR || httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        MDC.put("action", "GET_SERENSIA_FACTURE");
        if (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.SERVER_ERROR) {

            LOGGER.error("Impossible de recuperer Token Serensia;SERVER_ERROR " + httpResponse
                    .getStatusCode());
            //Handle SERVER_ERROR
            throw new HttpClientErrorException(httpResponse.getStatusCode());
        } else if (httpResponse
                .getStatusCode()
                .series() == HttpStatus.Series.CLIENT_ERROR) {
            LOGGER.error("Impossible de recuperer Token Serensia;CLIENT_ERROR " + httpResponse
                    .getStatusCode());

            //Handle CLIENT_ERROR
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new NotFoundException();
            }
        }

    }

}
