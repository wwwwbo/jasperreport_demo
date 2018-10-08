package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @author wangbo
 * @description
 * @date 2017/9/6
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class JasperReportXlsView extends JasperReportsSingleFormatView implements ReportExportInterface {

    public JasperReportXlsView() {
        setContentType("application/vnd.ms-excel");
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
