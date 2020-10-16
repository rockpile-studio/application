package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.model.entity.Account;
import studio.rockpile.application.server.demo.dao.AccountMapper;
import studio.rockpile.application.server.demo.provider.AccountProvider;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 账户信息 服务实现类
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@Service
public class AccountProviderImpl extends ServiceImpl<AccountMapper, Account> implements AccountProvider {

}
