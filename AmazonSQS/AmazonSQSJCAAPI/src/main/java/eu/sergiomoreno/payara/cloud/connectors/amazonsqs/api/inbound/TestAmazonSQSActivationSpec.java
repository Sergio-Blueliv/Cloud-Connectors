package eu.sergiomoreno.payara.cloud.connectors.amazonsqs.api.inbound;

import fish.payara.cloud.connectors.amazonsqs.api.AmazonSQSListener;
import fish.payara.cloud.connectors.amazonsqs.api.inbound.AmazonSQSActivationSpec;
import jakarta.resource.spi.Activation;
import jakarta.resource.spi.InvalidPropertyException;
import software.amazon.awssdk.utils.StringUtils;

@Activation(messageListeners = AmazonSQSListener.class)
public class TestAmazonSQSActivationSpec extends AmazonSQSActivationSpec {

	private String overrideUrlEndPoint;


	@Override
	public void validate() throws InvalidPropertyException {
		super.validate();
		if (StringUtils.isBlank(overrideUrlEndPoint)) {
			throw new InvalidPropertyException("Don't use this class if you are not going to use localStack");
		}
	}

	public String getOverrideUrlEndPoint() {
		return overrideUrlEndPoint;
	}

	public void setOverrideUrlEndPoint(String overrideUrlEndPoint) {
		this.overrideUrlEndPoint = overrideUrlEndPoint;
	}

}
