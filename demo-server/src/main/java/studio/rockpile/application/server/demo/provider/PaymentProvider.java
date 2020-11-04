package studio.rockpile.application.server.demo.provider;

import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 支付 服务类
 * </p>
 *
 * @author rockpile
 * @since 2020-11-04
 */
public interface PaymentProvider extends IService<Payment> {

	IPage<Payment> queryPageByOrder(QueryPageParam<Payment> query) throws Exception;

	void create(Payment payment) throws Exception;

}
