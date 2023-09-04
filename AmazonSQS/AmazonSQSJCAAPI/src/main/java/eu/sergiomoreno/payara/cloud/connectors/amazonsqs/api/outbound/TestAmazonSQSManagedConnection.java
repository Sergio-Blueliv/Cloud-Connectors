package eu.sergiomoreno.payara.cloud.connectors.amazonsqs.api.outbound;

import java.net.URI;

import javax.security.auth.Subject;

import fish.payara.cloud.connectors.amazonsqs.api.outbound.AmazonSQSManagedConnection;
import jakarta.resource.spi.ConnectionRequestInfo;

public class TestAmazonSQSManagedConnection extends AmazonSQSManagedConnection {

	TestAmazonSQSManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo,
			TestAmazonSQSManagedConnectionFactory aThis) {
		super(subject, cxRequestInfo, aThis, URI.create(aThis.getOverrideUrl()));
	}

}
