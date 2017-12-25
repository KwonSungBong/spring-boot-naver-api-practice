package com.example.demo.dto;

import lombok.Data;

import java.util.List;

/**
 * Created by ksb on 2017. 12. 24..
 */
@Data
public class Param {

    private String subject;
    private String content;
    private List<NaverCafe> naverCafeList;

}
