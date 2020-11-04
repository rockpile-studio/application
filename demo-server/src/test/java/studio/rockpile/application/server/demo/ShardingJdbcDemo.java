package studio.rockpile.application.server.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;

import studio.rockpile.application.DemoServer;
import studio.rockpile.application.model.constant.OrderStatusEnum;
import studio.rockpile.application.model.entity.Payment;
import studio.rockpile.application.server.demo.dao.DictMapper;
import studio.rockpile.application.server.demo.dao.OrderMapper;
import studio.rockpile.application.server.demo.dao.PaymentMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShardingJdbcDemo {
	@Autowired
	private PaymentMapper paymentMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private OrderMapper orderMapper;
	
	// t_payment 分库策略
	@Test
	public void insertPayment() {
		try {
			for (int i = 0; i < 6; i++) {
				Long accountId = 5030000L + i % 2;
				Long orderId = IdWorker.getId();

				Payment payment = new Payment();
				payment.setOrderId(orderId);
				payment.setAccountId(accountId);
				payment.setAmount(BigDecimal.valueOf(100.356).setScale(2, BigDecimal.ROUND_HALF_UP));
				payment.setPayTime(Calendar.getInstance().getTime());
				payment.setRefund(false);

				int rows = paymentMapper.insert(payment);
				System.out.println("... rows : " + rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_payment 分库策略
	@Test
	public void queryPaymentByIds() {
		try {
			List<Long> ids = new ArrayList<>();
			ids.add(1323908501990395906L);
			ids.add(1323908504460840963L);

			// 由于查询未指定分库策略的字段，所以shardingjdbc会路由dbnode1、dbnode1处理SQL
			List<Payment> payments = paymentMapper.selectBatchIds(ids);
			for (int i = 0; i < payments.size(); i++) {
				System.out.println("... " + payments.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_payment 分库策略
	@Test
	public void queryPaymentByAccount() {
		try {
			QueryWrapper<Payment> wrapper = new QueryWrapper<>();
			wrapper.eq("account_id", 5030000L);
			wrapper.in("id", Arrays.asList(1323908501990395906L, 1323908504460840963L));

			// 由于account_id=5030000L，符合dbnode$->{account_id % 2 + 1}
			// 所以shardingjdbc会自动路由dbnode1处理SQL
			List<Payment> payments = paymentMapper.selectList(wrapper);
			for (int i = 0; i < payments.size(); i++) {
				System.out.println("... " + payments.get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_dict 公共表
	@Test
	public void insertDict() {
		try {
			dictMapper.insert(503016601001L, "account_type", "买家", "1");
			dictMapper.insert(503016601002L, "account_type", "卖家", "2");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteDict() {
		try {
			dictMapper.deleteById(503016601002L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 公共表和分库分表的关联查询测试
	@Test
	public void queryPaymentDictByAccountIds() {
		try {
			List<Long> accountIds = new ArrayList<>();
			accountIds.add(5030000L);
			List<Map<String, Object>> records = dictMapper.queryPaymentByAccountIds(accountIds);
			for (Map<String, Object> record : records) {
				System.out.println(".. record : " + record);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_order分库分表策略
	@Test
	public void insertOrder() {
		try {
			// 16条记录平均分布在dbnode1/dbnode2中，t_order_1/t_order_2四张表对象中
			for (int i = 0; i < 16; i++) {
				BigDecimal price = BigDecimal.valueOf(100.356).setScale(2, BigDecimal.ROUND_HALF_UP);
				Long accountId = 5030000L + (i < 8 ? 1L : 0L);
				Integer status = OrderStatusEnum.SUBMIT.getKey();
				orderMapper.insert(price, accountId, status);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_order分库分表策略
	@Test
	public void queryOrderByIds() {
		try {
			List<Long> ids = new ArrayList<>();
			ids.add(530806618414120961L);
			ids.add(530806618464452609L);
			List<Map<String, Object>> orders = orderMapper.queryByIds(5030000L, ids);
			System.out.println(".. orders : " + orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
