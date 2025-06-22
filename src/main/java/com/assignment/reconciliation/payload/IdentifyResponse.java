package com.assignment.reconciliation.payload;

import java.util.Set;

public class IdentifyResponse {
    private Integer primaryContactId;
    private Set<String> emails;
    private Set<String> phoneNumbers;
    private Set<Integer> secondaryContactIds;

    public Integer getPrimaryContactId() {
        return primaryContactId;
    }

    public void setPrimaryContactId(Integer primaryContactId) {
        this.primaryContactId = primaryContactId;
    }

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<Integer> getSecondaryContactIds() {
        return secondaryContactIds;
    }

    public void setSecondaryContactIds(Set<Integer> secondaryContactIds) {
        this.secondaryContactIds = secondaryContactIds;
    }

    public Set<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public IdentifyResponse(Integer primaryContactId, Set<String> emails, Set<String> phoneNumbers, Set<Integer> secondaryContactIds) {
        this.primaryContactId = primaryContactId;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.secondaryContactIds = secondaryContactIds;
    }
}