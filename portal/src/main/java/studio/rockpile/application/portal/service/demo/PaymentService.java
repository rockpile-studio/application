package studio.rockpile.application.portal.service.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.portal.service.demo.fallback.PaymentFallbackService;

@FeignClient(value = "demo-server", fallback = PaymentFallbackService.class)
public interface PaymentService {

	@GetMapping(value = "/payment/create")
	public CommonResult<Object> create(@RequestParam(value = "orderId", required = true) Long orderId);

	@RequestMapping(value = "/payment/query/order/{orderId}", method = RequestMethod.GET)
	public CommonResult<Object> queryByOrderId(@PathVariable(value = "orderId") Long orderId);
}
