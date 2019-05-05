package com.tensor.rpc.common.serialize;

import com.esotericsoftware.kryo.Kryo;

/**
 * @author liaochuntao
 */
public class ThreadLocalKryoFactory extends KryoFactory {

    private final ThreadLocal<Kryo> holder  = ThreadLocal.withInitial(() -> createKryo());

    public Kryo getKryo() {
        return holder.get();
    }

}
