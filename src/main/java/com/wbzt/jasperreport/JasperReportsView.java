package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.web.servlet.view.jasperreports.JasperReportsMultiFormatView;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wangbo
 * @description
 * @date 2017/9/6
 */
public class JasperReportsView extends JasperReportsMultiFormatView {

    private Map<String, Class<? extends ReportExportInterface>> formatMappings = new HashMap<>(4);

    public JasperReportsView() {
        formatMappings.put("pdf", JasperReportPdfView.class);
        formatMappings.put("xls", JasperReportXlsView.class);
        formatMappings.put("xlsx", JasperReportXlsxView.class);
        formatMappings.put("docx", JasperReportDocxView.class);
    }

    @Override
    public void renderReport(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response)
            throws Exception {

        String format = (String) model.get("format");
        if (format == null) {
            throw new IllegalArgumentException("No format found in model");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Rendering report using format mapping key [" + format + "]");
        }

        Class viewClass = this.formatMappings.get(format);
        if (viewClass == null) {
            throw new IllegalArgumentException("Format discriminator [" + format + "] is not a configured mapping");
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Rendering report using view class [" + viewClass.getName() + "]");
        }


        ReportExportInterface view = (ReportExportInterface) viewClass.newInstance();
        // Can skip most initialization since all relevant URL processing
        // has been done - just need to convert parameters on the sub view.
        //view.setExporterParameters(getExporterParameters());

        if (model.containsKey("download")) {
            setDownloadContentDisposition(response, format, (String) model.get("reportName"));
        }

        // Prepare response and render report.
        view.export(populatedReport, model, response);
    }

    private void setDownloadContentDisposition(HttpServletResponse response, String format, String reportName) throws UnsupportedEncodingException {
        StringBuffer contentDisposition = new StringBuffer()
                .append("attachment;filename=\"")
                .append(new String(reportName.getBytes("GBK"), "iso-8859-1"))
                .append(".")
                .append(format)
                .append("\"");
        response.setHeader(HEADER_CONTENT_DISPOSITION, contentDisposition.toString());
    }
}
