package studio.rockpile.application.framework.config;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

// Jackson全局转化long类型为String，解决jackson序列化时long类型缺失精度问题
@EnableWebMvc
@Configuration
public class WebDataConvertConfig implements WebMvcConfigurer {

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.setSerializationInclusion(Include.NON_NULL).configure(SerializationFeature.INDENT_OUTPUT, false)
				.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		// 因为js中数字类型不能包含所有的java的Long类型取值
		// 序列换成json时,将所有的long变成string
		SimpleModule simpleModule = new SimpleModule();
		simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
		simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
		objectMapper.registerModule(simpleModule);
		jackson2HttpMessageConverter.setObjectMapper(objectMapper);

		converters.add(jackson2HttpMessageConverter);
	}

}
