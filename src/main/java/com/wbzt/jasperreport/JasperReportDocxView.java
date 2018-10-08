package com.wbzt.jasperreport;


import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;

/**
 * @author wangbo
 * @description
 * @date 2017/9/6
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class JasperReportDocxView extends JasperReportsSingleFormatView implements ReportExportInterface {

    public JasperReportDocxView() {
        setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
    }

    @Override
    protected net.sf.jasperreports.engine.JRExporter createExporter() {
        return new JRDocxExporter();
    }

    @Override
    protected boolean useWriter() {
        return false;
    }

}
