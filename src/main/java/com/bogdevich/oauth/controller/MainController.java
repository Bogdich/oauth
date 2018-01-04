package com.bogdevich.oauth.controller;

import com.bogdevich.oauth.entity.dto.MessageDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<MessageDTO> getHelloWorld() {
        String message = "Hello World";
        MessageDTO messageDTO = new MessageDTO(
                LocalDateTime.now().toString(),
                HttpStatus.OK.value(),
                message
        );
        return new ResponseEntity<>(messageDTO, HttpStatus.OK);
    }
}
