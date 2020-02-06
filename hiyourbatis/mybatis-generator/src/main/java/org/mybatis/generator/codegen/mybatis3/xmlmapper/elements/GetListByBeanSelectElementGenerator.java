package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * creator : whh-lether
 * date    : 2018/10/18
 * time    : 15:10
 * desc    :
 **/
public class GetListByBeanSelectElementGenerator extends AbstractXmlElementGenerator {

    public GetListByBeanSelectElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getGetListByBeanSelectId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultMap",introspectedTable.getListResultMapId()));

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseQueryModelType());
        answer.addAttribute(new Attribute("parameterType",parameterType.getFullyQualifiedName()));
        answer.addElement(selectForListTvSqlElement());
        answer.addElement(new TextElement(" where 1=1 "));
        answer.addElement(queryConditionElement());
        StringBuilder sb = new StringBuilder();
        XmlElement orderElement = new XmlElement("if");
        sb.append("orders !=null and orders.size > 0");
        orderElement.addAttribute(new Attribute("test",sb.toString()));
        sb.setLength(0);
        orderElement.addElement(new TextElement("order by ${orderStr}"));
        answer.addElement(orderElement);
        XmlElement pageInfoElement = new XmlElement("if");
        sb.setLength(0);
        sb.append("null != pageInfo");
        pageInfoElement.addAttribute(new Attribute("test",sb.toString()));
        sb.setLength(0);
        sb.append("limit #{pageInfo.step},#{pageInfo.pageSize}");
        pageInfoElement.addElement(new TextElement(sb.toString()));
        answer.addElement(pageInfoElement);
        parentElement.addElement(answer);
    }
}
