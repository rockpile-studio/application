package studio.rockpile.application.portal.provider.impl;

import studio.rockpile.application.model.entity.Order;
import studio.rockpile.application.portal.dao.OrderMapper;
import studio.rockpile.application.portal.provider.OrderProvider;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
