package studio.rockpile.application.server.demo.dao;

import studio.rockpile.application.model.entity.Account;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 账户信息 Mapper 接口
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
public interface AccountMapper extends BaseMapper<Account> {

	@Insert("insert into t_account(account_id, fullname) values (#{accountId}, #{fullname})")
	public int insert(@Param(value = "accountId") Long accountId, @Param(value = "fullname") String fullname);
}
