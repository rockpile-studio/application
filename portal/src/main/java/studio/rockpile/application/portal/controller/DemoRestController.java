package studio.rockpile.application.portal.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import studio.rockpile.application.framework.protocol.CommonResult;

@RestController
@RequestMapping("/demo/rest")
public class DemoRestController {
	private final Logger logger = LoggerFactory.getLogger(DemoRestController.class);
	
	@RequestMapping(value = "/info", method = RequestMethod.GET)
	public CommonResult<?> testConfigServer() {
		return CommonResult.succ("/nacos/config");
	}
}
