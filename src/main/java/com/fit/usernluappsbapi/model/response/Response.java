package com.fit.usernluappsbapi.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String message;
    private Object data;

}
