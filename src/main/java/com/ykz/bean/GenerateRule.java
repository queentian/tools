package com.ykz.bean;

import java.util.Map;

public class GenerateRule {
    private String rootName;
    private Map<String, String> rootAttributes;
    private Map<String, String> belongTo;
    private String originData;
    private String metadata;
    private Map<String, String> arrayAttributes;
    private Map<String, String> fieldAttributes;

    public GenerateRule() {

    }

    public GenerateRule(String rootName, Map<String, String> rootAttributes, Map<String, String> belongTo,
                        String originData, String metadata, Map<String, String> arrayAttributes,
                        Map<String, String> fieldAttributes) {
        this.rootName = rootName;
        this.rootAttributes = rootAttributes;
        this.belongTo = belongTo;
        this.originData = originData;
        this.metadata = metadata;
        this.arrayAttributes = arrayAttributes;
        this.fieldAttributes = fieldAttributes;
    }

    public String getRootName() {
        return rootName;
    }

    public void setRootName(String rootName) {
        this.rootName = rootName;
    }

    public Map<String, String> getRootAttributes() {
        return rootAttributes;
    }

    public void setRootAttributes(Map<String, String> rootAttributes) {
        this.rootAttributes = rootAttributes;
    }

    public Map<String, String> getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(Map<String, String> belongTo) {
        this.belongTo = belongTo;
    }

    public String getOriginData() {
        return originData;
    }

    public void setOriginData(String originData) {
        this.originData = originData;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public Map<String, String> getArrayAttributes() {
        return arrayAttributes;
    }

    public void setArrayAttributes(Map<String, String> arrayAttributes) {
        this.arrayAttributes = arrayAttributes;
    }

    public Map<String, String> getFieldAttributes() {
        return fieldAttributes;
    }

    public void setFieldAttributes(Map<String, String> fieldAttributes) {
        this.fieldAttributes = fieldAttributes;
    }

    @Override
    public String toString() {
        return "GenerateRule{" +
                "rootName='" + rootName + '\'' +
                ", rootAttributes=" + rootAttributes +
                ", belongTo=" + belongTo +
                ", originData='" + originData + '\'' +
                ", metadata='" + metadata + '\'' +
                ", arrayAttributes=" + arrayAttributes +
                ", fieldAttributes=" + fieldAttributes +
                '}';
    }
}
