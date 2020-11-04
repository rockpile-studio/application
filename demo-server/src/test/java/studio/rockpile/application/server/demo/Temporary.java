package studio.rockpile.application.server.demo;

import java.text.SimpleDateFormat;

import org.junit.Test;

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
