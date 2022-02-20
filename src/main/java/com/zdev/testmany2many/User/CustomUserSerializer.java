package com.zdev.testmany2many.User;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CustomUserSerializer extends StdSerializer<Set<User>> {

    public CustomUserSerializer() {
        this(null);
    }

    public CustomUserSerializer(Class<Set<User>> t) {
        super(t);
    }

    @Override
    public void serialize(
            Set<User> users,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        Set<User> tmp = new HashSet<>();
        for(User u : users) {
            u.setGroups(null);
            tmp.add(u);
        }
        generator.writeObject(tmp);
    }
}
