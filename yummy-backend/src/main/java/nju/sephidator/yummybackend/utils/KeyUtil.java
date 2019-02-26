package nju.sephidator.yummybackend.utils;

import java.util.Random;

public class KeyUtil {
    public static String randomCheckCode() {
        Random random = new Random();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            result.append(random.nextInt(10));
        }
        return result.toString();
    }
}
