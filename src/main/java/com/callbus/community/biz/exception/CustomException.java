package com.callbus.community.biz.exception;

import com.callbus.community.biz.domain.properties.ErrorCode;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

}
