package com.ykz.main;

import com.ykz.GenerateConfigs.GenerateConfigs;
import com.ykz.GenerateConfigs.impl.GenerateConfigsImpl;
import com.ykz.bean.BlockBean;
import com.ykz.bean.GenerateRule;
import com.ykz.bean.XmlBean;
import com.ykz.utils.BodySheet;
import com.ykz.utils.GenerateConfigUtils;
import org.dom4j.Document;


public class start {
    public static void main(String[] args) {
        Boolean isOut = true;
        String inModule = "/Users/yangkz/IdeaProjects/tools/configs/defaultxml.properties";
        String outModule = "/Users/yangkz/IdeaProjects/tools/configs/bzjxml.properties";
        XmlBean xb = null;
        try {
            BodySheet excel = new BodySheet();
            String file = "/Users/yangkz/IdeaProjects/tools/configs/广东农信_外联平台项目_中间业务云平台系统_接口规范定义_V1.1.8.xlsx";
            excel.process(file, 9);
            xb = excel.getXmlBean();
            System.out.println(xb);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (xb == null){
            System.out.println("读取Excel失败");
            return;
        }
        GenerateConfigs generateConfigs = new GenerateConfigsImpl();
        GenerateRule inRule = GenerateConfigUtils.readGenerateRule(inModule);
        GenerateRule outRule = GenerateConfigUtils.readGenerateRule(outModule);

        Document inChannelDocument = isOut? generateConfigs.generateRuleDocument(inRule):generateConfigs.generateRuleDocument(outRule);
        Document inServiceDocument = isOut? generateConfigs.generateRuleDocument(inRule):generateConfigs.generateRuleDocument(outRule);
        Document outChannelDocument = isOut? generateConfigs.generateRuleDocument(outRule):generateConfigs.generateRuleDocument(inRule);
        Document outServiceDocument = isOut? generateConfigs.generateRuleDocument(outRule):generateConfigs.generateRuleDocument(inRule);
        if (isOut){
            generateConfigs.createServiceCode(xb.getServiceCode(), xb.getServiceSine(), inChannelDocument);
            generateConfigs.createServiceCode(xb.getServiceCode(), xb.getServiceSine(), inServiceDocument);
        }else{
            generateConfigs.createServiceCode(xb.getServiceCode(), xb.getServiceSine(), outChannelDocument);
            generateConfigs.createServiceCode(xb.getServiceCode(), xb.getServiceSine(), outServiceDocument);
        }
        BlockBean inList = xb.getInList();
        BlockBean outList = xb.getOutList();

        generateConfigs.createField(inRule, inChannelDocument, inList);
        generateConfigs.createField(outRule, outServiceDocument, inList);
        generateConfigs.createField(inRule, inServiceDocument, outList);
        generateConfigs.createField(outRule, outChannelDocument, outList);

        System.out.println(inChannelDocument.asXML());
        System.out.println(outServiceDocument.asXML());
        System.out.println("---------------------");
        System.out.println(outChannelDocument.asXML());
        System.out.println(inServiceDocument.asXML());

    }
}
