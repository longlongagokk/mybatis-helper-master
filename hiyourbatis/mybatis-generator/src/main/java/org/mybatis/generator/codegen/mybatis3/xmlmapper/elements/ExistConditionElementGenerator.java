package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * creator : whh-lether
 * date    : 2018/10/18
 * time    : 13:49
 * desc    :
 **/
public class ExistConditionElementGenerator extends AbstractXmlElementGenerator {

    public ExistConditionElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id",introspectedTable.getSqlExistsId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        XmlElement eNotNullElement = new XmlElement("if"); //$NON-NLS-1$
        sb.setLength(0);
        sb.append("entity");
        sb.append(" != null"); //$NON-NLS-1$
        eNotNullElement.addAttribute(new Attribute(
                "test", sb.toString())); //$NON-NLS-1$

        sb.setLength(0);
        sb.append("(1=0");
        eNotNullElement.addElement(new TextElement(sb.toString()));
        sb.setLength(0);
        sb.append(")");
        eNotNullElement.addElement(new TextElement(sb.toString()));
        //

        //
        for(IntrospectedColumn column:introspectedTable.getPrimaryKeyColumns()){
            XmlElement idNotNullElement = new XmlElement("if");
            sb.setLength(0);
            sb.append("entity.");
            sb.append(column.getJavaProperty());
            sb.append(" != null"); //$NON-NLS-1$
            idNotNullElement.addAttribute(new Attribute("test",sb.toString()));
            sb.setLength(0);
            sb.append("and ");
            sb.append(MyBatis3FormattingUtilities.getAliasedActualColumnName(column));
            sb.append(" != ");
            sb.append("#{entity.");
            sb.append(column.getActualColumnName());
            sb.append(",jdbcType=");
            sb.append(column.getJdbcTypeName());
            sb.append("}");
            idNotNullElement.addElement(new TextElement(sb.toString()));
            eNotNullElement.addElement(idNotNullElement);
        }

        answer.addElement(eNotNullElement);

//        Iterator<IntrospectedColumn> iter = introspectedTable
//                .getNonBLOBColumns().iterator();
//        while (iter.hasNext()) {
//            sb.append(MyBatis3FormattingUtilities.getSelectListPhraseNotAs(iter
//                    .next()));
//
//            if (iter.hasNext()) {
//                sb.append(", "); //$NON-NLS-1$
//            }
//
//            if (sb.length() > 80) {
//                answer.addElement(new TextElement(sb.toString()));
//                sb.setLength(0);
//            }
//        }

        sb.setLength(0);
        if (sb.length() > 0) {
            answer.addElement((new TextElement(sb.toString())));
        }

        parentElement.addElement(answer);
    }
}
