package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.dao.PaymentMapper;
import studio.rockpile.application.server.demo.provider.PaymentProvider;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 缴费信息 服务实现类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
@Service
public class PaymentProviderImpl extends ServiceImpl<PaymentMapper, Payment> implements PaymentProvider {
	private static final Logger logger = LoggerFactory.getLogger(PaymentProviderImpl.class);
	
	@Autowired
	private PaymentMapper paymentMapper;

	@Override
	public IPage<Payment> queryPageByOrderId( QueryPageParam<Payment> queryPage) {
		QueryWrapper<Payment> wrapper = new QueryWrapper<>();
		wrapper.eq("order_id", queryPage.getQuery().getOrderId());

		Page<Payment> page = new Page<>(queryPage.getPage().getCurrent(), queryPage.getPage().getSize());
		IPage<Payment> paymentIPage = paymentMapper.selectPage(page, wrapper);
		logger.debug("总条数 = {}", paymentIPage.getTotal());
		logger.debug("总页数 = {}", paymentIPage.getPages());
		return paymentIPage;
	}

	@Override
	public void createPayment(Payment payment) {
		payment.setId(null);
		payment.setPayTime(Calendar.getInstance().getTime());
		payment.setFallback(false);
		payment.setRemark("测试支付记录");
		paymentMapper.insert(payment);
	}
}
