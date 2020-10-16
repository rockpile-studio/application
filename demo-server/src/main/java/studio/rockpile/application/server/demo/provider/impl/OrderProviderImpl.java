package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.model.entity.Order;
import studio.rockpile.application.server.demo.dao.OrderMapper;
import studio.rockpile.application.server.demo.provider.OrderProvider;
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
