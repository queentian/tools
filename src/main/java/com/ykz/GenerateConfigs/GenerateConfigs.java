package com.ykz.GenerateConfigs;

import com.ykz.bean.BlockBean;
import com.ykz.bean.GenerateRule;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.Map;

public interface GenerateConfigs {

    /**
     * 创建 XML 文件
     */
    Document generateRuleDocument(GenerateRule rule);

    void createServiceCode(String svcCd, String svcScn, Document document);

    void createField(GenerateRule rule, Document document, BlockBean blockBean);



}
