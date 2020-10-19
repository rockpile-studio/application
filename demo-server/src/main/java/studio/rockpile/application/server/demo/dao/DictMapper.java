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

	@Insert("insert into t_dict(dict_id, type, code, value)" + " values (#{dictId}, #{type}, #{code}, #{value})")
	public int insert(@Param(value = "dictId") Long dictId, @Param(value = "type") String type,
			@Param(value = "code") String code, @Param(value = "value") String value);
	
	@Delete( "delete from t_dict where dict_id = #{id}" )
	public int deleteById( @Param(value = "id") Long id );
	
	// 公共表和分库分表的关联查询测试
	@Select( "<script>"
			+ "select * from t_account a, t_dict b"
			+ " where a.account_type = b.code"
			+ " and b.type = 'account_type'"
			+ " and a.account_id in "
			+ "<foreach collection='acctIds' item='acctId' open='(' separator=',' close=')'>"
			+ "#{acctId}"
			+ "</foreach>"
			+ "</script>")
	public List<Map<String, Object>> queryByAcctIds(@Param(value = "acctIds") List<Long> acctIds);
}
