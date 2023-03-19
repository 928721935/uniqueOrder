package com.jida.service;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xu
 * @Date 2021/6/17 18:32
 */
@Service
public class MyToolService {
    String name;

    public String findById(int id) {
        return "123";
    }


    public List<String> findAll(int id) {

        return Lists.newArrayList("1", "2", "3");
    }

    public int getIdValue(String str) {
        if (!checkIds(str)) {
            return 0;
        }

        int id = Integer.parseInt(str);

        if (checkIdRange(id)) {
            return id;
        }
        return 0;
    }

    private boolean checkIds(String ids) {
        if (StringUtils.isBlank(ids)) {
            return false;
        }
        if (ids.split(",").length > 1) {
            return true;
        }
        return false;
    }

    public static boolean checkIdRange(int id) {
        if (id > 100 || id < 0) {
            return false;
        }
        return true;
    }

    private final boolean finalMethod(String ids) {
        if (StringUtils.isBlank(ids)) {
            return false;
        }

        return ids.contains("-");
    }

    public String convertStr(String str) {
        if (finalMethod(str)) {
            return str.toUpperCase();
        }
        return str.toLowerCase();
    }

    public void quantity(int count) {

        List<String> arrayList = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            arrayList.add("v-" + i);
        }

        System.out.println(Joiner.on(",").join(arrayList));
    }


    public void execQuantity(String ids) {
        if (checkIds(ids)) {
            dataLog(ids);
            return;
        }

        name = "ABC";
        System.out.println(name);
    }

    private void dataLog(String ids) {
        System.out.println(ids + ",data log....");
    }


    private String checkPrivate(String str) {
        if (checkIds(str)) {
            str = "OK";
            return str;
        }

        str = "NO";
        return str;
    }

}