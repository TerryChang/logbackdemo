package com.terry.logbackdemo.vo;

import lombok.*;

@NoArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class UserVO {
    private Long idx;
    private String name;
    private String loginId;

    @Builder
    public UserVO(Long idx, String loginId, String name) {
        this.idx = idx;
        this.loginId = loginId;
        this.name = name;
    }
}
