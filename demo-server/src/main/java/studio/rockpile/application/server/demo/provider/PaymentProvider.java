package studio.rockpile.application.server.demo.provider;

import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Payment;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 缴费信息 服务类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
public interface PaymentProvider extends IService<Payment> {
	// 使用mybatisplus原生的分页
	public IPage<Payment> queryPageByOrderId(QueryPageParam<Payment> queryPage);

	public void createPayment(Payment pay);
}
