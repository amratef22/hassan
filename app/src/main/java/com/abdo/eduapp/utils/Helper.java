package com.abdo.eduapp.utils;

import java.util.Random;

public class Helper {

    public static int generateCode() {

        Random random = new Random();
        int upperBound = 9999 - 1000;
        int code = random.nextInt(upperBound) + 1000;
        return code;
    }
}
