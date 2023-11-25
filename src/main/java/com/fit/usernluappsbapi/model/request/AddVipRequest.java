package com.fit.usernluappsbapi.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddVipRequest {
    private String username;
    private int days;
}
