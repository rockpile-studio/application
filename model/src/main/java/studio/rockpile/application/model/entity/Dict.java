package studio.rockpile.application.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典信息
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@TableName("t_dict")
@ApiModel(value="Dict对象", description="字典信息")
public class Dict implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "字典id")
    @TableId(value = "dict_id", type = IdType.ASSIGN_ID)
    private Long dictId;

    @ApiModelProperty(value = "字典类型")
    private String type;

    @ApiModelProperty(value = "字典编码")
    private String code;

    @ApiModelProperty(value = "字典值")
    private String value;


    public Long getDictId() {
        return dictId;
    }

    public void setDictId(Long dictId) {
        this.dictId = dictId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Dict{" +
        "dictId=" + dictId +
        ", type=" + type +
        ", code=" + code +
        ", value=" + value +
        "}";
    }
}
