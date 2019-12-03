package com.ykz.bean;

import lombok.Data;


@Data
public class XmlBean {
    private String serviceCode;
    private String serviceSine;
    private String txCode;
    private BlockBean inList;
    private BlockBean outList;
}
