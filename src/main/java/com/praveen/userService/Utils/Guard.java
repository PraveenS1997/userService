package com.praveen.userService.Utils;

public class Guard {
    public static void notNull(Object value, String message) {
        if(value == null){
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String value, String message) {
        if(value == null || value.isEmpty() || value.isBlank()){
            throw new IllegalArgumentException(message);
        }
    }

    public static void greaterThanZero(Long value, String message) {
        if(value == null || value <= 0){
            throw new IllegalArgumentException(message);
        }
    }
}
