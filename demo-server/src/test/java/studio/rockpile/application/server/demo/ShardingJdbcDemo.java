package studio.rockpile.application.server.demo;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import studio.rockpile.application.DemoServer;
import studio.rockpile.application.model.entity.Account;
import studio.rockpile.application.server.demo.dao.AccountMapper;
import studio.rockpile.application.server.demo.dao.DictMapper;
import studio.rockpile.application.server.demo.dao.OrderMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoServer.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShardingJdbcDemo {
	@Autowired
	private AccountMapper accountMapper;

	@Autowired
	private DictMapper dictMapper;

	@Autowired
	private OrderMapper orderMapper;

	// t_account是分库策略
	@Test
	public void insertAccount() {
		try {
			long ts = Calendar.getInstance().getTimeInMillis();
			for (int i = 0; i < 4; i++) {
				Long accountId = ts * 10 + i;
				int rows = accountMapper.insert(accountId, "rockpile");
				System.out.println("... rows : " + rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_account是分库策略
	@Test
	public void queryAcctByIds() {
		try {
			List<Long> ids = new ArrayList<>();
			ids.add(16030765335460L);
			ids.add(16030765335462L);
			// 由于id= 16030765335460L，16030765335462L，均符合dbnode$->{account_id % 2 + 1}
			// 所以shardingjdbc会自动路由dbnode1处理SQL
			List<Account> accounts = accountMapper.selectBatchIds(ids);
			for (Account account : accounts) {
				System.out.println("... " + account);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// t_dict是公共表
	@Test
	public void insertDict() {
		try {
			dictMapper.insert(5030001L, "account_type", "0", "管理员");
			dictMapper.insert(5030002L, "account_type", "1", "操作员");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteDict() {
		try {
			dictMapper.deleteById(5030002L);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 公共表和分库分表的关联查询测试
	@Test
	public void queryDictByAcctIds() {
		try {
			List<Long> acctIds = new ArrayList<>();
			acctIds.add(16030765335460L);
			List<Map<String, Object>> recs = dictMapper.queryByAcctIds(acctIds);
			for (Map<String, Object> rec : recs) {
				System.out.println(".. rec : " + rec);
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
				Long accountId = 16030765335460L + (i < 8 ? 1L : 0L);
				String status = "success";
				int rows = orderMapper.insert(price, accountId, status);
				System.out.println("... rows : " + rows);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void queryOrderByIds() {
		try {
			List<Long> ids = new ArrayList<>();
			ids.add(524910703769812992L);
			ids.add(524910703866281985L);
			ids.add(524910703681732609L);
			List<Map<String, Object>> orders = orderMapper.queryByIds(16030765335460L, ids);
			System.out.println(".. orders : " + orders);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
