package com.fit.usernluappsbapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fit.usernluappsbapi.model.User;
import lombok.*;

import java.util.Date;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    @JsonProperty("user_name")
    private String userName;
    private User.Role role;
    @JsonProperty("non_locked")
    private boolean isNonLocked;
    @JsonProperty("vip")
    private boolean isVip;
    @JsonProperty("expired_vip_date")
    private Date expiredVipDate;

    public UserResponse(User user){
        this.userName = user.getUsername();
        this.role = user.getRole();
        this.isNonLocked = user.isNonLocked();
        this.isVip = user.isVip();
        this.expiredVipDate = user.getExpiredVipDate();
    }
}
