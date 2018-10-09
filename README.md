# jasperreport_demo
项目中用到jasperreport，网上看了好几篇博客躺了几个坑才实际使用，这边简单记录下，方便以后直接拿来用。
这个工程内容是springboot集成jasperreport，通过接口下载或展示报表内容。

- maven依赖

```
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.5.10.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>4.3.11.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>net.sf.jasperreports</groupId>
            <artifactId>jasperreports</artifactId>
            <version>6.6.0</version>
        </dependency>
        <dependency>
            <groupId>org.apache.poi</groupId>
            <artifactId>poi</artifactId>
            <version>3.10.1</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
```

- 报表输出的逻辑
通过ViewResolver的方式，根据controller中返回ModelAndView中viewName定位到要渲染的报表文件。

```
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
```

- controller编写,demo中接口地址 http://127.0.0.1:8080/report?reportName=test_report&format=pdf

```
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
```

- pdf格式中文展示问题
demo里报表文字用的华文宋体，通过fonts.xml、STSONG.TTF、jasperreports_extension.properties三个文件解决。
