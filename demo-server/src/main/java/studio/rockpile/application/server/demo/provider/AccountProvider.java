package studio.rockpile.application.server.demo.provider;

import studio.rockpile.application.model.entity.Account;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账户 服务类
 * </p>
 *
 * @author rockpile
 * @since 2020-11-04
 */
public interface AccountProvider extends IService<Account> {

	void deductById(Long id, BigDecimal amount) throws Exception;

}
