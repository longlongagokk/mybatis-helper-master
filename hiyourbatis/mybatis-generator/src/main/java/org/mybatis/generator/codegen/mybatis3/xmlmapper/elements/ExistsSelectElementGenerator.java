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
public class ExistsSelectElementGenerator extends AbstractXmlElementGenerator {

    public ExistsSelectElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getIsExistsSelectId())); //$NON-NLS-1$
        answer.addAttribute(new Attribute("resultType","java.lang.Boolean"));

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseQueryModelType());
        answer.addAttribute(new Attribute("parameterType",parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select case when exists(select 0 from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" where");
        answer.addElement(new TextElement(sb.toString()));
        //include
        answer.addElement(existConditionElement());
        sb.setLength(0);
        sb.append(") then 1 else 0 end as exists_record from dual");
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }
}
