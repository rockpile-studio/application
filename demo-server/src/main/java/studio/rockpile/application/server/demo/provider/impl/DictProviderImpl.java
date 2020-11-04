package studio.rockpile.application.server.demo.provider.impl;

import studio.rockpile.application.model.entity.Dict;
import studio.rockpile.application.server.demo.dao.DictMapper;
import studio.rockpile.application.server.demo.provider.DictProvider;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 字典 服务实现类
 * </p>
 *
 * @author rockpile
 * @since 2020-11-04
 */
@Service
public class DictProviderImpl extends ServiceImpl<DictMapper, Dict> implements DictProvider {

}
