package com.ykz.service;

import com.ykz.model.ReadExcelModel;

public interface ReadExcelService {

    String createSingleConfigs(ReadExcelModel readExcelModel);

    String getAllSheetName(String filePath);


}
