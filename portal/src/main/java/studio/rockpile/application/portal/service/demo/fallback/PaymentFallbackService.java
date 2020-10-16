package studio.rockpile.application.portal.service.demo.fallback;

import org.springframework.stereotype.Component;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.portal.service.demo.PaymentService;

@Component
public class PaymentFallbackService implements PaymentService {

	@Override
	public CommonResult<Object> create(Long orderId) {
		return CommonResult.error(500, "创建订单id对应的付款流水信息，业务异常降级处理", null);
	}

	@Override
	public CommonResult<Object> queryByOrderId(Long orderId) {
		return CommonResult.error(500, "未查询到订单id对应的付款流水信息，业务异常降级处理", null);
	}

}
