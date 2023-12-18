package com.example.my_blog.utils;

import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CastUtils {
    public static List<Long> convertStringToLongList(String str) {
        return !StringUtils.isEmptyOrWhitespace(str) ?
                Arrays.stream(str.split(",")).mapToLong(Long::parseLong).boxed().toList() : null;
    }

    public static String convertLongListToString(List<Long> longList) {
        return longList.stream().map(Object::toString).collect(Collectors.joining(","));
    }
}
