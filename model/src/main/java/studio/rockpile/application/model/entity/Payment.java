package studio.rockpile.application.model.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>
 * 缴费信息
 * </p>
 *
 * @author rockpile
 * @since 2020-10-14
 */
@TableName("t_payment")
@ApiModel(value="Payment对象", description="缴费信息")
public class Payment implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "支付id")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @ApiModelProperty(value = "订单id")
    private Long orderId;

    @ApiModelProperty(value = "支付金额")
    private BigDecimal amount;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "支付时间")
    private Date payTime;

    @ApiModelProperty(value = "是否回退：0否(默认) 1是")
    private Boolean isFallback;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Boolean getFallback() {
        return isFallback;
    }

    public void setFallback(Boolean isFallback) {
        this.isFallback = isFallback;
    }

    @Override
    public String toString() {
        return "Payment{" +
        "id=" + id +
        ", orderId=" + orderId +
        ", amount=" + amount +
        ", remark=" + remark +
        ", payTime=" + payTime +
        ", isFallback=" + isFallback +
        "}";
    }
}
