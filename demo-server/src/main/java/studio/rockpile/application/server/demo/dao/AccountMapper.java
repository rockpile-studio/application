package studio.rockpile.application.server.demo.dao;

import studio.rockpile.application.model.entity.Account;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

	@Update("update t_account set balance = balance - #{balance} where account_id = #{accountId}")
	public int updateBalance(@Param(value = "accountId") Long accountId, @Param(value = "balance") BigDecimal balance);
}
