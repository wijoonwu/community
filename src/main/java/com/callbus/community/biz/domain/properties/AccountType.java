package com.callbus.community.biz.domain.properties;

import lombok.Getter;

@Getter
public enum AccountType {
    Lessor("임대인"), Realtor("공인중개사"), Lessee("임차인");

    String description;

    AccountType(String description){
        this.description = description;
    }
}
