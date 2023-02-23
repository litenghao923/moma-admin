package com.moma.momaadmin.util;


import java.util.Random;

public class ValidateCodeUtil {

    public static String generateValidateCode(int length) {
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < length; i++) {
            code.append(new Random().nextInt(10));
        }

        return code.toString();
    }


    public static String generateStringValidateCode(int length) {
        String hash = Integer.toHexString(new Random().nextInt());
        return hash.substring(0, length);
    }


    public static void main(String[] args) {
        System.out.println(generateStringValidateCode(6));
    }

}
