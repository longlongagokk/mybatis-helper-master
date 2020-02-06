/*
 *  Copyright 2009 The Apache Software Foundation
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.mybatis.generator.codegen.mybatis3;

import org.mybatis.generator.api.GeneratedJavaFile;
import org.mybatis.generator.api.GeneratedXmlFile;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.ProgressCallback;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.xml.Document;
import org.mybatis.generator.codegen.*;
import org.mybatis.generator.codegen.mybatis3.controller.JavaConsoleControllerGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.AnnotatedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.JavaMapperGenerator;
import org.mybatis.generator.codegen.mybatis3.javamapper.MixedClientGenerator;
import org.mybatis.generator.codegen.mybatis3.javaservice.JavaServiceGenerator;
import org.mybatis.generator.codegen.mybatis3.javaserviceimpl.JavaServiceImplGenerator;
import org.mybatis.generator.codegen.mybatis3.model.BaseRecordGenerator;
import org.mybatis.generator.codegen.mybatis3.model.ExampleGenerator;
import org.mybatis.generator.codegen.mybatis3.model.PrimaryKeyGenerator;
import org.mybatis.generator.codegen.mybatis3.model.RecordWithBLOBsGenerator;
import org.mybatis.generator.codegen.mybatis3.querymodel.SimpleQueryModelGenerator;
import org.mybatis.generator.codegen.mybatis3.requestmodel.SimpleRequestModelGenerator;
import org.mybatis.generator.codegen.mybatis3.viewmodel.SimpleViewModelGenerator;
import org.mybatis.generator.codegen.mybatis3.xmlmapper.XMLMapperGenerator;
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.ObjectFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Jeff Butler
 * 
 */
public class IntrospectedTableMyBatis3Impl extends IntrospectedTable {
    protected List<AbstractJavaGenerator> javaModelGenerators;
    protected List<AbstractJavaGenerator> javaQueryModelGenerators;
    protected List<AbstractJavaGenerator> javaRequestModelGenerators;
    protected List<AbstractJavaGenerator> javaViewModelGenerators;
    protected List<AbstractJavaGenerator> clientGenerators;
    protected List<AbstractJavaGenerator> serviceGenerators;
    protected List<AbstractJavaGenerator> serviceImplGenerators;
    protected List<AbstractJavaGenerator> consoleControllerGenerators;
    protected AbstractXmlGenerator xmlMapperGenerator;

    public IntrospectedTableMyBatis3Impl() {
        super(TargetRuntime.MYBATIS3);
        javaModelGenerators = new ArrayList<AbstractJavaGenerator>();
        javaQueryModelGenerators = new ArrayList<AbstractJavaGenerator>();
        javaRequestModelGenerators = new ArrayList<AbstractJavaGenerator>();
        javaViewModelGenerators = new ArrayList<AbstractJavaGenerator>();
        clientGenerators = new ArrayList<AbstractJavaGenerator>();
        serviceGenerators = new ArrayList<AbstractJavaGenerator>();
        serviceImplGenerators = new ArrayList<AbstractJavaGenerator>();
        consoleControllerGenerators = new ArrayList<AbstractJavaGenerator>();
    }

    @Override
    public void calculateGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        calculateJavaModelGenerators(warnings, progressCallback);

        //calculetaJavaQueryModels
        AbstractJavaQueryGenerator javaQueryGenerator = calculateJavaQueryModelGenerators(warnings,progressCallback);
        AbstractJavaRequestGenerator javaRequestGenerator = calculateJavaRequestModelGenerators(warnings,progressCallback);
        //calculateJavaViewModels
        AbstractJavaViewGenerator javaViewGenerator = calculateJavaViewModelGenerators(warnings,progressCallback);
        
        AbstractJavaClientGenerator javaClientGenerator = calculateClientGenerators(warnings, progressCallback);

        //service
        AbstractJavaServiceGenerator serviceGenerator = calculateServiceGenerators(warnings,progressCallback);

        //serviceimpl
        AbstractJavaServiceImplGenerator serviceImplGenerator = calculateServiceImplGenerators(warnings,progressCallback);

        //controller
        AbstractConsoleControllerGenerator consoleControllerGenerator = calculateConsoleControllerGenerators(warnings,progressCallback);
            
