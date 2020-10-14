package studio.rockpile.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Portal implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(Portal.class);

	public static void main(String[] args) {
		SpringApplication.run(Portal.class, args);
		logger.info("====== 启动成功 ======");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("====== 开始项目启动后执行功能 ======");
	}
}
