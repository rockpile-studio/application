package studio.rockpile.application.portal.service.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.portal.service.demo.fallback.PaymentFallbackService;

@FeignClient(value = "demo-server", fallback = PaymentFallbackService.class)
public interface PaymentService {
	@GetMapping(value = "/payment/query-by-account/{accountId}")
	public CommonResult<Object> queryByAccount(@PathVariable(value = "accountId") Long accountId);
	
	@RequestMapping(value = "/payment/query-by-account/pagehelper", method = RequestMethod.POST)
	public CommonResult<Object> queryPageHelperByAccount(@RequestBody(required = true) QueryPageParam<Payment> query);
	
	@PostMapping(value = "/payment/create")
	public CommonResult<?> create(@RequestBody Payment payment) throws Exception;
}
