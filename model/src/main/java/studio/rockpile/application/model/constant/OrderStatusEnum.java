package studio.rockpile.application.model.constant;

public enum OrderStatusEnum {
	SUBMIT("submit", "订单提交"),
	SUCC("success", "支付成功"),
	FAIL("fail", "支付失败");
	
	private final String key;
	private final String description;

	OrderStatusEnum(final String key, final String description) {
		this.key = key;
		this.description = description;
	}

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public static OrderStatusEnum getType(String key) {
		OrderStatusEnum[] values = OrderStatusEnum.values();
		for (OrderStatusEnum value : values) {
			if (value.getKey().equals(key)) {
				return value;
			}
		}
		return null;
	}
}
