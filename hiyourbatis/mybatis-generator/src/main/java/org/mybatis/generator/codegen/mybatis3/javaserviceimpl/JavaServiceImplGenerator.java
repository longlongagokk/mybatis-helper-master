package org.mybatis.generator.codegen.mybatis3.javaserviceimpl;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaServiceImplGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.setFirstLowerOfString;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author : lether
 * @date : 2018/10/18 22:10
 **/
public class JavaServiceImplGenerator extends AbstractJavaServiceImplGenerator{

    /**
     *
     */
    public JavaServiceImplGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                introspectedTable.getFullyQualifiedTable().toString()));
        CommentGenerator commentGenerator = context.getCommentGenerator();

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getJavaServiceImplType());
        Classes classes = new Classes(type);
        classes.setVisibility(JavaVisibility.PUBLIC);
        commentGenerator.addJavaFileComment(classes);

        String rootInterface = introspectedTable
                .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        if (!stringHasValue(rootInterface)) {
            rootInterface = context.getJavaServiceImplGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
        }

        if (stringHasValue(rootInterface)) {
            //BaseServiceImpl<T,S,V>
            rootInterface = rootInterface.replace("#l","<");
            rootInterface = rootInterface.replace("#r",">");
            rootInterface = rootInterface.replace("$T",introspectedTable.getBaseRecordType());
            rootInterface = rootInterface.replace("$Q",introspectedTable.getBaseRequestModelType());
            rootInterface = rootInterface.replace("$V",introspectedTable.getBaseViewModelType());
            rootInterface = rootInterface.replace("$M",introspectedTable.getMyBatis3JavaMapperType());
            FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
            classes.addSuperInterface(fqjt);
            classes.addImportedType(fqjt);

        }else {
            //有继承不需要再进行编写了
        }
        classes.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Service"));
        classes.addAnnotation("@Component");
        //impl service
        FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(introspectedTable.getJavaServiceType());
        classes.addImportedType(serviceType);
        classes.addImplementsClasses(serviceType);

        //field
        FullyQualifiedJavaType mapperType = new FullyQualifiedJavaType(introspectedTable.getMyBatis3JavaMapperType());
//        Field field0 = new Field(setFirstLowerOfString(mapperType.getShortName()),mapperType);
//        field0.setFinal(true);
//        classes.addField(field0);
        classes.addImportedType(mapperType);

        //constructor
//        Method method = new Method(type.getShortName());
//        method.getAnnotations().add("@Autowired");
//        classes.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
//        method.setConstructor(true);
//        method.setVisibility(JavaVisibility.PUBLIC);
//        Parameter cp0 = new Parameter(mapperType,"mapper",false);
//        //method.addBodyLine("this."+field0.getName()+" = " + field0.getName()+";");
//        if(classes.getImplementsClasses().size() > 0){
//
//        }
//        method.addBodyLine("super(mapper);");
//        method.addParameter(cp0);
//        classes.addMethod(method);


        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().clientGenerated(classes, null,
                introspectedTable)) {
            answer.add(classes);
        }

        List<CompilationUnit> extraCompilationUnits = getExtraCompilationUnits();
        if (extraCompilationUnits != null) {
            answer.addAll(extraCompilationUnits);
        }

        return answer;
    }

    protected void initializeAndExecuteGenerator(
            AbstractJavaMapperMethodGenerator methodGenerator,
            Interface interfaze) {
        methodGenerator.setContext(context);
        methodGenerator.setIntrospectedTable(introspectedTable);
        methodGenerator.setProgressCallback(progressCallback);
        methodGenerator.setWarnings(warnings);
        methodGenerator.addInterfaceElements(interfaze);
    }

    public List<CompilationUnit> getExtraCompilationUnits() {
        return null;
    }
}
