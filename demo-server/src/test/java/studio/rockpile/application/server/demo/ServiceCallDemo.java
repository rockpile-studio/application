package studio.rockpile.application.server.demo;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import studio.rockpile.application.DemoServer;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.provider.PaymentProvider;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ServiceCallDemo {
	@Autowired
	private PaymentProvider paymentProvider;
	
	@Autowired
	private StringEncryptor encryptor;

	// 部署时，设置/etc/profile，export JASYPT_PASSWORD = lCs1T3aOxWQU2jWZw
	// java -Xms128m -Xmx128m -Djasypt.encryptor.password=lCs1T3aOxWQU2jWZw -jar demo-server.jar
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
			Date ts = Calendar.getInstance().getTime();
			Payment payment = new Payment();
			payment.setOrderId(ts.getTime());
			payment.setAmount(new BigDecimal(100.126).setScale(2, BigDecimal.ROUND_DOWN));
			payment.setPayTime(ts);
			payment.setRemark("测试支付记录");
			payment.setFallback(false);
			paymentProvider.save(payment);

			System.out.println("insert t_payment, PK=" + payment.getId());

			QueryWrapper<Payment> query = new QueryWrapper<>();
			query.eq("order_id", ts.getTime());
			List<Payment> list = paymentProvider.list(query);
			for (Payment one : list) {
				System.out.println("... " + one);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
