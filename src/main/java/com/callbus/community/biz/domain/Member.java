package com.callbus.community.biz.domain;

import com.callbus.community.biz.domain.properties.AccountType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    private String accountId;

    private boolean quit;
}
