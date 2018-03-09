package org.james.common.util.profileLoader;

import java.lang.reflect.Field;
import java.util.Objects;

@FunctionalInterface
public interface FieldConsumer {

    void accept(Field field,Object instance,String value);

    default FieldConsumer andThen(FieldConsumer after) {
        Objects.requireNonNull(after);
        return (field,instance,value) -> { accept(field,instance,value); after.accept(field,instance,value); };
    }
}
