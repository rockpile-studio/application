package studio.rockpile.application.framework.config;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

@Configuration
public class RestTemplatePoolConfig {

	@Autowired
	private RestHttpClientConfig config;

	@Bean
	@LoadBalanced
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		ObjectMapper jsonMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
				.setSerializationInclusion(JsonInclude.Include.NON_NULL)
				.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setObjectMapper(jsonMapper);

		List<HttpMessageConverter<?>> converters = new ArrayList<>();
		converters.add(converter);

		RestTemplate restTemplate = builder.build();
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		restTemplate.setMessageConverters(converters);
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setHttpClient(httpClientBuilder().build());
		clientHttpRequestFactory.setConnectTimeout(config.getConnectTimeout());
		clientHttpRequestFactory.setReadTimeout(config.getReadTimeout());
		clientHttpRequestFactory.setConnectionRequestTimeout(config.getConnReqTimeout());
		return clientHttpRequestFactory;
	}

	// 设置HTTP连接管理器，连接池相关配置管理，返回客户端链接管理器
	@Bean
	public HttpClientBuilder httpClientBuilder() {
		HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
		httpClientBuilder.setConnectionManager(poolingConnectionManager());
		return httpClientBuilder;
	}

	// 连接线程池管理，可以keep-alive不断开链接请求，这样速度会更快
	@Bean
	public HttpClientConnectionManager poolingConnectionManager() {
		PoolingHttpClientConnectionManager poolingConnectionManager = new PoolingHttpClientConnectionManager();
		poolingConnectionManager.setMaxTotal(config.getMaxTotal());
		poolingConnectionManager.setDefaultMaxPerRoute(config.getDefaultMaxPerRoute());
		poolingConnectionManager.setValidateAfterInactivity(config.getValidateAfterInactivity());
		return poolingConnectionManager;
	}
}
