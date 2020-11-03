package studio.rockpile.application.portal.provider;

import studio.rockpile.application.model.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单信息 服务类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
public interface OrderProvider extends IService<Order> {
	// 创建订单
	public void createOrder(Order order);
}
