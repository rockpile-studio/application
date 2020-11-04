package studio.rockpile.application.server.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.framework.util.SpringContextUtil;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.provider.PaymentProvider;

/**
 * <p>
 * 缴费信息 前端控制器
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
@RestController
@RefreshScope // 支持Nacos动态刷新配置
@RequestMapping("/payment")
public class PaymentController {
	private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

	@Value("${server.port}")
	private String serverPort;

	@Value("${nacos.config.info}")
	private String info;

	@Value("${nacos.shared.config.info}")
	private String sharedInfo;

	@Value("${nacos.global.config.info}")
	private String globalInfo;

	@Value("${nacos.runtime.config.info}")
	private String runtimeInfo;

	@Autowired
	private PaymentProvider paymentProvider;

	// mybatisplus原生的分页实现方案
	// http://127.0.0.1:53011/payment/query-by-account/page
	// {"page":{"current":2,"size":2},"query":{"account_id":"5030000"}}
	@RequestMapping(value = "/query-by-account/page", method = RequestMethod.POST)
	public CommonResult<Object> queryPageByAccount(@RequestBody(required = true) QueryPageParam<Payment> query)
			throws Exception {
		IPage<Payment> result = paymentProvider.queryPageByOrder(query);

		if (ObjectUtils.isEmpty(result.getRecords())) {
			throw new NullPointerException("未查询到对应的付款流水信息");
		}
		return CommonResult.succ(result);
	}

	// pagehelper插件的分页实现方案
	// PageInfo.list：结果集
	// PageInfo.pageNum：当前页码
	// PageInfo.pageSize：当前页面显示的数据条目
	// PageInfo.pages：总页数
	// PageInfo.total：数据的总条目数
	// PageInfo.prePage：上一页
	// PageInfo.nextPage：下一页
	// PageInfo.isFirstPage：是否为第一页
	// PageInfo.isLastPage：是否为最后一页
	// PageInfo.hasPreviousPage：是否有上一页
	// PageHelper.hasNextPage：是否有下一页
	// http://127.0.0.1:53011/payment/query-by-account/pagehelper
	// {"page":{"current":2,"size":2},"query":{"account_id":"5030000"}}
	@RequestMapping(value = "/query-by-account/pagehelper", method = RequestMethod.POST)
	public CommonResult<Object> queryPageHelperByAccount(@RequestBody(required = true) QueryPageParam<Payment> query) {
		int current = query.getPage().getCurrent();
		int size = query.getPage().getSize();
		if (current > 0 && size > 0) {
			PageHelper.startPage(current, size);
		}
		QueryWrapper<Payment> wrapper = new QueryWrapper<>();
		wrapper.eq("account_id", query.getQuery().getAccountId());
		List<Payment> list = paymentProvider.list(wrapper);
		if (ObjectUtils.isEmpty(list)) {
			throw new NullPointerException("未查询到对应的付款流水信息");
		}

		PageInfo<Payment> pageInfo = new PageInfo<>(list);
		return CommonResult.succ(pageInfo);
	}

	// http://127.0.0.1:53011/payment/server/info
	// 这里捕获异常，正常返回CommonResult(code=500)，不会触发sentinel的熔断器的计数，也不会触发seata异常的全局事务回滚
	@GetMapping(value = "/server/info")
	public CommonResult<?> serverPort() {
		try {
			Map<String, String> params = new HashMap<>();
			params.put("spring.application.name", SpringContextUtil.getProperty("spring.application.name"));
			params.put("serverPort", serverPort);
			params.put("nacos.config.info", info);
			params.put("nacos.global.config.info", globalInfo);
			params.put("nacos.runtime.config.info", runtimeInfo);
			params.put("nacos.shared.config.info", sharedInfo);
			return CommonResult.succ(params);
		} catch (Exception e) {
			logger.error("查询服务信息异常：{}", e);
			return CommonResult.error("查询服务信息异常：" + e.getMessage());
		}
	}

	@PostMapping(value = "/create")
	public CommonResult<?> create(@RequestBody Payment payment) throws Exception {
		paymentProvider.create(payment);
		return CommonResult.succ(payment);
	}

	// http://127.0.0.1:53011/payment/query-by-account/5030000
	// @PathVariable传递参数的微服务，在sentinel中会按实际请求参数保存资源名/payment/query-by-account/5030000
	@RequestMapping(value = "/query-by-account/{accountId}", method = RequestMethod.GET)
	@SentinelResource(value = "queryPaymentByAccount")
	public CommonResult<Object> queryByAccount(@PathVariable(value = "accountId") Long accountId) throws Exception {
		QueryWrapper<Payment> wrapper = new QueryWrapper<>();
		wrapper.eq("account_id", accountId);
		List<Payment> list = paymentProvider.list(wrapper);

		if (ObjectUtils.isEmpty(list)) {
			throw new NullPointerException("未查询到对应的付款流水信息");
		}
		return CommonResult.succ(list);
	}
}
