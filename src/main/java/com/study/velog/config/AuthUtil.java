package com.study.velog.config;

public class AuthUtil {

    public static String currentUserEmail()
    {
        return DataInit.MASTER_EMAIL;
    }

    public static String currentNickName()
    {
        return DataInit.MASTER;
    }

    public static Long currentUserId()
    {
        return DataInit.MASTER_ID;
    }
}
