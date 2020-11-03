package studio.rockpile.application.server.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import studio.rockpile.application.framework.protocol.CommonResult;
import studio.rockpile.application.framework.protocol.Pagination;
import studio.rockpile.application.framework.protocol.QueryPageParam;
import studio.rockpile.application.model.entity.Order;
import studio.rockpile.application.server.demo.provider.OrderProvider;

/**
 * <p>
 * 订单信息 前端控制器
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@RestController
@RequestMapping("/order")
public class QueryPageController {

	@Autowired
	private OrderProvider orderProvider;

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
	// http://127.0.0.1:53011/order/query/page/by-account?accountId=16030765335460
	@RequestMapping(value = "/query/page/by-account", method = RequestMethod.GET)
	public CommonResult<Object> queryPageByAccountId(@RequestParam(value = "accountId", required = true) Long accountId) {
		// 模拟查询参数
		QueryPageParam<Order> query = new QueryPageParam<>();
		Pagination page = new Pagination(1, 3);
		query.setPage(page);
		Order order = new Order();
		order.setAccountId(accountId);
		query.setQuery(order);
		
		if (query.getPage().getCurrent() > 0 && query.getPage().getSize() > 0) {
			PageHelper.startPage(query.getPage().getCurrent(), query.getPage().getSize());
		}
		QueryWrapper<Order> wrapper = new QueryWrapper<>();
		wrapper.eq("account_id", query.getQuery().getAccountId());
		List<Order> list = orderProvider.list(wrapper);
		PageInfo<Order> pageInfo = new PageInfo<>(list);
		return CommonResult.succ(pageInfo);
	}
}
