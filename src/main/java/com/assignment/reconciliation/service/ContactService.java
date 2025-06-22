package com.assignment.reconciliation.service;

import com.assignment.reconciliation.entity.Contact;
import com.assignment.reconciliation.model.LinkPrecedence;
import com.assignment.reconciliation.payload.IdentifyResponse;
import com.assignment.reconciliation.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public IdentifyResponse identify(String email, String phoneNumber) {
        List<Contact> matchingContacts = contactRepository.findByEmailOrPhoneNumber(email, phoneNumber);

        if (matchingContacts.isEmpty()) {
            Contact primary = new Contact();
            primary.setEmail(email);
            primary.setPhoneNumber(phoneNumber);
            primary.setLinkPrecedence(LinkPrecedence.PRIMARY);
            contactRepository.save(primary);

            return new IdentifyResponse(primary.getId(), Set.of(email), Set.of(phoneNumber), Set.of());
        }

        Contact primary = matchingContacts.stream()
                .filter(c -> c.getLinkPrecedence() == LinkPrecedence.PRIMARY)
                .min(Comparator.comparing(Contact::getCreatedAt))
                .orElse(matchingContacts.get(0));

        Set<String> emails = new HashSet<>();
        Set<String> phones = new HashSet<>();
        Set<Integer> secondaryIds = new HashSet<>();

        for (Contact c : matchingContacts) {
            if (c.getEmail() != null) emails.add(c.getEmail());
            if (c.getPhoneNumber() != null) phones.add(c.getPhoneNumber());
            if (!c.getId().equals(primary.getId())) secondaryIds.add(c.getId());
        }

        boolean isNewEmail = email != null && emails.stream().noneMatch(email::equalsIgnoreCase);
        boolean isNewPhone = phoneNumber != null && phones.stream().noneMatch(phoneNumber::equalsIgnoreCase);

        if (isNewEmail || isNewPhone) {
            Contact secondary = new Contact();
            secondary.setEmail(email);
            secondary.setPhoneNumber(phoneNumber);
            secondary.setLinkedId(primary.getId());
            secondary.setLinkPrecedence(LinkPrecedence.SECONDARY);
            contactRepository.save(secondary);
            if (email != null) emails.add(email);
            if (phoneNumber != null) phones.add(phoneNumber);
            secondaryIds.add(secondary.getId());
        }

        return new IdentifyResponse(primary.getId(), emails, phones, secondaryIds);
    }
}

