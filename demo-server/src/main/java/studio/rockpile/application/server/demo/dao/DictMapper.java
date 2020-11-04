package studio.rockpile.application.server.demo.dao;

import studio.rockpile.application.model.entity.Dict;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 字典信息 Mapper 接口
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
public interface DictMapper extends BaseMapper<Dict> {

	@Insert("insert into t_dict(id, category, item_label, item_code)" + " values (#{id}, #{category}, #{itemLabel}, #{itemCode})")
	public int insert(@Param(value = "id") Long id, @Param(value = "category") String category,
			@Param(value = "itemLabel") String label, @Param(value = "itemCode") String code);
	
	@Delete( "delete from t_dict where id = #{id}" )
	public int deleteById( @Param(value = "id") Long id );
	
	// 公共表和分库分表的关联查询测试
	@Select( "<script>"
			+ "select a.*, b.item_label from t_payment a, t_dict b"
			+ " where b.item_code = '1'"
			+ " and b.category = 'account_type'"
			+ " and a.account_id in "
			+ "<foreach collection='accountIds' item='accountId' open='(' separator=',' close=')'>"
			+ "#{accountId}"
			+ "</foreach>"
			+ "</script>")
	public List<Map<String, Object>> queryPaymentByAccountIds(@Param(value = "accountIds") List<Long> accountIds);
}
