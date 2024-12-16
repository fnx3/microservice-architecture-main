package ru.otus.userapp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import ru.otus.common.dto.CreateWalletRequest;
import ru.otus.common.dto.CreateWalletResponse;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.net.URI;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;
    private final Validator validator;
    @Value("${billing.url}")
    private String billingServiceUrl;

    public CreateWalletResponse createUserWallet(String userId) {
        return postForObject(billingServiceUrl, new CreateWalletRequest(userId), CreateWalletResponse.class);
    }

    private <P, R> R postForObject(String url, P request, Class<R> responseType) {

        HttpEntity<P> httpEntity = new HttpEntity<>(request);
        URI uri = URI.create(url);

        R response = postForObject(uri, httpEntity, responseType);

        Set<ConstraintViolation<Object>> violations = validator.validate(response);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return response;
    }

    private <P, R> R postForObject(URI uri, HttpEntity<P> httpEntity, Class<R> responseType) {
        try {
            ResponseEntity<R> result = restTemplate.postForEntity(uri, httpEntity, responseType);
            return result.getBody();
        } catch (RestClientException e) {
            log.error("Request ({}) error: {}", uri, e.getLocalizedMessage());
            throw e;
        }
    }
}
