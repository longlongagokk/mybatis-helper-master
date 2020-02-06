package com.vitily.common.util;

import com.vitily.common.module.BaseEntity;

import java.io.InputStream;
import java.security.MessageDigest;
import java.text.MessageFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public class StringUtil {

    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字符串
     *
     * @param b 字节数组
     * @return 16进制字符串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(byteToHexString(b[i]));
        }

        return sb.toString();
    }

    /**
     * 转换字节数为16进制字符串
     *
     * @param b byte数值
     * @return 16进制字符串
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;

        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 获取指定字符串的MD5编码
     *
     * @param original 字符串
     * @return MD5编码
     */
    public static String MD5Encode(String original) {
        String ret = null;

        try {
            ret = new String(original);
            MessageDigest md = MessageDigest.getInstance("MD5");
            ret = byteArrayToHexString(md.digest(ret.getBytes()));
        } catch (Exception ex) {
            // empty
        }

        return ret;
    }

    /**
     * 获得0-9的随机数字符串
     *
     * @param length 返回字符串的长度
     * @return String
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    /**
     * 获得0-9,a-z,A-Z范围的随机字符串
     *
     * @param length 字符串长度
     * @return String
     */
    public static String getRandomChar(int length) {
        char[] chr = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
                'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
                'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
                'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
                'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z'};

        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length; i++) {
            buffer.append(chr[random.nextInt(62)]);
        }

        return buffer.toString();
    }

    /**
     * 判断字符串数组中是否包含某字符串
     *
     * @param substring 某字符串
     * @param source    源字符串数组
     * @return 包含则返回true，否则返回false
     */
    public static boolean isContains(String substring, String[] source) {
        if (source == null || source.length == 0) {
            return false;
        }

        for (int i = 0; i < source.length; i++) {
            String aSource = source[i];
            if (aSource.equals(substring)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 首字母大写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String upFirstChar(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    /**
     * 首字母小写
     *
     * @param str 字符串
     * @return 首字符大写后的字符串
     */
    public static String lowerFirstChar(String str) {
        return str.substring(0, 1).toLowerCase() + str.substring(1);
    }
    public static <T extends BaseEntity> String blistIdToString(List<T> list,String split){
    	StringBuilder sb = new StringBuilder();
    	if(list == null){
    		return null;
    	}
    	for(BaseEntity item:list){
    		sb.append(item.getId());
    		sb.append(split);
    	}
    	if(sb != null && sb.length() > 0){
    		sb.deleteCharAt(sb.length() -split.length());
    	}
        return sb.toString();
    }
    /**
     * 数组list转成string
     *
     * @param
     * @return
     */
    public static String listToString(@SuppressWarnings("rawtypes") List list,String split) {
    	StringBuilder sb = new StringBuilder();
    	if(list == null){
    		return null;
    	}
    	for(Object item:list){
    		sb.append(item);
    		sb.append(split);
    	}
    	if(sb != null && sb.length() > 0){
    		sb.deleteCharAt(sb.length() -split.length());
    	}
        return sb.toString();
    }
    /**
     * 字符串数组转成列表
     *
     * @param arr
     * @return
     */
    public static List<String> StringsToList(String[] arr) {
        List<String> strList = null;
        if (null == arr) {
            return strList;
        }
        strList = new ArrayList<String>();
        for (int i = 0; i < arr.length; i++) {
            strList.add(arr[i]);
        }
        return strList;
    }
    /**
     * 字符串数组转成列表
     *
     * @param iarr
     * @return
     */
    public static List<Integer> StringsToIntList(String[] iarr) {
        List<Integer> strList = null;
        if (null == iarr) {
            return strList;
        }
        strList = new ArrayList<>();
        for (int i = 0; i < iarr.length; i++) {
            strList.add(Integer.valueOf(iarr[i]));
        }
        return strList;
    }
    public static List<Long> StringsToLongList(String[] iarr) {
        List<Long> strList = null;
        if (null == iarr) {
            return strList;
        }
        strList = new ArrayList<>();
        for (int i = 0; i < iarr.length; i++) {
            strList.add(Long.valueOf(iarr[i]));
        }
        return strList;
    }
    /**
     *
    *
    * @param arr
    * @return
    */
   public static String listToString(String[] arr,String pt) {
       if(null == arr || arr.length == 0){
    	   return "";
       }
       StringBuffer sb = new StringBuffer();
       for(String s:arr){
    	   sb.append(s).append(pt);
       }
       return sb.toString().substring(0, sb.length() - pt.length());
   }

    /**
     * inputstream解析成string
     *
     * @param in
     * @return
     */
    public static String inputStream2String(InputStream in) {
        try {
            StringBuffer out = new StringBuffer();
            byte[] b = new byte[4096];
            for (int n; (n = in.read(b)) != -1; ) {
                out.append(new String(b, 0, n));
            }
            return out.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把字串变为指定长度，用指定值实录空位
     *
     * @return
     * @throws Exception
     */
    public static String fixStringToLength(String numStr, int length,
                                           char addChar) {
        if (length <= 0) {
            return "";
        }
        if (numStr.length() == length) {
            return numStr;
        } else if (numStr.length() > length) {
            return numStr.substring(0, length);
        }
        char bytes[] = new char[length];
        char cs[] = numStr.toCharArray();
        int fromId = length - cs.length;

        for (int i = 0; i < fromId; i++) {
            bytes[i] = addChar;
        }
        for (int i = 0; i < cs.length; i++) {
            bytes[fromId] = cs[i];
            fromId++;
        }

        return new String(bytes, 0, fromId);
    }

    /**
     * 查看是否匹配
     *
     * @param reg    正则表达式
     * @param source 匹配的数据
     * @return
     */
    public static boolean isMatch(String reg, String source) {
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(source);
        return matcher.matches();
    }

    static Map<String, String> idMap = new HashMap<String, String>();

    public static String leftFill(String content, int length, String fillContent) throws Exception {
        if (content.length() >= length)
            return content;
        else {
            StringBuffer leftFillSb = new StringBuffer();
            int dif = length - content.length();
            for (int i = 1; i <= dif; i++) {
                leftFillSb.append(fillContent);
            }
            leftFillSb.append(content);
            return leftFillSb.toString();
        }
    }

    public static String substituteParams(String msgtext, Object params[]) {
        if (params == null || msgtext == null) {
            return msgtext;
        }
        MessageFormat mf = new MessageFormat(msgtext);
        return mf.format(params);
    }

    public static String encryPhone(String phone) throws Exception {
        String newPhone = phone;
        if (phone != null && phone.length() >= 11) {
            newPhone = phone.substring(0, 3);
            newPhone += "*****";
            newPhone += phone.substring(8);
        }

        return newPhone;
    }
    public static String encryEmail(String email)throws Exception{
    	if(email == null){
    		return null;
    	}
    	String newEmail=email;
    	int index=newEmail.indexOf("@");
    	if(index >= 1){
    		newEmail=newEmail.substring(0,1);
    		newEmail +="****";
    		if(index > 1){
    			newEmail +=email.substring(index-1,index);
    		}
    		newEmail +=email.substring(index);
    	}
    	return newEmail;
    }
    public static String encryUserName(String name)throws Exception{
    	if(name == null){
    		return null;
    	}
    	if(name.length() >= 2){
    		return name.substring(0,1) + "****" + name.substring(name.length() - 1);
    	}
    	return name + "****";
    }
    public static String encryCall(String call)throws Exception{
    	String newCall=call;
    	if(newCall != null && newCall.length() >= 11){
    		newCall=call.substring(0, 5);
    		newCall +="*****";
    	}
    	return newCall;
    }
    /**
     * 格式化字符窜替代
     * 如传入的content: first{item}last{this},param 为:["item","this"],values["dd","bb"]
     * 则替换为firstddlastbb
     * @param content
     * @param param
     * @param values
     * @return
     */
    public static String fmtParamString(String content,String[] param,String[] values){
    	if(param.length !=values.length || param.length == 0){
    		return content;
    	}
    	for(int i=0;i<param.length;++i){
    		content = content.replaceAll("\\{"+param[i]+"\\}", values[i]);
    	}
    	return content;
    }
    public static String wrapErrorMessge(String content){

		if(content.indexOf("Exception") > -1){
			return "应用程序错误";
		}
		return content;
    }

    /**
     * 判断一个字符串Str是否为空 return true if it is supplied with an empty, zero length,
     * or whitespace-only string. documented
     *
     * @param str
     * @return
     */
    public static boolean isEmpty(String str) {
        return (str == null) || (str.trim().length() == 0);
    }
    public static boolean isNotEmpty(String str) {
        return (str != null) && (str.trim().length() > 0);
    }
    public static void main(String[] args){
        System.out.println(toLine("UserInfo"));
        System.out.println(toLine("dserInfo"));
        System.out.println(toLine("sfwassf"));
        System.out.println(toLine("FFsfwassf"));
        System.out.println(toLine("FFsFFwassf"));
        System.out.println(toLine("FFsFFwasDfD"));
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
        chars[0] -= 32;
        return String.valueOf(chars);
    }

}
