package com.wbzt.jasperreport;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.jasperreports.JasperReportsViewResolver;

@Configuration
public class JasperConfig extends WebMvcConfigurerAdapter {

    private final static String REPORT_DATA_KEY = "datasource";
    private final static String PATH_KEY = "classpath:jaspertemplate/";
    private final static String TYPE_KEY = ".jrxml";
    private final static String VIEW_KEY = "report";

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Bean
    public JasperReportsViewResolver getJasperReportsViewResolver() {
        JasperReportsViewResolver resolver = new JasperReportsViewResolver();
        //resource文件夹下放模板的路径
        resolver.setPrefix(PATH_KEY);
        //模板文件的类型，这里选用jrxml而不是编译之后的jasper
        resolver.setSuffix(TYPE_KEY);
        //JasperReportsMultiFormatView定义了ReportDataKey，这里给定key为datasource，后面controller的时候会用到
        resolver.setReportDataKey(REPORT_DATA_KEY);
        //视图名称，模板名称需要符合 *你定义的key* 如*Report*
        resolver.setViewNames("*" + VIEW_KEY + "*");
        //视图类
        resolver.setViewClass(JasperReportsView.class);
        resolver.setOrder(0);
        return resolver;
    }

}  