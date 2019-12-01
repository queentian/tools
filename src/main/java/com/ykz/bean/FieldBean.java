package com.ykz.bean;



import java.util.ArrayList;
import java.util.List;

public class FieldBean {

    private String originData;
    private String metadata;
    private String length;
    private String type;
    private String belongTo;
    private String startFlag;
    private List<FieldBean> childField = new ArrayList<>();

    public FieldBean() {
    }

    public FieldBean(String originData, String metadata, String length,
                     String type, String belongTo, String startFlag,
                     List<FieldBean> childField) {
        this.originData = originData;
        this.metadata = metadata;
        this.length = length;
        this.type = type;
        this.belongTo = belongTo;
        this.startFlag = startFlag;
        this.childField = childField;
    }

    @Override
    public String toString() {
        return "FieldBean{" +
                "originData='" + originData + '\'' +
                ", metadata='" + metadata + '\'' +
                ", length='" + length + '\'' +
                ", type='" + type + '\'' +
                ", belongTo='" + belongTo + '\'' +
                ", startFlag='" + startFlag + '\'' +
                ", childField=" +
                childField.size()+
                '}';
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

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getStartFlag() {
        return startFlag;
    }

    public void setStartFlag(String startFlag) {
        this.startFlag = startFlag;
    }

    public List<FieldBean> getChildField() {
        return childField;
    }

    public void setChildField(List<FieldBean> childField) {
        this.childField = childField;
    }
}
