package com.ykz.utils;

import com.ykz.bean.BlockBean;
import com.ykz.bean.FieldBean;
import com.ykz.bean.GenerateRule;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

public class GenerateConfigUtils {

    public static void saveRow(Map<String, String> dataMap, StackBean stackBean, BlockBean bb) {
        // 判断是否是 结构体和数组的结束标志
        if (dataMap.containsKey("M")) {
            if ("end".equalsIgnoreCase(dataMap.get("M")) || dataMap.get("M").toLowerCase().startsWith("end")) {
                stackBean.pop();
                return;
            }
        }
        // 创建一个字段
        FieldBean fieldBean = new FieldBean();
        // 第三方字段
        fieldBean.setOriginData(dataMap.getOrDefault("A", ""));
        // 元数据
        fieldBean.setMetadata(dataMap.getOrDefault("H", ""));
        // 备注该字段在什么位置
        if (dataMap.containsKey("N") && StringUtils.isNotBlank("N")){
            fieldBean.setBelongTo(dataMap.get("N"));
        }else if (dataMap.containsKey("M")){
            fieldBean.setBelongTo(dataMap.get("M"));
        }
        // 长度字段和类型
        if (dataMap.containsKey("J")){
            fieldBean.setLength(StringFormatUtil.getLength(dataMap.get("J")));
        }
        fieldBean.setType(StringFormatUtil.getType(dataMap.get("J")));
        // 判断是否是数组或者结构体
        if (dataMap.containsKey("M")){
            if ("array".equalsIgnoreCase(fieldBean.getType()) || "struct".equalsIgnoreCase(fieldBean.getType())){
                if ("start".equalsIgnoreCase(dataMap.get("M")) || dataMap.get("M").toLowerCase().startsWith("start")){
                    fieldBean.setStartFlag("start");
                    judeBelong(fieldBean, stackBean, bb);
                    stackBean.push(fieldBean);
                    return;
                }
            }
        }
        judeBelong(fieldBean, stackBean, bb);
    }

    private static void judeBelong(FieldBean fieldBean, StackBean stackBean, BlockBean bb) {
        if (!stackBean.isEmpty()){
            FieldBean arrayFieldBean = (FieldBean) stackBean.peek();
            arrayFieldBean.getChildField().add(fieldBean);
            return;
        }
        if ("sys_head".equalsIgnoreCase(fieldBean.getBelongTo())){
            bb.getSysHeadList().add(fieldBean);
            return;
        }
        if ("local_head".equalsIgnoreCase(fieldBean.getBelongTo())){
            bb.getLocalHeadList().add(fieldBean);
            return;
        }
        if ("app_head".equalsIgnoreCase(fieldBean.getBelongTo())){
            bb.getAppHeadList().add(fieldBean);
            return;
        }
//        fieldBean.setBelongTo("body");
        bb.getBodyList().add(fieldBean);
    }

    public static Properties loadProperties(String moduleFile){
        Properties properties = new Properties();
        try {
            properties.load(FileUtils.openInputStream(new File(moduleFile)));
        } catch (IOException e) {
            System.out.println("加载" + moduleFile + "失败");
            e.printStackTrace();
        }
        return properties;
    }

    public static GenerateRule readGenerateRule(String moduleFile){
        Properties properties = loadProperties(moduleFile);
        GenerateRule rule = new GenerateRule();
        if (StringUtils.isBlank(properties.getProperty("rootName"))){
            System.out.println("生成规则错误：rootName 规则为空");
        }
        rule.setRootName(properties.getProperty("rootName"));
        if (StringUtils.isBlank(properties.getProperty("originData")) ||
                StringUtils.isBlank(properties.getProperty("metadata"))){
            System.out.println("生成规则错误：originData|metadata 规则为空");
        }
        rule.setOriginData(properties.getProperty("originData"));
        rule.setMetadata(properties.getProperty("metadata"));
        Map<String, String> rootAttributes = splitValue(properties.getProperty("rootAttributes"));
        Map<String, String> belongTo = splitValue(properties.getProperty("belongTo"));
        Map<String, String> arrayAttributes = splitValue(properties.getProperty("arrayAttributes"));
        Map<String, String> fieldAttributes = splitValue(properties.getProperty("fieldAttributes"));
        rule.setRootAttributes(rootAttributes);
        rule.setArrayAttributes(arrayAttributes);
        rule.setBelongTo(belongTo);
        rule.setFieldAttributes(fieldAttributes);
        return rule;
    }

    private static Map<String, String> splitValue(String attributesValue) {
        Map<String, String> rootAttributes = new HashMap<>();
        if (StringUtils.isNotBlank(attributesValue)){
            String[] value;
            if (attributesValue.contains(",")){
                String[] befValue = attributesValue.split(",");
                for (String str : befValue) {
                    value = str.split(":");
                    rootAttributes.put(value[0], value[1]);
                }
            }else{
                value = attributesValue.split(":");
                rootAttributes.put(value[0], value[1]);
            }
        }
        return rootAttributes;
    }

    public static void getValue(int curRow, int i, Map<String, String> dataMap, boolean isOut,
                                BlockBean inBlock, BlockBean outBlock, StackBean stackBean){
        if (curRow <= i){
            return;
        }
        // 如果是输入输出则直接结束
        if (Objects.equals(dataMap.get("A"), "输入")){
            return;
        }

        BlockBean bb = isOut? outBlock: inBlock;
        GenerateConfigUtils.saveRow(dataMap, stackBean, bb);
    }
}
