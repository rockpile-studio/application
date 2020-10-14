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
@RequestMapping("/demo/rest")
public class DemoRestController {
	private final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 测试restTemplate是否负载均衡
	// http://127.0.0.1:53001/portal/demo/rest/payment/info
	@RequestMapping(value = "/payment/info", method = RequestMethod.GET)
	public CommonResult<?> paymentInfo() {
		String url = SpringContextUtil.getProperty("nacos.service.url.demo") + "/payment/server/info";
		logger.debug("... url : {}", url);
		return restTemplate.getForObject(url, CommonResult.class);
	}
}
