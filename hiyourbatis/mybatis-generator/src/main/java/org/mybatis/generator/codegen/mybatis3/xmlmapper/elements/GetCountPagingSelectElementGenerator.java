package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;

/**
 * creator : whh-lether
 * date    : 2018/10/18
 * time    : 15:10
 * desc    :
 **/
public class GetCountPagingSelectElementGenerator extends AbstractXmlElementGenerator {

    public GetCountPagingSelectElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getGetCountPageSelectId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType","java.lang.Integer"));

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseQueryModelType());
        answer.addAttribute(new Attribute("parameterType",parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select count(1) as recordCount from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" where 1=1");
        answer.addElement(new TextElement(sb.toString()));
        //include
        answer.addElement(queryConditionElement());

        parentElement.addElement(answer);
    }
}
