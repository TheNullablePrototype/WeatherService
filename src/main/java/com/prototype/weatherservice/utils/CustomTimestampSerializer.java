package com.prototype.weatherservice.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.sql.Timestamp;

public class CustomTimestampSerializer extends StdSerializer<Timestamp> {

    public CustomTimestampSerializer() {
        this(null);
    }

    public CustomTimestampSerializer(Class<Timestamp> t) {
        super(t);
    }

    @Override
    public void serialize(Timestamp value, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeNumber(value.getTime());
    }

}
