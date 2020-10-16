package studio.rockpile.application.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.util.SpringContextUtil;

@RestController
@RequestMapping("/demo")
public class DemoController {
	private final Logger logger = LoggerFactory.getLogger(DemoController.class);

	@Autowired
	private RestTemplate restTemplate;

	// 测试restTemplate是否负载均衡
	// http://127.0.0.1:53001/portal/demo/rest/load/balance
	@RequestMapping(value = "/rest/load/balance", method = RequestMethod.GET)
	public CommonResult<?> testRestLoadBalance() {
		String url = SpringContextUtil.getProperty("nacos.service.url.demo") + "/payment/server/info";
		logger.debug("... url : {}", url);
		return restTemplate.getForObject(url, CommonResult.class);
	}

	// http://127.0.0.1:53001/portal/demo/flow/limit/test
	@RequestMapping(value = "/flow/limit/test", method = RequestMethod.GET)
	public CommonResult<?> testFlowLimit() {
		StringBuilder message = new StringBuilder("application.name=");
		message.append(SpringContextUtil.getProperty("spring.application.name")).append(",sentinel.transport.port=");
		message.append(SpringContextUtil.getProperty("spring.cloud.sentinel.transport.port")).append(")");
		return CommonResult.succ(message.toString());
	}
}
