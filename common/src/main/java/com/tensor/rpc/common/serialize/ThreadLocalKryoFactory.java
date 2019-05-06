package com.tensor.rpc.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoPool;

import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * @author liaochuntao
 */
public class ThreadLocalKryoFactory {

    private static KryoPool kryoPool;

    ThreadLocalKryoFactory() {
        kryoPool = new KryoPool.Builder(new KryoFactory()).softReferences().build();
    }

    ByteArrayOutputStream serialize(Function<Kryo, ByteArrayOutputStream> function) {
         return kryoPool.run(function::apply);
    }

    Object deserialize(Function<Kryo, Object> function) {
        return kryoPool.run(function::apply);
    }

}