        calculateXmlMapperGenerator(javaClientGenerator, warnings, progressCallback);
    }


    protected void calculateXmlMapperGenerator(AbstractJavaClientGenerator javaClientGenerator, 
            List<String> warnings,
            ProgressCallback progressCallback) {
        if (javaClientGenerator == null) {
            if (context.getSqlMapGeneratorConfiguration() != null) {
                xmlMapperGenerator = new XMLMapperGenerator();
            }
        } else {
            xmlMapperGenerator = javaClientGenerator.getMatchedXMLGenerator();
        }
        
        initializeAbstractGenerator(xmlMapperGenerator, warnings,
                progressCallback);
    }

    /**
     * 
     * @param warnings
     * @param progressCallback
     * @return true if an XML generator is required
     */
    protected AbstractJavaClientGenerator calculateClientGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (!rules.generateJavaClient()) {
            return null;
        }
        
        AbstractJavaClientGenerator javaGenerator = createJavaClientGenerator();
        if (javaGenerator == null) {
            return null;
        }

        initializeAbstractGenerator(javaGenerator, warnings, progressCallback);
        clientGenerators.add(javaGenerator);
        
        return javaGenerator;
    }
    
    protected AbstractJavaClientGenerator createJavaClientGenerator() {
        if (context.getJavaClientGeneratorConfiguration() == null) {
            return null;
        }
        
        String type = context.getJavaClientGeneratorConfiguration().getConfigurationType();

        AbstractJavaClientGenerator javaGenerator;
        if ("XMLMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else if ("MIXEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new MixedClientGenerator();
        } else if ("ANNOTATEDMAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new AnnotatedClientGenerator();
        } else if ("MAPPER".equalsIgnoreCase(type)) { //$NON-NLS-1$
            javaGenerator = new JavaMapperGenerator();
        } else {
            javaGenerator = (AbstractJavaClientGenerator) ObjectFactory
                    .createInternalObject(type);
        }
        
        return javaGenerator;
    }

    protected AbstractJavaServiceGenerator calculateServiceGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaServiceGeneratorConfiguration() == null) {
            return null;
        }

        AbstractJavaServiceGenerator serviceGenerator = new JavaServiceGenerator();

        initializeAbstractGenerator(serviceGenerator, warnings, progressCallback);
        serviceGenerators.add(serviceGenerator);

        return serviceGenerator;
    }
    protected AbstractJavaServiceImplGenerator calculateServiceImplGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaServiceImplGeneratorConfiguration() == null) {
            return null;
        }

        AbstractJavaServiceImplGenerator serviceImplGenerator = new JavaServiceImplGenerator();

        initializeAbstractGenerator(serviceImplGenerator, warnings, progressCallback);
        serviceImplGenerators.add(serviceImplGenerator);

        return serviceImplGenerator;
    }
    protected AbstractConsoleControllerGenerator calculateConsoleControllerGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaConsoleControllerGeneratorConfiguration() == null) {
            return null;
        }

        AbstractConsoleControllerGenerator consoleControllerGenerator = new JavaConsoleControllerGenerator();

        initializeAbstractGenerator(consoleControllerGenerator, warnings, progressCallback);
        consoleControllerGenerators.add(consoleControllerGenerator);

        return consoleControllerGenerator;
    }

    protected void calculateJavaModelGenerators(List<String> warnings,
            ProgressCallback progressCallback) {
        if (getRules().generateExampleClass()) {
            AbstractJavaGenerator javaGenerator = new ExampleGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generatePrimaryKeyClass()) {
            AbstractJavaGenerator javaGenerator = new PrimaryKeyGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateBaseRecordClass()) {
            AbstractJavaGenerator javaGenerator = new BaseRecordGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }

        if (getRules().generateRecordWithBLOBsClass()) {
            AbstractJavaGenerator javaGenerator = new RecordWithBLOBsGenerator();
            initializeAbstractGenerator(javaGenerator, warnings,
                    progressCallback);
            javaModelGenerators.add(javaGenerator);
        }
    }

    protected AbstractJavaQueryGenerator calculateJavaQueryModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaQueryModelGeneratorConfiguration() == null) {
            return null;
        }

        //simpleQueryModelGenerator

        AbstractJavaQueryGenerator javaQueryGenerator = new SimpleQueryModelGenerator();
        initializeAbstractGenerator(javaQueryGenerator, warnings,progressCallback);
        javaQueryModelGenerators.add(javaQueryGenerator);
        return javaQueryGenerator;
    }

    protected AbstractJavaRequestGenerator calculateJavaRequestModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaRequestModelGeneratorConfiguration() == null) {
            return null;
        }

        //simpleQueryModelGenerator

        AbstractJavaRequestGenerator generator = new SimpleRequestModelGenerator();
        initializeAbstractGenerator(generator, warnings,progressCallback);
        javaRequestModelGenerators.add(generator);
        return generator;
    }
    protected AbstractJavaViewGenerator calculateJavaViewModelGenerators(List<String> warnings, ProgressCallback progressCallback) {
        if (context.getJavaViewModelGeneratorConfiguration() == null) {
            return null;
        }

        //simpleQueryModelGenerator

        AbstractJavaViewGenerator javaViewGenerator = new SimpleViewModelGenerator();
        initializeAbstractGenerator(javaViewGenerator, warnings,progressCallback);
        javaViewModelGenerators.add(javaViewGenerator);
        return javaViewGenerator;
    }

    protected void initializeAbstractGenerator(
            AbstractGenerator abstractGenerator, List<String> warnings,
            ProgressCallback progressCallback) {
        if (abstractGenerator == null) {
            return;
        }
        
        abstractGenerator.setContext(context);
        abstractGenerator.setIntrospectedTable(this);
        abstractGenerator.setProgressCallback(progressCallback);
        abstractGenerator.setWarnings(warnings);
    }

    @Override
    public List<GeneratedJavaFile> getGeneratedJavaFiles() {
        List<GeneratedJavaFile> answer = new ArrayList<GeneratedJavaFile>();

        for (AbstractJavaGenerator javaGenerator : javaModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaModelGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        //queryModels

        for (AbstractJavaGenerator javaGenerator : javaQueryModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaQueryModelGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        //reqModels

        for (AbstractJavaGenerator javaGenerator : javaRequestModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaRequestModelGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        //viewModels
        for (AbstractJavaGenerator javaGenerator : javaViewModelGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator.getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaViewModelGeneratorConfiguration().getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }


        //mapper.java
        for (AbstractJavaGenerator javaGenerator : clientGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaClientGeneratorConfiguration()
                                .getTargetProject(),
                                context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                                context.getJavaFormatter());
                answer.add(gjf);
            }
        }
        //service
        for (AbstractJavaGenerator javaGenerator : serviceGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaServiceGeneratorConfiguration()
                                .getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        //serviceimpl
        for (AbstractJavaGenerator javaGenerator : serviceImplGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaServiceImplGeneratorConfiguration()
                                .getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        //controller
        for (AbstractJavaGenerator javaGenerator : consoleControllerGenerators) {
            List<CompilationUnit> compilationUnits = javaGenerator
                    .getCompilationUnits();
            for (CompilationUnit compilationUnit : compilationUnits) {
                GeneratedJavaFile gjf = new GeneratedJavaFile(compilationUnit,
                        context.getJavaConsoleControllerGeneratorConfiguration()
                                .getTargetProject(),
                        context.getProperty(PropertyRegistry.CONTEXT_JAVA_FILE_ENCODING),
                        context.getJavaFormatter());
                answer.add(gjf);
            }
        }

        return answer;
    }

    @Override
    public List<GeneratedXmlFile> getGeneratedXmlFiles() {
        List<GeneratedXmlFile> answer = new ArrayList<GeneratedXmlFile>();

        if (xmlMapperGenerator != null) {
            Document document = xmlMapperGenerator.getDocument();
            GeneratedXmlFile gxf = new GeneratedXmlFile(document,
                getMyBatis3XmlMapperFileName(), getMyBatis3XmlMapperPackage(),
                context.getSqlMapGeneratorConfiguration().getTargetProject(),
                context.getSqlMapGeneratorConfiguration().isMergeable(),
                context.getXmlFormatter());
            if (context.getPlugins().sqlMapGenerated(gxf, this)) {
                answer.add(gxf);
            }
        }

        return answer;
    }

    @Override
    public int getGenerationSteps() {
        return javaModelGenerators.size() + clientGenerators.size() +
                javaModelGenerators.size() + serviceGenerators.size() +
                serviceImplGenerators.size() + consoleControllerGenerators.size() +
            (xmlMapperGenerator == null ? 0 : 1);
    }

    @Override
    public boolean isJava5Targeted() {
        return true;
    }

    @Override
    public boolean requiresXMLGenerator() {
        AbstractJavaClientGenerator javaClientGenerator =
            createJavaClientGenerator();
        
        if (javaClientGenerator == null) {
            return false;
        } else {
            return javaClientGenerator.requiresXMLGenerator();
        }
    }
}
