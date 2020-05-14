package org.mybatis.generator.codegen.mybatis3.controller;

import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractConsoleControllerGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.elements.AbstractJavaMapperMethodGenerator;
import org.mybatis.generator.config.PropertyRegistry;

import java.util.ArrayList;
import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.setFirstLowerOfString;
import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author : lether
 * @date : 2018/10/18 22:14
 **/
public class JavaConsoleControllerGenerator extends AbstractConsoleControllerGenerator{

        /**
         *
         */
        public JavaConsoleControllerGenerator() {
            super();
        }

        @Override
        public List<CompilationUnit> getCompilationUnits() {
            progressCallback.startTask(getString("Progress.17", //$NON-NLS-1$
                    introspectedTable.getFullyQualifiedTable().toString()));
            CommentGenerator commentGenerator = context.getCommentGenerator();

            FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                    introspectedTable.getConsoleControllerType());
            Classes classes = new Classes(type);
            classes.setVisibility(JavaVisibility.PUBLIC);
            commentGenerator.addJavaFileComment(classes);

            String rootInterface = introspectedTable
                    .getTableConfigurationProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
            if (!stringHasValue(rootInterface)) {
                rootInterface = context.getJavaConsoleControllerGeneratorConfiguration()
                        .getProperty(PropertyRegistry.ANY_ROOT_INTERFACE);
            }

            if (stringHasValue(rootInterface)) {
                //BaseController<T,S,V>
                rootInterface = rootInterface.replace("#l","<");
                rootInterface = rootInterface.replace("#r",">");
                rootInterface = rootInterface.replace("$T",introspectedTable.getBaseRecordType());
                rootInterface = rootInterface.replace("$V",introspectedTable.getBaseViewModelType());
                FullyQualifiedJavaType fqjt = new FullyQualifiedJavaType(rootInterface);
                classes.addSuperInterface(fqjt);
                classes.addImportedType(fqjt);
            }else {
                //有继承不需要再进行编写了
            }
            String path =  context.getJavaConsoleControllerGeneratorConfiguration().getBasePath();

            //field
            FullyQualifiedJavaType serviceType = new FullyQualifiedJavaType(introspectedTable.getJavaServiceType());
            Field field0 = new Field(setFirstLowerOfString(serviceType.getShortName()),serviceType);
            classes.addField(field0);
            classes.addImportedType(serviceType);
            //inner-fields
            String innerFields = context.getJavaConsoleControllerGeneratorConfiguration()
                    .getProperty(PropertyRegistry.ANY_INPUT_INNER_FIELDS);
            if(stringHasValue(innerFields)) {
                String [] _inf = innerFields.split(",");
                for(String s:_inf){
                    FullyQualifiedJavaType t = new FullyQualifiedJavaType(s);
                    Field field1 = new Field(setFirstLowerOfString(t.getShortName()),t);
                    classes.addField(field1);
                    classes.addImportedType(t);
                }
            }
            classes.addImportedType(new FullyQualifiedJavaType("org.springframework.stereotype.Controller"));
            classes.addAnnotation("@Controller");
            classes.addImportedType(new FullyQualifiedJavaType("org.springframework.web.bind.annotation.RequestMapping"));
            classes.addAnnotation("@RequestMapping(\""+path+"\")");

            //constructor
            Method method = new Method("set"+serviceType.getShortName());
            method.getAnnotations().add("@Autowired");
            classes.addImportedType(new FullyQualifiedJavaType("org.springframework.beans.factory.annotation.Autowired"));
            method.setReturnType(new FullyQualifiedJavaType("void"));
            method.setVisibility(JavaVisibility.PUBLIC);
            Parameter cp0 = new Parameter(serviceType,field0.getName(),false);
            method.addBodyLine("this."+field0.getName()+" = " + field0.getName()+";");
            method.addBodyLine("viewModel = new ViewModel();");
            method.addBodyLine("viewModel.setBasePath(\""+path+"\");");
            method.addBodyLine("viewModel.setShowSort(false);");
            method.addBodyLine("super.setViewModel(viewModel);");
            if(classes.getImplementsClasses().size() > 0){

            }
            method.addBodyLine("super.setBaseService("+field0.getName()+");");
            method.addParameter(cp0);
            classes.addMethod(method);

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
