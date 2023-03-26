package com.jida.common.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderParam{

private String param;

// 值越小，优先级越高

private int order;

}