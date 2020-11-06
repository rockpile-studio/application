package studio.rockpile.application.server.demo;

import java.text.SimpleDateFormat;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.util.StopWatch;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

import studio.rockpile.application.framework.protocol.Pagination;
import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;

public class Temporary {

	private static ObjectMapper jsonMapper = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
			.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE).setSerializationInclusion(Include.NON_NULL)
			.configure(SerializationFeature.INDENT_OUTPUT, false)
			.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
			.registerModule(new SimpleModule().addSerializer(Long.class, ToStringSerializer.instance)
					.addSerializer(Long.TYPE, ToStringSerializer.instance));
	
	@Test
	public void getSnowFlakeId() {
		try {
			StopWatch stopWatch = new StopWatch();
			stopWatch.start(); // 开始时间
			Long[] arr = new Long[300];
			for( int i=0; i<300; i++ ) {
				arr[i] = IdWorker.getId();
			}
			stopWatch.stop(); // 截止时间
			System.out.println("... 毫秒: " + stopWatch.getTotalTimeMillis() );
			System.out.println("... 纳秒: " + stopWatch.getTotalTimeNanos() );
			for (Long id : arr) {
				System.out.print("," + id );
			}
		}catch ( Exception e ) {
			e.printStackTrace();
		}
	}

	@Test
	public void objectToJson() {
		try {
			QueryPageParam<Payment> queryPage = new QueryPageParam<>();
			Pagination page = new Pagination(2, 2);
			queryPage.setPage(page);
			Payment query = new Payment();
			query.setAccountId(5030000L);
			queryPage.setQuery(query);
			String json = jsonMapper.writeValueAsString(queryPage);
			System.out.println("... json : " + json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
