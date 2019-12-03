package com.ykz.utils;

import com.ykz.bean.BlockBean;
import com.ykz.bean.FieldBean;
import org.apache.logging.log4j.util.Strings;

import java.util.Map;
import java.util.Objects;

public class GenerateConfigUtils {

    public static void saveRow(Map<String, String> dataMap, StackBean stackBean, BlockBean bb) {

        // 否则提取有用的数据
        FieldBean fieldBean = new FieldBean();
        if (dataMap.containsKey("K")) {
            if ("end".equalsIgnoreCase(dataMap.get("K"))) {
                fieldBean.setStartFlag("end");
                stackBean.pop();
                return;
            }
        }
        // 原始数据
        fieldBean.setOriginData(dataMap.get("A"));
        fieldBean.setMetadata(dataMap.get("G"));
        if (dataMap.containsKey("J")){
            fieldBean.setBelongTo(dataMap.get("J"));
        }
        if (dataMap.containsKey("D")){
            fieldBean.setLength(StringFormatUtil.getLength(dataMap.get("D")));
        }
        fieldBean.setType(StringFormatUtil.getType(dataMap.get("I")));

        if (dataMap.containsKey("K")){
            if ("start".equalsIgnoreCase(dataMap.get("K"))){
                fieldBean.setStartFlag("start");
                judeBelong(fieldBean, stackBean, bb);
                stackBean.push(fieldBean);
                return;
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
        if (Strings.isBlank(fieldBean.getBelongTo())){
            bb.getBodyList().add(fieldBean);
        }
    }
}
