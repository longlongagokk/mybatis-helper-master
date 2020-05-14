package org.mybatis.generator.config;

import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.XmlElement;

import java.util.List;

import static org.mybatis.generator.internal.util.StringUtility.stringHasValue;
import static org.mybatis.generator.internal.util.messages.Messages.getString;

/**
 * @author : lether
 * @date : 2018/10/18 21:44
 **/
public class JavaConsoleControllerGeneratorConfiguration extends JavaClientGeneratorConfiguration {
    private String basePath;
    public String getBasePath() {
        return basePath;
    }
    public void setBasePath(String basePath) {
        this.basePath = basePath;
    }
    @Override
    public XmlElement toXmlElement() {
        XmlElement answer = new XmlElement("javaConsoleControllerGenerator"); //$NON-NLS-1$
        if (getConfigurationType() != null) {
            answer.addAttribute(new Attribute("type", getConfigurationType())); //$NON-NLS-1$
        }

        if (super.getTargetPackage() != null) {
            answer.addAttribute(new Attribute("targetPackage", super.getTargetPackage())); //$NON-NLS-1$
        }
        if (getBasePath() != null) {
            answer.addAttribute(new Attribute("bashPath", getBasePath())); //$NON-NLS-1$
        }

        if (super.getTargetProject() != null) {
            answer.addAttribute(new Attribute("targetProject", super.getTargetProject())); //$NON-NLS-1$
        }

        if (super.getImplementationPackage() != null) {
            answer.addAttribute(new Attribute("implementationPackage", super.getImplementationPackage())); //$NON-NLS-1$
        }

        addPropertyXmlElements(answer);

        return answer;
    }
    @Override

    public void validate(List<String> errors, String contextId) {
        if (!stringHasValue(getTargetPackage())) {
            errors.add(getString("ValidationError.2", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(getTargetProject())) {
            errors.add(getString("ValidationError.12", //$NON-NLS-1$
                    "javaConsoleControllerGenerator", contextId)); //$NON-NLS-1$
        }

        if (!stringHasValue(getConfigurationType())) {
            errors.add(getString("ValidationError.20", //$NON-NLS-1$
                    contextId));
        }
    }
}
