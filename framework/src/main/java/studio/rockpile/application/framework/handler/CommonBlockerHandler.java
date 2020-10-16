package studio.rockpile.application.framework.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;

import studio.rockpile.application.framework.protocol.CommonResult;

public class CommonBlockerHandler {
	public static CommonResult<?> process(BlockException exception ) {
		return CommonResult.error(403, "资源限流服务", null);
	}
}
