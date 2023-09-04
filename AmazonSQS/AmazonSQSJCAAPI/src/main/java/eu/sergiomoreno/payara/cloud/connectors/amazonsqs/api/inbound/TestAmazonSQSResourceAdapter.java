package eu.sergiomoreno.payara.cloud.connectors.amazonsqs.api.inbound;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Timer;
import java.util.logging.Level;

import fish.payara.cloud.connectors.amazonsqs.api.inbound.AmazonSQSActivationSpec;
import fish.payara.cloud.connectors.amazonsqs.api.inbound.AmazonSQSResourceAdapter;
import fish.payara.cloud.connectors.amazonsqs.api.inbound.SQSPoller;
import jakarta.resource.ResourceException;
import jakarta.resource.spi.ActivationSpec;
import jakarta.resource.spi.BootstrapContext;
import jakarta.resource.spi.ResourceAdapterInternalException;
import jakarta.resource.spi.endpoint.MessageEndpointFactory;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

class TestAmazonSQSResourceAdapter extends AmazonSQSResourceAdapter {

	public TestAmazonSQSResourceAdapter() {
		super();
	}

	@Override
	public void start(BootstrapContext ctx) throws ResourceAdapterInternalException {
		LOGGER.info("Test Amazon SQS Resource Adapter Started..");
		setContext(ctx);
	}

	@Override
	public void stop() {
		LOGGER.info("Test Amazon SQS Resource Adapter Stopped");
	}

	@Override
	public void endpointActivation(MessageEndpointFactory endpointFactory, ActivationSpec spec)
			throws ResourceException {
		if (spec instanceof TestAmazonSQSActivationSpec) {
			TestAmazonSQSActivationSpec sqsSpec = (TestAmazonSQSActivationSpec) spec;
			SQSPoller sqsTask = null;
			try {
				sqsTask = new SQSPoller(sqsSpec, getContext(), endpointFactory,
						SqsClient.builder().region(Region.of(sqsSpec.getRegion())).credentialsProvider(sqsSpec)
						.endpointOverride(new URI(sqsSpec.getOverrideUrlEndPoint())).build());
				getRegisteredFactories().put(endpointFactory, sqsTask);

				Timer timer = createTimer();
				getRegisteredTimers().put(endpointFactory, timer);
				timer.schedule(sqsTask, sqsSpec.getInitialPollDelay(), sqsSpec.getPollInterval());
			} catch (URISyntaxException e) {
				throw new ResourceException("Cannot convert url " + sqsSpec.getOverrideUrlEndPoint() + " to URI");
			}

		} else if (spec instanceof AmazonSQSActivationSpec) {
			super.endpointActivation(endpointFactory, spec);
		} else {
			LOGGER.log(Level.WARNING, "Got endpoint activation for an ActivationSpec of unknown class {0}",
					spec.getClass().getName());
		}
	}
}
