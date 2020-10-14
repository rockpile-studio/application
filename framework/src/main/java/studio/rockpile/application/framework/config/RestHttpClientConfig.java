package studio.rockpile.application.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "rest.http.client.pool")
public class RestHttpClientConfig {

	private Integer maxTotal;
	private Integer defaultMaxPerRoute;
	private Integer validateAfterInactivity;
	private Integer connectTimeout;
	private Integer readTimeout;
	private Integer connReqTimeout;

	@Override
	public String toString() {
		return "RestHttpClientConfig [maxTotal=" + maxTotal + ", defaultMaxPerRoute=" + defaultMaxPerRoute
				+ ", validateAfterInactivity=" + validateAfterInactivity + ", connectTimeout=" + connectTimeout
				+ ", readTimeout=" + readTimeout + ", connReqTimeout=" + connReqTimeout + "]";
	}

	public Integer getMaxTotal() {
		return maxTotal;
	}

	public void setMaxTotal(Integer maxTotal) {
		this.maxTotal = maxTotal;
	}

	public Integer getDefaultMaxPerRoute() {
		return defaultMaxPerRoute;
	}

	public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
		this.defaultMaxPerRoute = defaultMaxPerRoute;
	}

	public Integer getValidateAfterInactivity() {
		return validateAfterInactivity;
	}

	public void setValidateAfterInactivity(Integer validateAfterInactivity) {
		this.validateAfterInactivity = validateAfterInactivity;
	}

	public Integer getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(Integer connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public Integer getReadTimeout() {
		return readTimeout;
	}

	public void setReadTimeout(Integer readTimeout) {
		this.readTimeout = readTimeout;
	}

	public Integer getConnReqTimeout() {
		return connReqTimeout;
	}

	public void setConnReqTimeout(Integer connReqTimeout) {
		this.connReqTimeout = connReqTimeout;
	}

}
