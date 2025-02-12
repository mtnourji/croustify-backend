package com.croustify.backend.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class CompanyInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private UserCredential requestor;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String targetEmail;
    private String deeplink;
    private int clickCount=0;
    private int maxClick=5;
    private LocalDateTime createdOn = LocalDateTime.now();
    private LocalDateTime expiresOn = LocalDateTime.now().plus(5, ChronoUnit.DAYS);

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserCredential getRequestor() {
        return requestor;
    }

    public void setRequestor(UserCredential requestor) {
        this.requestor = requestor;
    }

    public String getTargetEmail() {
        return targetEmail;
    }

    public void setTargetEmail(String targetEmail) {
        this.targetEmail = targetEmail;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public int getMaxClick() {
        return maxClick;
    }

    public void setMaxClick(int maxClick) {
        this.maxClick = maxClick;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(LocalDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public LocalDateTime getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(LocalDateTime expiresOn) {
        this.expiresOn = expiresOn;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
