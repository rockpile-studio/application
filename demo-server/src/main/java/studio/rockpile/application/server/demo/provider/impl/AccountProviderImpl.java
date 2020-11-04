package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.model.entity.Account;
import studio.rockpile.application.server.demo.dao.AccountMapper;
import studio.rockpile.application.server.demo.provider.AccountProvider;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户 服务实现类
 * </p>
 *
 * @author rockpile
 * @since 2020-11-04
 */
@Service
public class AccountProviderImpl extends ServiceImpl<AccountMapper, Account> implements AccountProvider {
	private static final Logger logger = LoggerFactory.getLogger(AccountProviderImpl.class);

	@Autowired
	private AccountMapper accountMapper;

	@Override
	public void deductById(Long id, BigDecimal amount) throws Exception {
		UpdateWrapper<Account> wrapper = new UpdateWrapper<>();
		wrapper.setSql("balance = balance-" + amount.toString());
		wrapper.eq("id", id);
		int rows = accountMapper.update(null, wrapper);
		logger.debug("... updateBalance rows : {}", rows);
	}
}
