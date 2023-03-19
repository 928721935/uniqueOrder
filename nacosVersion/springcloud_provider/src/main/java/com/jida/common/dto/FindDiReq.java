package com.jida.common.dto;

import lombok.Data;

import java.util.List;

@Data
public class FindDiReq {
    private String id;
    private List<String> groupCodeList;
    private List<String> calcSceneCodeList;
    private List<String> bussSceneCodeList;
    private String checkMode;
    private String roleCode;
    private List<Long> userIdList;
}
