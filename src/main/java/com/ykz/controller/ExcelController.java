package com.ykz.controller;

import com.ykz.code.EmReturnCode;
import com.ykz.model.ResultData;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/excel")
@RestController
public class ExcelController extends BaseController {
    @PostMapping("/getAllSheetName")
    public ResultData getAllSheetName(String filePath){



        return new ResultData(EmReturnCode.SUCCESS);
    }
}
