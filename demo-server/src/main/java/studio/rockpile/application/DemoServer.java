package studio.rockpile.application;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

// 指定端口启动
// java -Xms128m -Xmx128m -Dserver.port=53012 -jar demo-server.jar --spring.config.additional-location=/opt/rockpile/server/demo/conf/
// 在conf目录下配置当前微服务固有配置application-runtime.properties
// 启动nacos：运行nacos-server-1.1.4/bin目录下startup.sh（startup.cmd）
// 运行成功后，直接访问 http://127.0.0.1:8848/nacos（默认账号/密码：nacos/nacos）

// 启动sentinel：sentinel-dashboard-1.7.0.jar
// java -Xms128m -Xmx128m -Dserver.port=8090 -Dlogging.file=./log/sentinel-dash.log -jar sentinel-dashboard.jar --spring.config.additional-location=./conf/
// 配置 application-runtime.properties
// auth.username=sentinel
// auth.password=sentinel
// spring.cloud.nacos.config.server-addr=127.0.0.1:8848
// spring.cloud.nacos.config.namespace=  # namespace空，表示使用默认的命名空间
// spring.cloud.nacos.config.group=SENTINEL_GROUP
// 运行成功后，直接访问 http://127.0.0.1:8090（默认账号/密码：sentinel/sentinel）
@SpringBootApplication
@EnableDiscoveryClient  
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
