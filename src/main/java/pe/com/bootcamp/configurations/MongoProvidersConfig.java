package pe.com.bootcamp.configurations;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.SimpleReactiveMongoDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import com.mongodb.connection.ClusterConnectionMode;

@Configuration
public class MongoProvidersConfig {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	// region provider0
	@Primary
	@Bean
	@ConfigurationProperties(prefix = "mongodb.provider0")
	MongoProperties getProvider0Properties() {
		return new MongoProperties();
	}
	@Primary
	@Bean
	ReactiveMongoDatabaseFactory mongoProvider0Factory(final MongoProperties mongo) throws Exception {
	    return new SimpleReactiveMongoDatabaseFactory(mongoProviderClient(mongo), mongo.getDatabase());
	}
	@Primary
	@Bean
	@Qualifier("mongodb.provider0.template")
	ReactiveMongoTemplate mongoProvider0Template() throws Exception {
	    return new ReactiveMongoTemplate(mongoProvider0Factory(getProvider0Properties()));
	}
	// endRegion provider0
	
	@Bean
	MongoClient mongoProviderClient(MongoProperties properties) {
	    ConnectionString connectionString = new ConnectionString(properties.determineUri());	    
	    MongoCredential credential = MongoCredential.createCredential(properties.getUsername(), properties.getDatabase(), properties.getPassword());	    
	    
	    logger.info("user:"+properties.getUsername() + "db:"+properties.getDatabase());
	    
	    MongoClientSettings.Builder builder = MongoClientSettings
	            .builder()
	            .applyConnectionString(connectionString)
	            .credential(credential)
	            .applyToClusterSettings(b -> b.mode(ClusterConnectionMode.SINGLE))
	            .applyToConnectionPoolSettings(b -> {	            	
	            	b.maxWaitTime(10, TimeUnit.SECONDS);
	            	b.maxSize(100);
	            })
	            .applyToServerSettings(b -> {
	            	b.minHeartbeatFrequency(700, TimeUnit.MILLISECONDS);
	            	b.heartbeatFrequency(15, TimeUnit.SECONDS);
            	})
	            .applyToSocketSettings(b -> {	            	
	            	b.connectTimeout(5, TimeUnit.SECONDS);
	            	b.readTimeout(15, TimeUnit.SECONDS);	            	
	            })
	            .applyToSslSettings(b -> b.enabled(false));
	    		//.streamFactoryFactory(NettyStreamFactory::new);
	            //.applyToSslSettings(b -> b.enabled(true).invalidHostNameAllowed(false))
			    //.streamFactoryFactory(NettyStreamFactoryFactory.builder().sslContext(sslContext).build());
	   
	    return MongoClients.create(builder.build());
	}
}
