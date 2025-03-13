package com.example.my_spring_boot_demo1.util;

public class StringUtil {

    private static final String USER_ID_PATTERN = "^\\w+$";
    private static final String PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[()\\[\\]{}<>+\\-*/?,.:;\"'_\\|~`!@#$%^&=]).{8,}$";

    /**
     * 檢查帳號是否符合英文字母、數字及底線
     * @param userId
     * @return
     */
    public static boolean checkUserId(String userId) {
        return userId.matches(USER_ID_PATTERN);
    }

    /**
     * 檢查字串是否符合密碼規則
     * @param password
     * @return
     */
    public static boolean checkPassword(String password) {
        return password.matches(PASSWORD_PATTERN);
    }
}
