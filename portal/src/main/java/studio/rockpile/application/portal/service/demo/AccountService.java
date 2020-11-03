package studio.rockpile.application.portal.service.demo;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import studio.rockpile.application.framework.protocol.CommonResult;

@FeignClient(value = "demo-server")
public interface AccountService {

	@RequestMapping(value = "/account/deduct-by-id", method = RequestMethod.GET)
	public CommonResult<?> deductById(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "amount", required = true) BigDecimal amount);
}
