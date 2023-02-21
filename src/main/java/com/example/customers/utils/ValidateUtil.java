package com.example.customers.utils;

import java.util.regex.Pattern;

public class ValidateUtil {
    public static final String FULLNAME_REGEX = "^[a-zA-Z \\-\\.\\']*$";
    public static final String ADDRESS_REGEX = "^[A-Za-z0-9][A-Za-z0-9\\s]{4,50}$";
    public static final String FULL_NAME = "^([A-Z][a-z]*((\\s)))+[A-Z][a-z]*$";
    public static final String PHONE_NUMBER = "^(0|84)(3|5|7|8|9)([0987654321]{8})$";
    public static boolean isNameValid(String name) {
        return Pattern.matches(FULLNAME_REGEX, name);
    }
    public static boolean isAddressValid(String address) {
        return Pattern.matches(ADDRESS_REGEX, address);
    }
    public static boolean isPhoneValid(String address) {
        return Pattern.matches(PHONE_NUMBER, address);
    }
}
