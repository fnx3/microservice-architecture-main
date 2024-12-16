package ru.otus.dzone.web.dto;

public class GetHealthResponse {
    private String status;

    public GetHealthResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
