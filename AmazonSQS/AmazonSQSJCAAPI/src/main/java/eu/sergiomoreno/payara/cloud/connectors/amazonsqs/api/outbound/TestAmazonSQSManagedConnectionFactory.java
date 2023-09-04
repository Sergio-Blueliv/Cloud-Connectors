package eu.sergiomoreno.payara.cloud.connectors.amazonsqs.api.outbound;

import javax.security.auth.Subject;

import fish.payara.cloud.connectors.amazonsqs.api.AmazonSQSConnection;
import fish.payara.cloud.connectors.amazonsqs.api.AmazonSQSConnectionFactory;
import fish.payara.cloud.connectors.amazonsqs.api.outbound.AmazonSQSConnectionFactoryImpl;
import fish.payara.cloud.connectors.amazonsqs.api.outbound.AmazonSQSConnectionImpl;
import fish.payara.cloud.connectors.amazonsqs.api.outbound.AmazonSQSManagedConnectionFactory;
import jakarta.resource.ResourceException;
import jakarta.resource.spi.ConfigProperty;
import jakarta.resource.spi.ConnectionDefinition;
import jakarta.resource.spi.ConnectionRequestInfo;
import jakarta.resource.spi.ManagedConnection;

@ConnectionDefinition(connection = AmazonSQSConnection.class, connectionFactory = AmazonSQSConnectionFactory.class, connectionFactoryImpl = AmazonSQSConnectionFactoryImpl.class, connectionImpl = AmazonSQSConnectionImpl.class)
public class TestAmazonSQSManagedConnectionFactory extends AmazonSQSManagedConnectionFactory {

	@ConfigProperty(description = "Url overriding aws automatic creation", type = String.class)
	private String overrideUrl;

	public TestAmazonSQSManagedConnectionFactory() {
		// TODO Auto-generated constructor stub
	}

	public String getOverrideUrl() {
		return overrideUrl;
	}

	public void setOverrideUrl(String overrideUrl) {
		this.overrideUrl = overrideUrl;
	}

	@Override
	public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo)
			throws ResourceException {
		return new TestAmazonSQSManagedConnection(subject, cxRequestInfo, this);
	}


}
