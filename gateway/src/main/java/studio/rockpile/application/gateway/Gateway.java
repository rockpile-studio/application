package studio.rockpile.application.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Gateway {
    private static final Logger logger = LoggerFactory.getLogger(Gateway.class);

    public static void main(String[] args) {
        SpringApplication.run(Gateway.class, args);
        logger.info("====== 启动成功 ======");
    }
}
