package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * @author wangbo
 * @description
 * @date 2017/9/6
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class JasperReportPdfView extends JasperReportsSingleFormatView implements ReportExportInterface {

    public JasperReportPdfView() {
        setContentType("application/pdf");
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
