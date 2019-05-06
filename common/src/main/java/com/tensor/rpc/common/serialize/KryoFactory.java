package com.tensor.rpc.common.serialize;


import com.esotericsoftware.kryo.Kryo;

/**
 * @author liaochuntao
 */
class KryoFactory implements com.esotericsoftware.kryo.pool.KryoFactory {

    @Override
    public Kryo create() {
        Kryo kryo = new Kryo();
        kryo.setRegistrationRequired(false);
        return kryo;
    }

}
