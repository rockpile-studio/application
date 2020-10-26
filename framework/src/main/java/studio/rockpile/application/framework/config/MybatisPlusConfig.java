package studio.rockpile.application.framework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
public class MybatisPlusConfig {
	// mybatis实现得分页时逻辑分页或者叫做内存不是物理分页
    // 是把符合条件的数据全部查询出来放到内存中，然后返回需要的那部分
    // 当数据量大时，建议使用物理分页，所以要注册分页插件
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		return new PaginationInterceptor();
	}
}
