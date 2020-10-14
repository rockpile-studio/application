package studio.rockpile.application.framework.protocol;

public class CommonResult<T> {
	public final static Integer SUCC = 200;
	public final static Integer ERROR = 500;

	private Integer code;
	private String message;
	private T data;

	private CommonResult() {
	}

	public static <T> CommonResult<T> succ(T data) {
		CommonResult<T> result = new CommonResult<>();
		result.setCode(SUCC);
		result.setData(data);
		return result;
	}

	public static <T> CommonResult<T> error(String message) {
		CommonResult<T> result = new CommonResult<>();
		result.setCode(ERROR);
		result.setMessage(message);
		return result;
	}

	public static <T> CommonResult<T> error(Integer code, String message, T data) {
		CommonResult<T> result = new CommonResult<>();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}

	public CommonResult(Integer code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public String toString() {
		return "CommonResult{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + '}';
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
