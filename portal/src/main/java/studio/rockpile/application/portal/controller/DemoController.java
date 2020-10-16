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
import com.alibaba.csp.sentinel.slots.block.BlockException;

import studio.rockpile.application.framework.handler.CommonBlockHandler;
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
	public CommonResult<?> testLoadBalance() {
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

	// http://127.0.0.1:53001/portal/demo/resource/limit/test
	// @SentinelResource注解方式埋点不支持private方法
	// blockHandler对应处理BlockException的函数名称，
	// blockHandler范围函数需要public，返回类型需与原方法相匹配，参数类型与原方法匹配并额外一个BlockException
	// 不指定blockHandlerClass时，方法需和原方法在同一个类中
	@RequestMapping(value = "/resource/limit/test", method = RequestMethod.GET)
	@SentinelResource(value = "testResourceLimit", blockHandler = "fallback_testResourceLimit")
	public CommonResult<?> testResourceLimit(@RequestParam(value = "arg1", required = false) String arg1,
			@RequestParam(value = "arg2", required = false) String arg2) {
		StringBuilder message = new StringBuilder("资源请求处理成功，param1=");
		message.append(arg1).append("，param2=").append(arg2);
		return CommonResult.succ(message.toString());
	}

	public CommonResult<?> fallback_testResourceLimit(String arg1, String arg2, BlockException exception) {
		StringBuilder message = new StringBuilder("资源限流服务，param1=");
		message.append(arg1).append("，param2=").append(arg2);
		return CommonResult.error(403, message.toString(), null);
	}

	// 指定blockHandlerClass后，blockHandler对应的函数必须是static，否则无法解析
	@RequestMapping(value = "/block/handler/test", method = RequestMethod.GET)
	@SentinelResource(value = "testCustomerBlockHandler", blockHandlerClass = CommonBlockHandler.class, blockHandler = "process")
	public CommonResult<?> testCustomerBlockHandler() {
		return CommonResult.succ("资源请求处理成功");
	}
}
