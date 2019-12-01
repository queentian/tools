package com.ykz.bean;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class XmlBean {

    private List<FieldBean> sysHeadList = new ArrayList<>();
    private List<FieldBean> localHeadList = new ArrayList<>();
    private List<FieldBean> appHeadList = new ArrayList<>();
    private List<FieldBean> bodyList = new ArrayList<>();

}
