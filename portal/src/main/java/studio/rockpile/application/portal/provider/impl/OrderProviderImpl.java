package studio.rockpile.application.portal.provider.impl;

import studio.rockpile.application.model.constant.OrderStatusEnum;
import studio.rockpile.application.model.entity.Order;
import studio.rockpile.application.portal.dao.OrderMapper;
import studio.rockpile.application.portal.provider.OrderProvider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单信息 服务实现类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@Service
public class OrderProviderImpl extends ServiceImpl<OrderMapper, Order> implements OrderProvider {
	@Autowired
	private OrderMapper orderMapper;

	@Override
	public void createOrder(Order order) {
		order.setOrderId(null);
		order.setOrderTime(Calendar.getInstance().getTime());
		order.setStatus(OrderStatusEnum.SUBMIT.getKey());
		orderMapper.insert(order);
	}

}
