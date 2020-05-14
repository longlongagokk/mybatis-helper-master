package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

import java.util.Iterator;

/**
 * creator : whh-lether
 * date    : 2018/10/18
 * time    : 13:49
 * desc    :
 **/
public class QueryConditionElementGenerator extends AbstractXmlElementGenerator {

    public QueryConditionElementGenerator() {
        super();
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$

        answer.addAttribute(new Attribute("id",introspectedTable.getSqlQueryConditionId()));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        XmlElement insertNotNullElement = new XmlElement("if"); //$NON-NLS-1$
        sb.setLength(0);
        sb.append("entity");
        sb.append(" != null"); //$NON-NLS-1$
        insertNotNullElement.addAttribute(new Attribute(
                "test", sb.toString())); //$NON-NLS-1$

        sb.setLength(0);
        insertNotNullElement.addElement(new TextElement(sb.toString()));
        answer.addElement(insertNotNullElement);

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
