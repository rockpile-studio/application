package studio.rockpile.application.portal.service.demo.fallback;

import org.springframework.stereotype.Component;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.portal.service.demo.PaymentService;

@Component
public class PaymentFallbackService implements PaymentService {

	@Override
	public CommonResult<Object> queryByAccount(Long accountId) {
		return CommonResult.error(500, "未查询到对应的付款流水信息，业务异常降级处理", null);
	}

	@Override
	public CommonResult<Object> queryPageHelperByAccount(QueryPageParam<Payment> query) {
		return CommonResult.error(500, "未查询到对应的付款流水信息，业务异常降级处理", null);
	}

	@Override
	public CommonResult<?> create(Payment payment) {
		return CommonResult.error(500, "提交付款流水信息，业务异常降级处理", null);
	}
}
