package studio.rockpile.application.server.demo.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.server.demo.provider.AccountProvider;

/**
 * <p>
 * 账户信息 前端控制器
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountProvider accountProvider;

	// http://127.0.0.1:53011/account/deduct-by-id?id=xxx&amount=xxx
	@RequestMapping(value = "/deduct-by-id", method = RequestMethod.GET)
	public CommonResult<?> deductById(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "amount", required = true) BigDecimal amount) throws Exception {
		accountProvider.deductById(id, amount);
		return CommonResult.succ("扣款成功");
	}
}
