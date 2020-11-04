package studio.rockpile.application.portal.controller;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.model.entity.Order;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.portal.provider.OrderProvider;
import studio.rockpile.application.portal.service.demo.AccountService;
import studio.rockpile.application.portal.service.demo.PaymentService;

@RestController
@RequestMapping("/transact")
public class DistributeTransactDemoController {
	private static final Logger logger = LoggerFactory.getLogger(DistributeTransactDemoController.class);

	@Autowired
	private OrderProvider orderProvider;

	@Resource
	private PaymentService paymentService;

	@Resource
	private AccountService accountService;

	// http://127.0.0.1:53001/portal/transact/order/submit?accountId=16030765335460
	@RequestMapping(value = "/order/submit", method = RequestMethod.GET)
	public CommonResult<?> submitOrder(@RequestParam(value = "accountId", required = true) Long accountId) {
		try {
			// 提交订单
			Order order = new Order();
			order.setAccountId(accountId);
			order.setPrice(new BigDecimal(100.126).setScale(2, BigDecimal.ROUND_DOWN));
			orderProvider.createOrder(order);

			// 缴费记录
			Payment payment = new Payment();
			payment.setOrderId(order.getOrderId());
			payment.setAmount(order.getPrice());
			paymentService.create(payment);

			// 更新账户余额
			accountService.deductById(accountId, order.getPrice());
		} catch (Exception e) {
			logger.error("订单提交分布式事务处理失败：{}", e);
			CommonResult.error("订单提交分布式事务处理失败：" + e.getMessage());
		}
		return CommonResult.succ("订单提交分布式事务处理成功");

	}
}
