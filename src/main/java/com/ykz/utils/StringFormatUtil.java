package com.ykz.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringFormatUtil {

    public static String getLength(String value) {

        List<String> list = new ArrayList<>();
        Pattern p = Pattern.compile("([(（\\[])([0-9a-zA-Z.,/=，])*[）)\\]]");
        Matcher m = p.matcher(value);
        while (m.find()) {
            list.add(m.group(0).substring(1, m.group().length() - 1));
        }
        String lengthStr = value;
        if (!list.isEmpty()){
            lengthStr = list.get(0);
        }
        return subCharBefore(lengthStr, ",", "，");
    }

    private static String subCharBefore(String value, String... char1) {
        for (String flag : char1){
            if (value.contains(flag)){
                int index = value.indexOf(flag);
                return value.substring(0, index);
            }
        }
        return value;
    }

    public static String getType(String value){
        return subCharBefore(value, "(", "（");
    }

    public static void main(String[] args) {
        String msg = "String[20,3)";

        System.out.println(getType(msg));
        System.out.println(getLength(msg));
    }
}

