package com.wbzt.web;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class ReportController {

    private static final String REPORT_NAME = "reportName";
    private static final String FILE_FORMAT = "format";
    private static final String DATASOURCE = "datasource";


    @GetMapping("/report")
    public ModelAndView getSceneReport(final ModelMap modelMap,
                                       String reportName,
                                       String format,
                                       String download) {
        if (!StringUtils.isEmpty(download)) {
            modelMap.put("download", download);
            modelMap.put("reportName", reportName);
        }
        modelMap.put(DATASOURCE, getListSource());
        modelMap.put(FILE_FORMAT, format);
        modelMap.put("exporterParameters", new HashMap());
        return new ModelAndView(reportName, modelMap);
    }

    private List getListSource() {
        List<User> list = new ArrayList<>();
        list.add(new User("123", "a"));
        list.add(new User("小明", "男"));
        list.add(new User("小红", "女"));
        return list;
    }


}


