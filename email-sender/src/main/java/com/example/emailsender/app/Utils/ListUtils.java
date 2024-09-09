package com.example.emailsender.app.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class ListUtils {
    public static List<String> convertToStringList(List<?> list) {
        return list.stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }
}
