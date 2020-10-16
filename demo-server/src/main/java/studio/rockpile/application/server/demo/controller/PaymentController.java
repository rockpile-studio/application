package studio.rockpile.application.server.demo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.util.SpringContextUtil;

/**
 * <p>
 * 缴费信息 前端控制器
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
@RestController
@RefreshScope // 支持Nacos动态刷新配置
@RequestMapping("/payment")
public class PaymentController {

	@Value("${server.port}")
	private String serverPort;
	
	@Value("${nacos.config.info}")
	private String info;

	// http://127.0.0.1:53011/payment/server/info
	@RequestMapping(value = "/server/info", method = RequestMethod.GET)
	public CommonResult<?> serverPort() {
		StringBuilder result = new StringBuilder("server(");
		result.append(SpringContextUtil.getProperty("spring.application.name")).append(":");
		result.append(serverPort).append(") info:\"").append(info).append("\"");
		return CommonResult.succ(result.toString());
	}

}
