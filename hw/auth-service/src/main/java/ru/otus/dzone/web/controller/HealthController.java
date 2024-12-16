package ru.otus.dzone.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.dzone.web.dto.GetHealthResponse;

@RestController
public class HealthController {
    private static final String GET_HEALTH = "/health";
    private static final String GET_ERROR = "/error";

    @GetMapping(GET_HEALTH)
    public GetHealthResponse getHealth(){
        return new GetHealthResponse("OK");
    }

    @GetMapping(GET_ERROR)
    public GetHealthResponse getError(){
        throw new IllegalStateException("test error");
    }

}
