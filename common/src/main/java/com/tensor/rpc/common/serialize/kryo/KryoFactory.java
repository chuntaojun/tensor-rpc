package com.tensor.rpc.common.serialize.kryo;


import com.esotericsoftware.kryo.Kryo;

/**
 * @author liaochuntao
 */
public class KryoFactory implements com.esotericsoftware.kryo.pool.KryoFactory {

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        kryo.register(byte[].class);
        kryo.register(String.class);
        return kryo;
    }

}
