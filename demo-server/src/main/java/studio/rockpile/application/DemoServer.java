package studio.rockpile.application;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//指定端口启动
//java -Xms128m -Xmx128m -Dserver.port=53012 -jar demo-server.jar --spring.config.additional-location=/opt/rockpile/server/demo/conf/
//在conf目录下配置当前微服务固有配置application-runtime.properties
//启动sentinel
//java -Xms128m -Xmx128m -Dlogging.file=./log/sentinel-dash.log -jar sentinel-dashboard-1.7.0.jar
@SpringBootApplication
@MapperScan("studio.rockpile.application.**.dao*")
public class DemoServer implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(DemoServer.class);

	public static void main(String[] args) {
		SpringApplication.run(DemoServer.class, args);
		logger.info("====== 启动成功 ======");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("====== 开始项目启动后执行功能 ======");
	}
}
