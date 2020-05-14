package com.mybatishelper.core.util;

public abstract class StringUtils {

    /**
     * 判断是否是正常的sql 只有[,a-zA-Z_d.`]" 才是
     * @param sql 1
     * @return 2
     */
    public static boolean isNormalSql(String sql){
        return null != sql && sql.matches("^[a-z0-9A-Z,_.`(),\\s]*$");
    }
    public static boolean isEmpty(String str){
        return org.springframework.util.StringUtils.isEmpty(str);
    }
}
