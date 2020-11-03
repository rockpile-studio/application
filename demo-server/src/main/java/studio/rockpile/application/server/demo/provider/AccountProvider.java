package studio.rockpile.application.server.demo.provider;

import studio.rockpile.application.model.entity.Account;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 账户信息 服务类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
public interface AccountProvider extends IService<Account> {
	public	void updateBalance(Long accountId, BigDecimal amount);
}
