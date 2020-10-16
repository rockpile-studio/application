package studio.rockpile.application.server.demo;

import java.util.Calendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import studio.rockpile.application.DemoServer;
import studio.rockpile.application.server.demo.dao.AccountMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShardingJdbcDemo {
	@Autowired
	private AccountMapper accountMapper;
	
	@Test
	public void insertAccount() {
		try {
			Long accountId = Calendar.getInstance().getTimeInMillis();
			int rows = accountMapper.insert(accountId, "rockpile");
			System.out.println("... rows : " + rows);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
