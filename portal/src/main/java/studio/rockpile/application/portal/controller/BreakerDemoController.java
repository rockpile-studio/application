package studio.rockpile.application.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.util.SpringContextUtil;

// 服务熔断示例
// @SentinelResource注解属性中，fallback管理运行异常；blockHandler管理sentinel流控&熔断等规则
@RestController
@RequestMapping("/demo/breaker")
public class BreakerDemoController {
	private final Logger logger = LoggerFactory.getLogger(BreakerDemoController.class);

	@Autowired
	private RestTemplate restTemplate;

	// http://127.0.0.1:53001/portal/demo/breaker/fallback/test?orderId=5030166
	@RequestMapping(value = "/fallback/test", method = RequestMethod.GET)
	@SentinelResource(value = "testFallBack", fallback = "handleFallback")
	public CommonResult<?> testFallBack(@RequestParam(value = "orderId", required = true) String orderId) {
		String url = SpringContextUtil.getProperty("nacos.service.url.demo") + "/payment/query/order/" + orderId;
		logger.debug("... url : {}", url);
		return restTemplate.getForObject(url, CommonResult.class);
	}

	public CommonResult<?> handleFallback(@RequestParam(value = "orderId", required = true) String orderId,
			Throwable exception) {
		return CommonResult.error(500, "未查询到订单id对应的付款流水信息，业务异常降级处理", null);
	}
}
