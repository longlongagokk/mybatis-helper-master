package com.vitily.common.util;

import com.vitily.common.module.BaseEntity;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * 一些常用攻击集合
 * @author lether
 *
 */
@Slf4j
public class CommonUtil {
	private CommonUtil(){
		throw new AssertionError();
	}
	/**
	 * 判断某个短字符串是否在常字符串内
	 * @param longStr 1
	 * @param shortStr 2
	 * @param trimStr 3
	 * @return 4
	 */
	public static boolean inStr(String longStr,String shortStr,char trimStr){
		return !StringUtil.isEmpty(shortStr) && (trimStr + longStr + trimStr).contains(trimStr + shortStr + trimStr);
	}
	/**
	 * 判断是否是正常的sql 只有[,a-zA-Z_d]" 才是
	 * @param sql 1
	 * @return 2
	 */
	public static boolean isNormalSql(String sql){
		return null != sql && sql.matches("^[a-z0-9A-Z,_.]*$");
	}

	/**
	 * 匹配逗号、数字(1,2,...3)
	 * @param strVal 1
	 * @return 2
	 */
    public static boolean isNumOrD(String strVal)
    {
    	return null != strVal && strVal.matches("^[-]?[\\d]+([,][-]?[\\d]+)*$");
    }
    /**
     * is email
     * @param strVal 1
     * @return 2
     */
    public static boolean isEmail(String strVal)
    {
    	return null != strVal
				&& strVal.indexOf('@') > -1
				&& strVal.length() < 255
				&& strVal.matches("^([a-z0-9A-Z_]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,10}$");
    }

	/**
	 * 是否整数
	 * @param strVal 1
	 * @return 2
	 */
	public static boolean isNumber(String strVal)
    {
    	return null != strVal && strVal.matches("^-?\\d+$");
    }

	/**
	 * 匹配逗号、数字｛含小数点｝
	 * @param strVal 1
	 * @return 2
	 */
	public static boolean isDecimalOrD(String strVal)
    {
    	return null != strVal && strVal.matches("^(\\d+(\\.)?\\d*)([,]\\d+(\\.)?\\d*)*$");
    }
    public static boolean isDecimal(String strVal){
    	return null != strVal && strVal.matches("^-?\\d+((\\.)?\\d+)?$");
    }
    public static boolean isNormalFilepath(String strVal){
		return (strVal != null && strVal.length() != 0) && strVal.matches("^[a-z0-9A-Z\\-_/]*$");
	}
    /**
     * 获取随即字符串[A-Z1-9]
     * @param length 1
     * @return 2
     */
    public static String ranStr(int length){
    	String base = "ABCDEFGHIJKLMNPQRSTUVWXYZ123456789";   
	    Random random = new Random();
	    StringBuilder sb = new StringBuilder();
	    for (int i = 0; i < length; i++) {   
	        int number = random.nextInt(base.length());   
	        sb.append(base.charAt(number));   
	    }   
	    return sb.toString();   
    }
	private static <T> boolean isEqualOfType(T left,T right){
        if (null != left) {
            return left.equals(right);
        } else if (null != right) {
            return false;
        }
		return true;
	}
	public final static boolean isEmpty(Object[] array){
		return array == null || array.length == 0;
	}
	public final static boolean isNotEmpty(Object[] array){
		return !isEmpty(array);
	}
	public final static boolean isEmpty(Collection<?> collection){
		return collection == null || collection.isEmpty();
	}
	public final static boolean isNotEmpty(Collection<?> collection){
		return !isEmpty(collection);
	}
	public final static boolean isEmpty(Map<?, ?> map){
		return map == null || map.isEmpty();
	}
	public final static boolean isNotEmpty(Map<?, ?> map){
		return !isEmpty(map);
	}
	public final static boolean isNull(Object o){
		return null == o;
	}
	public final static boolean isNotNull(Object o){
		return null != o;
	}

	public static boolean isEqual(String left,String right){
		return isEqualOfType(left,right);
	}
	public static boolean isEqual(BigDecimal left, BigDecimal right){
		return isEqualOfType(left,right);
	}
	public static boolean isEqual(Integer left, Integer right){
		return isEqualOfType(left,right);
	}
	public static boolean isEqual(Long left, Long right){
		return isEqualOfType(left,right);
	}
    public static boolean isEqual(Enum left, Enum right){
        return isEqualOfType(left,right);
    }
	public static void SystemPrintln(Object obj){
		System.out.println(obj);
	}
	public static void SystemDebugPrintln(Object obj){
		System.out.println(obj);
	}
	public final static void formatUpdateEntity(BaseEntity be){
		be.setUpdateDate(new Date());
	}

	private static void setCharOfString(char[] cc,String v,int beginPos,int len){
		if(v == null){
			v = "";
		}
		char[] tc = v.toCharArray();
		for(int i = len-1,l = tc.length -1;i >= 0 && l >=0;--i,--l){
			cc[beginPos+i] = tc[l];
		}
	}
	public static boolean booleanOf(String val){
		return Boolean.valueOf(val) || !CommonUtil.isEqual("0",val);
	}
}
