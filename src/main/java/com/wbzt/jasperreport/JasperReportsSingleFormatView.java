package com.wbzt.jasperreport;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JasperPrint;
import org.springframework.ui.jasperreports.JasperReportsUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.view.jasperreports.AbstractJasperReportsSingleFormatView;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * @author wangbo
 * @description
 * @date 2017/9/7
 */
@SuppressWarnings({"deprecation", "rawtypes"})
public class JasperReportsSingleFormatView extends AbstractJasperReportsSingleFormatView implements ReportExportInterface {
    @Override
    protected JRExporter createExporter() {
        return null;
    }

    @Override
    protected boolean useWriter() {
        return false;
    }

    @Override
    public void export(JasperPrint populatedReport, Map<String, Object> model, HttpServletResponse response) throws JRException, IOException {
        net.sf.jasperreports.engine.JRExporter exporter = createExporter();

        Map<net.sf.jasperreports.engine.JRExporterParameter, Object> mergedExporterParameters = getConvertedExporterParameters();
        if (!CollectionUtils.isEmpty(mergedExporterParameters)) {
            exporter.setParameters(mergedExporterParameters);
        }

        if (useWriter()) {
            // Copy the encoding configured for the report into the response.
            String contentType = getContentType();
            String encoding = (String) exporter.getParameter(net.sf.jasperreports.engine.JRExporterParameter.CHARACTER_ENCODING);
            if (encoding != null) {
                // Only apply encoding if constant type is specified but does not contain charset clause already.
                if (contentType != null && !contentType.toLowerCase().contains(WebUtils.CONTENT_TYPE_CHARSET_PREFIX)) {
                    contentType = contentType + WebUtils.CONTENT_TYPE_CHARSET_PREFIX + encoding;
                }
            }
            response.setContentType(contentType);

            // Render report into HttpServletResponse's Writer.
            JasperReportsUtils.render(exporter, populatedReport, response.getWriter());
        } else {
            ByteArrayOutputStream baos = createTemporaryOutputStream();
            JasperReportsUtils.render(exporter, populatedReport, baos);
            response.setContentType(getContentType());
            response.setContentLength(baos.size());

            // Flush byte array to servlet output stream.
            ServletOutputStream out = response.getOutputStream();
            baos.writeTo(out);
            out.flush();
        }
    }
}
