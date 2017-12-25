package com.example.demo.dto;

import lombok.Data;

/**
 * Created by ksb on 2017. 12. 23..
 */
@Data
public class Token {

    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private String expiresIn;

}
