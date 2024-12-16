package ru.otus.mailservice.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.mailservice.service.MailService;
import ru.otus.mailservice.web.dto.UserMailsResponse;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;

    @GetMapping
    public UserMailsResponse getUserMails(HttpServletRequest request) {
        return UserMailsResponse.fromDomain(mailService.getUserMails(request));
    }
}
