package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

public interface ReportExportInterface {

    void export(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response)
            throws JRException, IOException;
}
