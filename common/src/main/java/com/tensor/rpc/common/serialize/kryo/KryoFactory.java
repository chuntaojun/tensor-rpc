package com.tensor.rpc.common.serialize.kryo;


import com.esotericsoftware.kryo.Kryo;
import org.objenesis.strategy.StdInstantiatorStrategy;

/**
 * @author liaochuntao
 */
public class KryoFactory implements com.esotericsoftware.kryo.pool.KryoFactory {

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.setInstantiatorStrategy(new Kryo.DefaultInstantiatorStrategy(new StdInstantiatorStrategy()));
        return kryo;
    }

}
