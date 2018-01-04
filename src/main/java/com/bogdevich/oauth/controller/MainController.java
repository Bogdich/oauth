package com.bogdevich.oauth.controller;

import com.bogdevich.oauth.entity.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

/**
 * Application main REST controller.
 *
 * @author Eugene Bogdevich
 */
@RestController
@RequestMapping("/api")
public class MainController {

    @RequestMapping(
            value = "/hello",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public ResponseEntity<MessageDTO> helloUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = String.format("Hello, %s", username);
        MessageDTO messageDTO = new MessageDTO(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                message
        );
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public ResponseEntity<MessageDTO> hello() {
        String message = "Hello!";
        MessageDTO messageDTO = new MessageDTO(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                message
        );
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/test",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public ResponseEntity<MessageDTO> helloTest() {
        String message = "Hello test!";
        MessageDTO messageDTO = new MessageDTO(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                message
        );
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }

    @RequestMapping(
            value = "/admin",
            method = RequestMethod.GET,
            produces = "application/json"
    )
    public ResponseEntity<MessageDTO> helloAdmin(Principal principal) {
        String message = String.format("Hello, %s", principal.getName());
        MessageDTO messageDTO = new MessageDTO(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                message
        );
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
