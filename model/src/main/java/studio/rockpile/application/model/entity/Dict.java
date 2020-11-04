package studio.rockpile.application.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 字典
 * </p>
 *
 * @author rockpile
 * @since 2020-11-04
 */
@TableName("t_dict")
@ApiModel(value="Dict对象", description="字典")
public class Dict implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "所属id")
    private Long pid;

    @ApiModelProperty(value = "类别")
    private String category;

    @ApiModelProperty(value = "标签")
    private String itemLabel;

    @ApiModelProperty(value = "编码")
    private String itemCode;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getItemLabel() {
        return itemLabel;
    }

    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    @Override
    public String toString() {
        return "Dict{" +
        "id=" + id +
        ", pid=" + pid +
        ", category=" + category +
        ", itemLabel=" + itemLabel +
        ", itemCode=" + itemCode +
        "}";
    }
}
