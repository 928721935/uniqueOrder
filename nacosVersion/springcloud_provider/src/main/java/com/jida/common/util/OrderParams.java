package com.jida.common.util;

import com.jida.common.dto.OrderParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OrderParams{

/**

* 根据字符串数组生成OrderParam集合

* @param params

* @return

*/

public static List createOrderings(String ... params){

return IntStream.range(0, params.length)

.mapToObj(i -> OrderParam.builder().param(params[i]).order(i).build())

.collect(Collectors.toList());

}

}