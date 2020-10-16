package studio.rockpile.application.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 账户信息
 * </p>
 *
 * @author rockpile
 * @since 2020-10-16
 */
@TableName("t_account")
@ApiModel(value="Account对象", description="账户信息")
public class Account implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "账户id")
    @TableId(value = "account_id", type = IdType.ASSIGN_ID)
    private Long accountId;

    @ApiModelProperty(value = "账户名")
    private String fullname;

    @ApiModelProperty(value = "账户类型")
    private Integer accountType;


    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    @Override
    public String toString() {
        return "Account{" +
        "accountId=" + accountId +
        ", fullname=" + fullname +
        ", accountType=" + accountType +
        "}";
    }
}
