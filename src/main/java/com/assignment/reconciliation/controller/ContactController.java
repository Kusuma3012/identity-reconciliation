package com.assignment.reconciliation.controller;

import com.assignment.reconciliation.payload.IdentifyRequest;
import com.assignment.reconciliation.payload.IdentifyResponse;
import com.assignment.reconciliation.service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("identify")
    public ResponseEntity<IdentifyResponse> identify(@RequestBody IdentifyRequest request) {
        IdentifyResponse response = contactService.identify(request.getEmail(), request.getPhoneNumber());
        return ResponseEntity.ok(response);
    }
}

