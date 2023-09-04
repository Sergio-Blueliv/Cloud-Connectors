package eu.sergiomoreno.payara.cloud.connectors.amazonsqs.api.inbound;

import java.net.URI;

import fish.payara.cloud.connectors.amazonsqs.api.inbound.SQSPoller;
import jakarta.resource.spi.BootstrapContext;
import jakarta.resource.spi.endpoint.MessageEndpointFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

public class TestSQSPoller extends SQSPoller {

	protected TestSQSPoller(TestAmazonSQSActivationSpec sqsSpec, BootstrapContext context,
			MessageEndpointFactory endpointFactory) {
		super(sqsSpec, context, endpointFactory,
				SqsClient.builder().endpointOverride(URI.create(sqsSpec.getOverrideUrlEndPoint()))
						.region(Region.of(sqsSpec.getRegion())).credentialsProvider(sqsSpec).build());
	}
}
