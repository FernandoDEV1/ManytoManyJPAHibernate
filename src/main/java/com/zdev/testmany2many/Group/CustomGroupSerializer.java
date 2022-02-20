package com.zdev.testmany2many.Group;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/*
* Custom serializer example for Groups
* see answer -> https://stackoverflow.com/questions/41407921/eliminate-circular-json-in-java-spring-many-to-many-relationship
* */
public class CustomGroupSerializer extends StdSerializer<Set<Group>>{

    public CustomGroupSerializer(){
        this(null);
    }

    public CustomGroupSerializer(Class<Set<Group>> t) {
        super(t);
    }

    @Override
    public void serialize(
            Set<Group> groups,
            JsonGenerator generator,
            SerializerProvider provider)
            throws IOException, JsonProcessingException {

        Set<Long> tmp = new HashSet<>();
        for(Group g : groups) {
            g.setUsers(null);
            tmp.add(g.getIdGroup());
        }
        generator.writeObject(tmp);
    }

}
