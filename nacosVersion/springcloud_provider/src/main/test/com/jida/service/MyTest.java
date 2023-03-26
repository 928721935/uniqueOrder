package com.jida.service;

import com.jida.common.dto.OrderParam;
import com.jida.common.util.OrderParams;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyTest {
    public static void main(String[] args) {
        List<OrderParam> orderings= OrderParams.createOrderings("温度", "运行时间");

        List<String> target= Stream.of("sa", "温度1", "ttttt", "运行时间", "温度", "运行时间ss")
                .collect(Collectors.toList());
        List result = target.stream()
                .map(t -> toOrderParam(orderings, t))
                // 这里已经转化成了OrderParam了，所以直接根据OrderParam的order排序即可
                .sorted(Comparator.comparing(OrderParam::getOrder))
                .map(OrderParam::getParam)
                .collect(Collectors.toList());
        System.out.println(result);
    }

    private static OrderParam toOrderParam(List<OrderParam> orderings, String t) {
        return orderings.stream()
                .filter(orderParam -> t.contains(orderParam.getParam()))
                .findFirst()
                .map(orderParam -> OrderParam.builder().param(t).order(orderParam.getOrder()).build())
                // 没有包含在orderings里的，就给一个最低优先级即可
                .orElse(OrderParam.builder().param(t).order(Integer.MAX_VALUE).build());
    }
}
