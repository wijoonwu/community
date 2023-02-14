package com.callbus.community.biz.domain.properties;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    MEMBER_NOT_FOUND(401,"외부 사용자는 글을 작성할 수 없습니다."),
    INTERNAL_SERVER_ERROR(500, "서버 에러가 발생했습니다. 고객센터로 문의 바랍니다."),
    ARTICLE_NOT_FOUND(404, "게시글을 찾을 수 없습니다." );

    private final int status;
    private final String msg;
}