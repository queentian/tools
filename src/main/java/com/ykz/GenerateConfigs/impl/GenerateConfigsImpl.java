package com.ykz.GenerateConfigs.impl;

import com.ykz.GenerateConfigs.GenerateConfigs;
import com.ykz.bean.BlockBean;
import com.ykz.bean.FieldBean;
import com.ykz.bean.GenerateRule;
import com.ykz.utils.GenerateConfigUtils;
import org.apache.logging.log4j.util.Strings;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GenerateConfigsImpl implements GenerateConfigs {

    @Override
    public Document generateRuleDocument(GenerateRule rule) {

        // 创建根节点
        Document document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement(rule.getRootName());
        addAttribute(root, rule.getRootAttributes());
        // 创建父节点
        Map<String, String> beto = rule.getBelongTo();
        createElement(root, beto);
        document.add(root);
        return document;

    }


    private void addAttribute(Element element, Map<String, String> ruleMap){
        if (!ruleMap.isEmpty()){
            ruleMap.forEach(element::addAttribute);
        }
    }

    private void createElement(Element rootElement, Map<String, String> map) {
        if (!map.isEmpty()){
            map.forEach((k,v) -> {
                if (rootElement.element(v) == null){
                    rootElement.add(DocumentHelper.createElement(v));
                }
            });
        }
    }

    @Override
    public void createServiceCode(String svcCd, String svcScn, Document document){
        Element svcCdE = DocumentHelper.createElement("SvcCd");
        svcCdE.addAttribute("expression", "'"+ svcCd+ "'");
        svcCdE.addAttribute("metadataid", "SvcCd");
        svcCdE.addAttribute("isSdoHead", "true");

        Element svcScnE = DocumentHelper.createElement("SvcScn");
        svcScnE.addAttribute("expression", "'"+ svcScn+ "'");
        svcScnE.addAttribute("metadataid", "SvcCd");
        svcScnE.addAttribute("isSdoHead", "true");
        Element root = document.getRootElement();
        Element sys = root.element("SYS_HEAD");
        if (sys != null){
            sys.add(svcCdE);
            sys.add(svcScnE);
        }
    }

    @Override
    public void createField(GenerateRule rule, Document document, BlockBean blockBean) {
        List<FieldBean> sysHeadList = blockBean.getSysHeadList();
        List<FieldBean> localHeadList = blockBean.getLocalHeadList();
        List<FieldBean> appHeadList = blockBean.getAppHeadList();
        List<FieldBean> bodyList = blockBean.getBodyList();
        Element rootElement = document.getRootElement();
        createFieldElement(rule, sysHeadList, rootElement.element(rule.getBelongTo().get("SYS_HEAD")));
        createFieldElement(rule, localHeadList, rootElement.element(rule.getBelongTo().get("LOCAL_HEAD")));
        createFieldElement(rule, appHeadList, rootElement.element(rule.getBelongTo().get("APP_HEAD")));
        createFieldElement(rule, bodyList, rootElement.element(rule.getBelongTo().get("default")));

    }

    private void createFieldElement(GenerateRule rule, List<FieldBean> list, Element father){
        if (list == null){
            return;
        }
        String or = rule.getOriginData();
        for (FieldBean fieldBean: list){
            Element element = null;

            // 判断是否是数组
            if ("array".equalsIgnoreCase(fieldBean.getType())){
                if (Objects.equals(or, "originData") && Strings.isNotBlank(fieldBean.getOriginData())){
                    element = DocumentHelper.createElement(fieldBean.getOriginData());
                    addAttribute(element, rule.getArrayAttributes());
                }else if (Objects.equals(or, "metadata") && Strings.isNotBlank(fieldBean.getMetadata())){
                    element = DocumentHelper.createElement(fieldBean.getMetadata());
                    addAttribute(element, rule.getArrayAttributes());
                }
                if (Objects.equals(or, "originData") && Strings.isBlank(fieldBean.getOriginData())){
                    createFieldElement(rule, fieldBean.getChildField(), father);
                }else if (element != null){
                    element.addAttribute("metadataid", fieldBean.getMetadata());
                    createFieldElement(rule, fieldBean.getChildField(), element);
                    father.add(element);
                }
            }else if ("struct".equalsIgnoreCase(fieldBean.getType())){
                if (Objects.equals(or, "originData") && Strings.isNotBlank(fieldBean.getOriginData())){
                    element = DocumentHelper.createElement(fieldBean.getOriginData());
                }else if (Objects.equals(or, "metadata") && Strings.isNotBlank(fieldBean.getMetadata())){
                    element = DocumentHelper.createElement(fieldBean.getMetadata());
                }
                if (Objects.equals(or, "originData") && Strings.isBlank(fieldBean.getOriginData())){
                    createFieldElement(rule, fieldBean.getChildField(), father);
                }else {
                    createFieldElement(rule, fieldBean.getChildField(), element);
                }
                father.add(element);
            }else{
                if (Objects.equals(or, "originData") && Strings.isNotBlank(fieldBean.getOriginData())){
                    element = DocumentHelper.createElement(fieldBean.getOriginData());
                    addAttribute(element, rule.getFieldAttributes());
                }else if (Objects.equals(or, "metadata") && Strings.isNotBlank(fieldBean.getMetadata())){
                    element = DocumentHelper.createElement(fieldBean.getMetadata());
                    addAttribute(element, rule.getFieldAttributes());
                }else if (Objects.equals(or, "originData") && Strings.isBlank(fieldBean.getOriginData())){
                    element = DocumentHelper.createElement(fieldBean.getMetadata());
                    addAttribute(element, rule.getFieldAttributes());
                }
                if (element != null){
                    element.addAttribute("metadataid", fieldBean.getMetadata());
                }
                father.add(element);

            }
        }
    }


}
