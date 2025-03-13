package com.example.my_spring_boot_demo1.util;


import com.example.my_spring_boot_demo1.controller.loginController.pojo.LoginDto;

public class UserUtil {

    private static final ThreadLocal<LoginDto> USER_THREAD_LOCAL = new ThreadLocal<>();

    public static LoginDto getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void setUser(LoginDto loginDto) {
        USER_THREAD_LOCAL.set(loginDto);
    }

    public static void clearUser() {
        if (USER_THREAD_LOCAL.get() != null) {
            USER_THREAD_LOCAL.remove();
        }
    }
}
