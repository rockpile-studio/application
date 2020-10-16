package studio.rockpile.application.server.demo.controller;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.util.SpringContextUtil;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.provider.PaymentProvider;

/**
 * <p>
 * 缴费信息 前端控制器
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
@RestController
@RefreshScope // 支持Nacos动态刷新配置
@RequestMapping("/payment")
public class PaymentController {

	@Value("${server.port}")
	private String serverPort;

	@Value("${nacos.config.info}")
	private String info;

	@Autowired
	private PaymentProvider paymentProvider;

	// http://127.0.0.1:53011/payment/server/info
	@GetMapping(value = "/server/info")
	public CommonResult<?> serverPort() {
		StringBuilder result = new StringBuilder("server(");
		result.append(SpringContextUtil.getProperty("spring.application.name")).append(":");
		result.append(serverPort).append(") info:\"").append(info).append("\"");
		return CommonResult.succ(result.toString());
	}

	// http://127.0.0.1:53011/payment/create?orderId=5030166
	@GetMapping(value = "/create")
	public CommonResult<Object> create(@RequestParam(value = "orderId", required = true) Long orderId) {
		Payment payment = new Payment();
		payment.setOrderId(orderId);
		payment.setAmount(new BigDecimal(100.126).setScale(2, BigDecimal.ROUND_DOWN));
		payment.setPayTime(Calendar.getInstance().getTime());
		payment.setRemark("测试支付记录");
		payment.setFallback(false);
		paymentProvider.save(payment);
		return CommonResult.succ(payment);
	}

	// http://127.0.0.1:53011/payment/query/order/5030166
	@RequestMapping(value = "/query/order/{orderId}", method = RequestMethod.GET)
	public CommonResult<Object> queryByOrderId(@PathVariable(value = "orderId") Long orderId) {
		QueryWrapper<Payment> query = new QueryWrapper<>();
		query.eq("order_id", orderId);
		List<Payment> list = paymentProvider.list(query);
		
		if( ObjectUtils.isEmpty(list) ) {
			throw new NullPointerException("未查询到订单id对应的付款流水信息");
		}
		return CommonResult.succ(list);
	}
}
