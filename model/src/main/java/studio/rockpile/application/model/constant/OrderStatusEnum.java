package studio.rockpile.application.model.constant;

public enum OrderStatusEnum {
	SUBMIT(1, "订单提交"), SUCCESS(2, "支付成功"), CANCEL(3, "取消订单");

	private final Integer key;
	private final String description;

	OrderStatusEnum(final Integer key, final String description) {
		this.key = key;
		this.description = description;
	}

	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public static OrderStatusEnum getType(Integer key) {
		OrderStatusEnum[] values = OrderStatusEnum.values();
		for (OrderStatusEnum value : values) {
			if (value.getKey() == key) {
				return value;
			}
		}
		return null;
	}
}
