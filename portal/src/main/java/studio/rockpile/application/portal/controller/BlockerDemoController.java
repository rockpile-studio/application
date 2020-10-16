package studio.rockpile.application.portal.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;

import studio.rockpile.application.framework.handler.CommonBlockerHandler;
import studio.rockpile.application.framework.protocol.CommonResult;

// 服务限流示例
// @SentinelResource注解属性中，fallback管理运行异常；blockHandler管理sentinel流控&熔断等规则
@RestController
@RequestMapping("/demo/blocker")
public class BlockerDemoController {

	// http://127.0.0.1:53001/portal/demo/blocker/resource/limit/test
	// @SentinelResource注解方式埋点不支持private方法
	// blockHandler对应处理BlockException的函数名称，
	// blockHandler范围函数需要public，返回类型需与原方法相匹配，参数类型与原方法匹配并额外一个BlockException
	// 不指定blockHandlerClass时，方法需和原方法在同一个类中
	@RequestMapping(value = "/resource/limit/test", method = RequestMethod.GET)
	@SentinelResource(value = "testResourceLimit", blockHandler = "blockResourceLimit")
	public CommonResult<?> testResourceLimit(@RequestParam(value = "arg1", required = false) String arg1,
			@RequestParam(value = "arg2", required = false) String arg2) {
		StringBuilder message = new StringBuilder("资源请求处理成功，param1=");
		message.append(arg1).append("，param2=").append(arg2);
		return CommonResult.succ(message.toString());
	}

	public CommonResult<?> blockResourceLimit(String arg1, String arg2, BlockException exception) {
		StringBuilder message = new StringBuilder("资源限流服务，param1=");
		message.append(arg1).append("，param2=").append(arg2);
		return CommonResult.error(403, message.toString(), null);
	}

	// 指定blockHandlerClass后，blockHandler对应的函数必须是static，否则无法解析
	@RequestMapping(value = "/block/handler/test", method = RequestMethod.GET)
	@SentinelResource(value = "testCustomerBlockHandler", blockHandlerClass = CommonBlockerHandler.class, blockHandler = "process")
	public CommonResult<?> testCustomerBlockHandler() {
		return CommonResult.succ("资源请求处理成功");
	}
}
