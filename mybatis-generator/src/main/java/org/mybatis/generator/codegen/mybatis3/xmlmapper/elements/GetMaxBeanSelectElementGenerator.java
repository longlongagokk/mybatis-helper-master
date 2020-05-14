package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * creator : whh-lether
 * date    : 2018/10/18
 * time    : 15:10
 * desc    :
 **/
public class GetMaxBeanSelectElementGenerator extends AbstractXmlElementGenerator {

    public GetMaxBeanSelectElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("select"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id", introspectedTable.getGetMaxBeanSelectId())); //$NON-NLS-1$
        if (introspectedTable.getRules().generateResultMapWithBLOBs()) {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getResultMapWithBLOBsId()));
        } else {
            answer.addAttribute(new Attribute("resultMap", //$NON-NLS-1$
                    introspectedTable.getBaseResultMapId()));
        }

        FullyQualifiedJavaType parameterType = new FullyQualifiedJavaType(introspectedTable.getBaseQueryModelType());
        answer.addAttribute(new Attribute("parameterType",parameterType.getFullyQualifiedName()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(getBaseColumnListElement());
        if (introspectedTable.hasBLOBColumns()) {
            answer.addElement(new TextElement(",")); //$NON-NLS-1$
            answer.addElement(getBlobColumnListElement());
        }
        sb.setLength(0);
        sb.append(" from ");
        sb.append(introspectedTable.getAliasedFullyQualifiedTableNameAtRuntime());
        sb.append(" where 1=1 ");
        answer.addElement(new TextElement(sb.toString()));
        //include
        answer.addElement(queryConditionElement());
        if(!introspectedTable.getPrimaryKeyColumns().isEmpty()){
            sb.setLength(0);
            sb.append(" order by ");
            for(IntrospectedColumn column:introspectedTable.getPrimaryKeyColumns()){
                sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(column));
                sb.append(" desc");
                sb.append(",");
            }
            sb.deleteCharAt(sb.length()-1);
        }
        sb.append(" limit 1");
        answer.addElement(new TextElement(sb.toString()));

        parentElement.addElement(answer);
    }
}
