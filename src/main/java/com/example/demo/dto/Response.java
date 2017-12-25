package com.example.demo.dto;

import lombok.Data;

/**
 * Created by ksb on 2017. 12. 24..
 */
public class Response {

    @Data
    public static class Message {
        private String status;
        private Result result;
    }

    @Data
    public static class Result {
        private String msg;
        private String cafeUrl;
        private String articleId;
        private String articleUrl;
    }

}

