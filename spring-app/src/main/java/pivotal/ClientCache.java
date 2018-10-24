package pivotal;

import io.pivotal.spring.cloud.service.gemfire.GemfireServiceConnectorConfig;
import org.springframework.cloud.Cloud;
import org.springframework.cloud.CloudFactory;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.cloud.service.ServiceConnectorConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by pivotal on 9/22/15.
 */
@Configuration
public class ClientCache extends AbstractCloudConfig {

  public ServiceConnectorConfig createGemfireConnectorConfig() {
    // Create a custom service connector config object which sets specific properties
    // for the ClientCache as exposed by the GemfireServiceConnectorConfig.
    GemfireServiceConnectorConfig gemfireConfig = new GemfireServiceConnectorConfig();
    gemfireConfig.setPoolIdleTimeout(7777L);

    return gemfireConfig;
  }

  @Bean(name = "my-client-cache")
  public org.apache.geode.cache.client.ClientCache getGemfireClientCache() {
    CloudFactory cloudFactory = new CloudFactory();

    // Obtain the Cloud object for the environment in which the application is running.
    // Note that you must have a CloudConnector suitable for your deployment environment on your classpath.
    // For example, if you are deploying the application to Cloud Foundry, you must add the Cloud Foundry
    // Connector to your classpath. If no suitable CloudConnector is found, the getCloud() method will throw
    // a CloudException. Use the Cloud instance to access application and service information and to create
    // service connectors.
    Cloud cloud = cloudFactory.getCloud();

    org.apache.geode.cache.client.ClientCache cache = cloud.getSingletonServiceConnector(org.apache.geode.cache.client.ClientCache.class,
            createGemfireConnectorConfig());

    
    return cache;
  }
}
