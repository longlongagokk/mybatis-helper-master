package org.mybatis.generator.api.dom.java;

import org.mybatis.generator.api.dom.OutputUtilities;

import java.util.*;

import static org.mybatis.generator.api.dom.OutputUtilities.calculateImports;
import static org.mybatis.generator.api.dom.OutputUtilities.javaIndent;
import static org.mybatis.generator.api.dom.OutputUtilities.newLine;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;

/**
 * @author : lether
 * @date : 2018/10/18 23:02
 **/
public class Classes extends Interface{
    public Classes(FullyQualifiedJavaType type) {
        super(type);
        annotations = new ArrayList<String>();
        implementsClasses = new TreeSet<FullyQualifiedJavaType>();
        fields = new ArrayList<Field>();
    }
    public Classes(String type) {
        super(type);
        annotations = new ArrayList<String>();
        implementsClasses = new TreeSet<FullyQualifiedJavaType>();
        fields = new ArrayList<Field>();
    }
    private List<String> annotations;
    private Set<FullyQualifiedJavaType> implementsClasses;
    private List<Field> fields;
    public List<String> getAnnotations() {
        return annotations;
    }
    public void addAnnotation(String annotation) {
        annotations.add(annotation);
    }

    public Set<FullyQualifiedJavaType> getImplementsClasses() {
        return Collections.unmodifiableSet(implementsClasses);
    }
    public void addImplementsClasses(FullyQualifiedJavaType implementsClass) {
        implementsClasses.add(implementsClass);
    }

    /**
     * @return Returns the fields.
     */
    public List<Field> getFields() {
        return fields;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    @Override
    public String getFormattedContent() {
        StringBuilder sb = new StringBuilder();

        for (String commentLine : super.getFileCommentLines()) {
            sb.append(commentLine);
            newLine(sb);
        }

        if (stringHasValue(getType().getPackageName())) {
            sb.append("package "); //$NON-NLS-1$
            sb.append(getType().getPackageName());
            sb.append(';');
            newLine(sb);
            newLine(sb);
        }

        for (String staticImport : super.getStaticImports()) {
            sb.append("import static "); //$NON-NLS-1$
            sb.append(staticImport);
            sb.append(';');
            newLine(sb);
        }

        if (super.getStaticImports().size() > 0) {
            newLine(sb);
        }

        //@Component
        //super.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Component"));


        Set<String> importStrings = calculateImports(super.getImportedTypes());
        for (String importString : importStrings) {
            sb.append(importString);
            newLine(sb);
        }

        if (importStrings.size() > 0) {
            newLine(sb);
        }

        int indentLevel = 0;

        addFormattedJavadoc(sb, indentLevel);
        addFormattedAnnotations(sb, indentLevel);

        //add annotation
        for(String annotation:getAnnotations()){
            sb.append(annotation);
            newLine(sb);
        }
        sb.append(getVisibility().getValue());

        if (isStatic()) {
            sb.append("static "); //$NON-NLS-1$
        }

        if (isFinal()) {
            sb.append("final "); //$NON-NLS-1$
        }

        sb.append("class "); //$NON-NLS-1$
        sb.append(getType().getShortName());

        if (getSuperInterfaceTypes().size() > 0) {
            sb.append(" extends "); //$NON-NLS-1$

            for (FullyQualifiedJavaType fqjt : getSuperInterfaceTypes()) {
                sb.append(fqjt.getShortName());
                sb.append(","); //$NON-NLS-1$
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if(getImplementsClasses().size() > 0){
            sb.append(" implements "); //$NON-NLS-1$

            for (FullyQualifiedJavaType fqjt : getImplementsClasses()) {
                sb.append(fqjt.getShortName());
                sb.append(","); //$NON-NLS-1$
            }
            sb.deleteCharAt(sb.length() - 1);
        }

        sb.append(" {"); //$NON-NLS-1$
        indentLevel++;

        Iterator<Field> fldIter = fields.iterator();
        if(fldIter.hasNext()){
            OutputUtilities.newLine(sb);
        }
        while (fldIter.hasNext()) {
            OutputUtilities.newLine(sb);
            Field field = fldIter.next();
            sb.append(field.getFormattedContent(indentLevel));
        }

        Iterator<Method> mtdIter = getMethods().iterator();
        if(mtdIter.hasNext()){
            OutputUtilities.newLine(sb);
        }
        while (mtdIter.hasNext()) {
            OutputUtilities.newLine(sb);
            Method method = mtdIter.next();
            sb.append(method.getFormattedContent(indentLevel, false));
            if (mtdIter.hasNext()) {
                OutputUtilities.newLine(sb);
            }
        }

        indentLevel--;
        newLine(sb);
        javaIndent(sb, indentLevel);
        sb.append('}');

        return sb.toString();
    }
}
