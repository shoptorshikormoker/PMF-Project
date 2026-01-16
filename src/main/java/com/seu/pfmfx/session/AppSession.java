package com.seu.pfmfx.session;

import com.seu.pfmfx.models.User;

public final class AppSession {

    private static User currentUser;

    private AppSession() {
    }

    public static void setCurrentUser(User user) {
        currentUser = user;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static boolean isLoggedIn() {
        return currentUser != null;
    }

    public static int getCurrentUserId() {
        return currentUser != null ? currentUser.getId() : -1;
    }

    public static void clear() {
        currentUser = null;
    }
}
