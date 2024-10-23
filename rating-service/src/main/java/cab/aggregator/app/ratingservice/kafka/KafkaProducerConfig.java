package cab.aggregator.app.ratingservice.kafka;

import cab.aggregator.app.ratingservice.dto.kafka.AvgRatingUserResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

import static cab.aggregator.app.ratingservice.kafka.util.Constants.*;

@Configuration
public class KafkaProducerConfig {

    @Value(KAFKA_BOOTSTRAP_SERVERS)
    private String kafkaBootstrapServers;

    @Bean
    public ProducerFactory<String, AvgRatingUserResponse> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
                kafkaBootstrapServers);
        configProps.put(
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
                StringSerializer.class);
        configProps.put(
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, AvgRatingUserResponse> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}
