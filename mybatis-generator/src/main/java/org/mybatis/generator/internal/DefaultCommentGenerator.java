/*
 *  Copyright 2008 The Apache Software Foundation
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

package org.mybatis.generator.internal;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

/**
 * @author Jeff Butler
 * 
 */
public class DefaultCommentGenerator implements CommentGenerator {

	private Properties properties;
	private Properties systemPro;
	private boolean suppressDate;
	private boolean suppressAllComments;
	private String currentDateStr;

	public DefaultCommentGenerator() {
	    super();
	    properties = new Properties();
	    systemPro = System.getProperties();
	    suppressDate = false;
	    suppressAllComments = false;
	    currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
	}

	public void addJavaFileComment(CompilationUnit compilationUnit) {
	    // add no file level comments by default
	    return;
	}

	/**
	 * Adds a suitable comment to warn users that the element was generated, and
	 * when it was generated.
	 */
	public void addComment(XmlElement xmlElement) {
	    return;
	}

	public void addRootComment(XmlElement rootElement) {
	    // add no document level comments by default
	    return;
	}

	public void addConfigurationProperties(Properties properties) {
	    this.properties.putAll(properties);

	    suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

	    suppressAllComments = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
	}

	/**
	 * This method adds the custom javadoc tag for. You may do nothing if you do
	 * not wish to include the Javadoc tag - however, if you do not include the
	 * Javadoc tag then the Java merge capability of the eclipse plugin will
	 * break.
	 * 
	 * @param javaElement
	 *            the java element
	 */
	protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
	    javaElement.addJavaDocLine(" *");
	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(MergeConstants.NEW_ELEMENT_TAG);
	    if (markAsDoNotDelete) {
	        sb.append(" do_not_delete_during_merge");
	    }
	    String s = getDateString();
	    if (s != null) {
	        sb.append(' ');
	        sb.append(s);
	    }
	    javaElement.addJavaDocLine(sb.toString());
	}

	/**
	 * This method returns a formated date string to include in the Javadoc tag
	 * and XML comments. You may return null if you do not want the date in
	 * these documentation elements.
	 * 
	 * @return a string representing the current timestamp, or null
	 */
	protected String getDateString() {
	    String result = null;
	    if (!suppressDate) {
	        result = currentDateStr;
	    }
	    return result;
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    innerClass.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    sb.append(" ");
	    sb.append(getDateString());
	    innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
	    innerClass.addJavaDocLine(" */");
	}

	public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    innerEnum.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    innerEnum.addJavaDocLine(sb.toString().replace("\n", " "));
	    innerEnum.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    field.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    field.addJavaDocLine(sb.toString().replace("\n", " "));
	    field.addJavaDocLine(" */");
	}

	public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    field.addJavaDocLine("/**");
	    sb.append(" * ");
	    sb.append(introspectedTable.getFullyQualifiedTable());
	    field.addJavaDocLine(sb.toString().replace("\n", " "));
	    field.addJavaDocLine(" */");
	}

	public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
	    if (suppressAllComments) {
	        return;
	    }
	    //      method.addJavaDocLine("/**");
	    //      addJavadocTag(method, false);
	    //      method.addJavaDocLine(" */");
	}

	public static String getEncoding(String str) {      
	       String encode = "GB2312";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GB2312
	               String s = encode;      
	              return s;      //是的话，返回“GB2312“，以下代码同理
	           }      
	       } catch (Exception exception) {      
	       }      
	       encode = "ISO-8859-1";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是ISO-8859-1
	               String s1 = encode;      
	              return s1;      
	           }      
	       } catch (Exception exception1) {      
	       }      
	       encode = "UTF-8";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {   //判断是不是UTF-8
	               String s2 = encode;      
	              return s2;      
	           }      
	       } catch (Exception exception2) {      
	       }      
	       encode = "GBK";      
	      try {      
	          if (str.equals(new String(str.getBytes(encode), encode))) {      //判断是不是GBK
	               String s3 = encode;      
	              return s3;      
	           }      
	       } catch (Exception exception3) {      
	       }      
	      return "";        //如果都不是，说明输入的内容不属于常见的编码格式。
	   }
	public void addGetterComment(Method method, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }
	    method.addJavaDocLine("/**");
	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString().replace("\n", " "));
	    sb.setLength(0);
	    sb.append(" * [whh]@return ");
	    sb.append(introspectedColumn.getActualColumnName());
	    sb.append(" ");
	    String rk = introspectedColumn.getRemarks();
	    try{
	    	System.out.println(getEncoding("rk.en:" + rk) +"-"+ rk);
//		    System.out.println("s:"+new String(rk.getBytes("GBK"),Charset.forName("UTF-8")));
//		    System.out.println("s:"+new String(rk.getBytes("GBK"),Charset.forName("GB2312")));
//		    System.out.println("s:"+new String(rk.getBytes("GBK"),Charset.forName("ASCII")));
//		    System.out.println("s:"+new String(rk.getBytes("GBK"),Charset.forName("UNICODE")));
//		    
//		    System.out.println("s:"+new String(rk.getBytes("GB2312"),Charset.forName("UTF-8")));
//		    System.out.println("s:"+new String(rk.getBytes("GB2312"),Charset.forName("GBK")));
//		    System.out.println("s:"+new String(rk.getBytes("GB2312"),Charset.forName("ASCII")));
//		    System.out.println("s:"+new String(rk.getBytes("GB2312"),Charset.forName("UNICODE")));
//
//		    System.out.println("s:"+new String(rk.getBytes("UTF-8"),Charset.forName("GB2312")));
//		    System.out.println("s:"+new String(rk.getBytes("UTF-8"),Charset.forName("GBK")));
//		    System.out.println("s:"+new String(rk.getBytes("UTF-8"),Charset.forName("ASCII")));
//		    System.out.println("s:"+new String(rk.getBytes("UTF-8"),Charset.forName("UNICODE")));
//
//		    System.out.println("s:"+new String(rk.getBytes("ASCII"),Charset.forName("UTF-8")));
//		    System.out.println("s:"+new String(rk.getBytes("ASCII"),Charset.forName("GB2312")));
//		    System.out.println("s:"+new String(rk.getBytes("ASCII"),Charset.forName("GBK")));
//		    System.out.println("s:"+new String(rk.getBytes("ASCII"),Charset.forName("UNICODE")));
		    
	    }catch(Exception e){
	    	e.printStackTrace();
	    }
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString().replace("\n", " "));
	    method.addJavaDocLine(" */");
	}

	public void addSetterComment(Method method, IntrospectedTable introspectedTable,
	        IntrospectedColumn introspectedColumn) {
	    if (suppressAllComments) {
	        return;
	    }
	    method.addJavaDocLine("/**");
	    StringBuilder sb = new StringBuilder();
	    sb.append(" * ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString().replace("\n", " "));
	    Parameter parm = method.getParameters().get(0);
	    sb.setLength(0);
	    sb.append(" * @param ");
	    sb.append(parm.getName());
	    sb.append(" ");
	    sb.append(introspectedColumn.getRemarks());
	    method.addJavaDocLine(sb.toString().replace("\n", " "));
	    method.addJavaDocLine(" */");
	}

	public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable, boolean markAsDoNotDelete) {
	    if (suppressAllComments) {
	        return;
	    }
	    StringBuilder sb = new StringBuilder();
	    innerClass.addJavaDocLine("/**");
	    sb.append(" * @description : ");
	    sb.append(introspectedTable.getTableRemarks());
	    innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
	    sb.setLength(0);
	    //sb.append(" * @version");
		//innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
		//sb.setLength(0);
	    sb.append(" * @author      : ");
	    sb.append(systemPro.getProperty("user.name"));
	    innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
	    sb.setLength(0);
	    sb.append(" * @createDate  : ");
	    sb.append(currentDateStr);
	    innerClass.addJavaDocLine(sb.toString().replace("\n", " "));
	    innerClass.addJavaDocLine(" */");
	}
}
