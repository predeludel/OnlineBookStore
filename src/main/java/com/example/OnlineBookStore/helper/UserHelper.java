package com.example.OnlineBookStore.helper;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserHelper {
    public static boolean isAdmin(User user) {
        for (GrantedAuthority var : user.getAuthorities()) {
            if (var.getAuthority().equals("admin")) {
                return true;
            }
        }
        return false;
    }
}
