package org.mybatis.generator.codegen.mybatis3.querymodel;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.FullyQualifiedTable;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.Plugin;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaQueryGenerator;
import org.mybatis.generator.codegen.RootClassInfo;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.messages.Messages.getString;

public class SimpleQueryModelGenerator extends AbstractJavaQueryGenerator {
    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedTable table = introspectedTable.getFullyQualifiedTable();
        progressCallback.startTask(getString("Progress.8", table.toString())); //$NON-NLS-1$
        Plugin plugins = context.getPlugins();
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getBaseQueryModelType());
        TopLevelClass topLevelClass = new TopLevelClass(type);
        topLevelClass.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(topLevelClass);
        FullyQualifiedJavaType enumType = new FullyQualifiedJavaType("Fields");
        InnerEnum enumFields = new InnerEnum(enumType);

        List<IntrospectedColumn> introspectedColumns = getColumnsInThisClass();
        String rootClass = getRootClass();
        for (IntrospectedColumn introspectedColumn : introspectedColumns) {
            if (RootClassInfo.getInstance(rootClass, warnings)
                    .containsProperty(introspectedColumn)) {
               // continue;
            }

            Field ef = getJavaBeansField(introspectedColumn);
            enumFields.addEnumConstant(ef.getName());
        }
        //add super class
        FullyQualifiedJavaType superClass = getSuperClass();
        if (superClass != null) {
            topLevelClass.setSuperClass(superClass);
            topLevelClass.addImportedType(superClass);
        }
        topLevelClass.addImportedType(new FullyQualifiedJavaType("lombok.Data"));
        topLevelClass.addAnnotation("@Data");

        enumFields.setVisibility(JavaVisibility.PUBLIC);
//        Method constructor = new Method("Fields");
//        constructor.setConstructor(true);
//        constructor.addParameter(new Parameter(new FullyQualifiedJavaType("String"),"column"));
//        constructor.addBodyLine("this.column = column;");
//        enumFields.addMethod(constructor);
//        Method toString = new Method();
//        toString.setFinal(true);
//        toString.setName("toString");
//        toString.setReturnType(new FullyQualifiedJavaType("String"));
//        toString.setVisibility(JavaVisibility.PUBLIC);
//        toString.addBodyLine("return this.column;");
//        toString.addAnnotation("@Override");
//        enumFields.addMethod(toString);
        topLevelClass.addInnerEnum(enumFields);

        if (false) {
            //addParameterizedConstructor(topLevelClass);

            if (!introspectedTable.isImmutable()) {
                addDefaultConstructor(topLevelClass);
            }
        }

        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelBaseRecordClassGenerated(topLevelClass,introspectedTable)) {
            answer.add(topLevelClass);
        }
        return answer;
    }
    @Override
    protected void addDefaultConstructor(TopLevelClass topLevelClass) {
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setConstructor(true);
        method.setName(topLevelClass.getType().getShortName());
        method.addBodyLine("super( new "+introspectedTable.getFullyQualifiedTable().getDomainObjectName()+"());"); //$NON-NLS-1$
        context.getCommentGenerator().addGeneralMethodComment(method, introspectedTable);
        topLevelClass.addMethod(method);
    }

    private List<IntrospectedColumn> getColumnsInThisClass() {
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getAllColumns();
        return introspectedColumns;
    }
    private FullyQualifiedJavaType getSuperClass() {
        FullyQualifiedJavaType superClass;
        String rootClass = this.context.getJavaQueryModelGeneratorConfiguration().getRootClass();
        rootClass += "<"+introspectedTable.getBaseRecordType()+">";
        if (rootClass != null) {
            superClass = new FullyQualifiedJavaType(rootClass);
        } else {
            superClass = null;
        }

        return superClass;
    }
}
