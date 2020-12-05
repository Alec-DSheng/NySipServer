package org.nee.ny.sip.nysipserver.configuration;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;

/**
 * @Author: alec
 * Description:
 * @date: 17:05 2020-12-02
 */
public class JacksonJsonSerializer implements Serializer<Object> {
    private final ObjectWriter writer = (new ObjectMapper()).writer();

    public JacksonJsonSerializer() {
    }

    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    public byte[] serialize(String topic, Object data) {
        try {
            return this.writer.writeValueAsBytes(data);
        } catch (JsonProcessingException var4) {
            throw new SerializationException(var4);
        }
    }

    public void close() {
    }
}
