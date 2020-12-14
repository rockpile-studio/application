package studio.rockpile.application.server.demo;

import java.math.BigDecimal;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import studio.rockpile.application.DemoServer;
import studio.rockpile.application.framework.util.SpringContextUtil;
import studio.rockpile.application.model.entity.Account;
import studio.rockpile.application.server.demo.provider.AccountProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceCallDemo {
	@Autowired
	private AccountProvider accountProvider;

	@Autowired
	private StringEncryptor encryptor;

	// 部署时，设置/etc/profile，export JASYPT_PASSWORD = lCs1T3aOxWQU2jWZw
	// java -Xms128m -Xmx128m -Djasypt.encryptor.password=${JASYPT_PASSWORD} -jar demo-server.jar
	// jasypt同一个密钥（secretKey）对同一个内容执行加密，每次生成的密文都是不一样的，但是根据密文解密成原内容都是一致的
	@Test
	public void envPasswordEncryptor() {
		String username = "rockpile";
		String password = "rockpile";
		System.out.println("username : " + encryptor.encrypt(username));
		System.out.println("password : " + encryptor.encrypt(password));
	}

	@Test
	public void test() {
		try {
			Account account = new Account();
			account.setId(5030000L);
			account.setName("rockpile");
			account.setType(1); // 买家
			account.setBalance(new BigDecimal(100000).setScale(2, BigDecimal.ROUND_DOWN));
			accountProvider.save(account);
			System.out.println("insert t_account(5030000), update_time=" + account.getUpdateTime());

			account.setId(5030001L);
			accountProvider.save(account);
			System.out.println("insert t_account(5030001), update_time=" + account.getUpdateTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
