package com.app.spendeasyjava.domain.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Response {

    private String result;
    private Object details;

    public Response(String result) {
        this.result = result;
    }
}
