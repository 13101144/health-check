package com.github.health.check.util;

public class KeyGenerator {

    public static String generateKey(String projectName, String checkName) {
        return new StringBuilder().append(projectName)
                .append("-")
                .append(checkName)
                .toString();
    }
}
