package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.dao.PaymentMapper;
import studio.rockpile.application.server.demo.provider.PaymentProvider;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
