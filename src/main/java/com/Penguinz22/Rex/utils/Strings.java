package com.Penguinz22.Rex.utils;

public class Strings {

    public static String join(String separator, String... strings) {
        StringBuilder builder = new StringBuilder();
        for(String s: strings) {
            builder.append(s);
            builder.append(separator);
        }
        builder.setLength(builder.length() - separator.length());
        return builder.toString();
    }

}
