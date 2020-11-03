package studio.rockpile.application.portal.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.portal.service.demo.PaymentService;

@RestController
@RequestMapping("/order")
public class OrderController {
	private final Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Resource
	private PaymentService paymentService;
	
	// http://127.0.0.1:53001/portal/order/query/payment/{orderId}
	@RequestMapping(value = "/query/payment/{orderId}", method = RequestMethod.GET)
	public CommonResult<?> queryPaymentByOrder(@PathVariable("orderId") Long orderId) {
		logger.debug("... OrderController.queryPaymentByOrder({})", orderId);
		CommonResult<Object> payments = paymentService.queryByOrderId(orderId);
		return payments;
	}

}
