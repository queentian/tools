package com.ykz.GenerateConfigs;

public interface GenerateConfigs {

    /**
     * 创建 XML 文件
     */
    void generate();

    /**
     * 创建 properties 文件
     */
    void generateProperties();

    /**
     * 对比并创建 元数据文件
     */
    void generateMetadata();

    /**
     * 创建服务识别文件
     */
    void generateServiceIdentify();

    /**
     * 创建系统识别文件
     */
    void generateSystemIdentify();
}
