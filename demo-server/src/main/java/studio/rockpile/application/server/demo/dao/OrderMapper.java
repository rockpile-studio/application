package studio.rockpile.application.server.demo.dao;

import studio.rockpile.application.model.entity.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单信息 Mapper 接口
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
public interface OrderMapper extends BaseMapper<Order> {

	// 插入语句中的表名，应该使用sharding配置的逻辑表名t_order，不能使用t_order_1/t_order_2
	// id字段在sharding配置通过snowflake算法生成，这里不用再指定id字段
	@Insert("insert into t_order(price, account_id, status)"
	        + " values (#{price}, #{accountId}, #{status})")
	public int insert(@Param(value = "price") BigDecimal price, @Param(value = "accountId") Long accountId,
			@Param(value = "status") Integer status);

	// 根据id列表查询数据
	// 当查询的id跨库或者跨表的时候，shardingjdbc会自动解析为查询多张表
	@Select("<script>"
			+ "select * from t_order"
			+ " where id in "
			+ "<foreach collection='ids' open='(' separator=',' close=')' item='id'>"
			+ "#{id}"
			+ "</foreach>"
			+ " and account_id = #{accountId}"
			+ "</script>")
	public List<Map<String, Object>> queryByIds(@Param(value = "accountId") Long accountId,
			@Param(value = "ids") List<Long> ids);
}
