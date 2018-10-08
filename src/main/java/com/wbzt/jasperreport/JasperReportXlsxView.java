package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @author wangbo
 * @description
 * @date 2017/9/6
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class JasperReportXlsxView extends JasperReportsSingleFormatView implements ReportExportInterface {

    public JasperReportXlsxView() {
        setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Override
    public net.sf.jasperreports.engine.JRExporter createExporter() {
        return new JRPdfExporter();
    }

    @Override
    public boolean useWriter() {
        return false;
    }
}
