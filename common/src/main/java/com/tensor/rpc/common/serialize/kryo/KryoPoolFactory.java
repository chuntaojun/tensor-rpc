package com.tensor.rpc.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayOutputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author liaochuntao
 */
public class KryoPoolFactory {

    private static KryoPool kryoPool;

    KryoPoolFactory() {
        kryoPool = new KryoPool.Builder(new KryoFactory()).softReferences().build();
    }

    ByteArrayOutputStream serialize(Function<Kryo, ByteArrayOutputStream> function) {
         return kryoPool.run(function::apply);
    }

    Object deserialize(Function<Kryo, Object> function) {
        return kryoPool.run(function::apply);
    }

    Object deserialize(BiFunction<Kryo, Class, Object> function, Class cls) {
        return kryoPool.run(kryo -> function.apply(kryo, cls));
    }

}
