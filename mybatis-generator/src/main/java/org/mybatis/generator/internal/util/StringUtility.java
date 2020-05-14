/*
 *  Copyright 2005 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.internal.util;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Jeff Butler
 */
public class StringUtility {

    /**
     * Utility class. No instances allowed
     */
    private StringUtility() {
        super();
    }

    public static boolean stringHasValue(String s) {
        return s != null && s.length() > 0;
    }

    public static String composeFullyQualifiedTableName(String catalog,
            String schema, String tableName, char separator) {
        StringBuilder sb = new StringBuilder();

        if (stringHasValue(catalog)) {
            sb.append(catalog);
            sb.append(separator);
        }

        if (stringHasValue(schema)) {
            sb.append(schema);
            sb.append(separator);
        } else {
            if (sb.length() > 0) {
                sb.append(separator);
            }
        }

        sb.append(tableName);

        return sb.toString();
    }
    public static String setFirstLowerOfString(String input){
        if(!stringHasValue(input)){
            return input;
        }
        char f = input.charAt(0);
        if (f >= 'A' && f <= 'Z') {
            f += 32;
        }
        return f + input.substring(1);
    }

    public static boolean stringContainsSpace(String s) {
        return s != null && s.indexOf(' ') != -1;
    }

    public static String escapeStringForJava(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("\\\""); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static String escapeStringForXml(String s) {
        StringTokenizer st = new StringTokenizer(s, "\"", true); //$NON-NLS-1$
        StringBuilder sb = new StringBuilder();
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            if ("\"".equals(token)) { //$NON-NLS-1$
                sb.append("&quot;"); //$NON-NLS-1$
            } else {
                sb.append(token);
            }
        }

        return sb.toString();
    }

    public static boolean isTrue(String s) {
        return "true".equalsIgnoreCase(s); //$NON-NLS-1$
    }

    public static boolean stringContainsSQLWildcard(String s) {
        if (s == null) {
            return false;
        }

        return s.indexOf('%') != -1 || s.indexOf('_') != -1;
    }


    public static void main(String[] args){
        System.out.println(underscoreToCamelCase("_abc_sS"));
        System.out.println(underscoreToCamelCase("_a_FC"));
        System.out.println(underscoreToCamelCase("_A_xdf"));
        System.out.println(underscoreToCamelCase("_Db"));
        System.out.println(underscoreToCamelCase("_DB"));
    }
    /**
     * 下划线命名转驼峰命名
     * @param underscore
     * @return
     */
    public static String underscoreToCamelCase(String underscore){
        String[] ss = underscore.split("_");
        if(ss.length ==1){
            return underscore;
        }

        StringBuffer sb = new StringBuffer();
        sb.append(ss[0]);
        for (int i = 1; i < ss.length; i++) {
            sb.append(upperFirstCase(ss[i]));
        }

        return sb.toString();
    }

    /**
     * 驼峰 转下划线
     * @param camelCase
     * @return
     */
    public static String toLine(String camelCase){
        Pattern humpPattern = Pattern.compile("[A-Z]");
        Matcher matcher = humpPattern.matcher(camelCase);
        StringBuffer sb = new StringBuffer();
        while(matcher.find()){
            matcher.appendReplacement(sb, "_"+matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
    /**
     * 首字母 转小写
     * @param str
     * @return
     */
    private static String lowerFirstCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 首字母 转大写
     * @param str
     * @return
     */
    private static String upperFirstCase(String str) {
        char[] chars = str.toCharArray();
        if(chars[0] >= 'a' && chars[0] <= 'z')
            chars[0] -= 32;
        return String.valueOf(chars);
    }


}
