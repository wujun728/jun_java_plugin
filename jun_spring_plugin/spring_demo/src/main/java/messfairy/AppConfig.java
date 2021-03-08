package messfairy;
import java.util.ArrayList;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
@Configuration
@EnableWebMvc
@ComponentScan({"messfairy"})
@Import({DatabaseConfig.class, WebMvcConfig.class})
public class AppConfig{
    @Autowired
    private DatabaseConfig dataConfig;

    @Bean
    public DataSource blogSource() {
        // reference the dataSource() bean method
        return dataConfig.dataSource();
    }
    @Bean
    public StringHttpMessageConverter stringHttpMessageConverter() {
        // reference the dataSource() bean method
        return new StringHttpMessageConverter();
    }
    @Bean
    public FormHttpMessageConverter formHttpMessageConverter() {
        // reference the dataSource() bean method
        return new FormHttpMessageConverter();
    }
    @Bean
    public RequestMappingHandlerAdapter mappingHandlerAdapter() {
        // reference the dataSource() bean method
        RequestMappingHandlerAdapter mappingHandlerAdapter = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(stringHttpMessageConverter());
        converters.add(formHttpMessageConverter());
        mappingHandlerAdapter.setMessageConverters(converters);
        return mappingHandlerAdapter;
    }
}
