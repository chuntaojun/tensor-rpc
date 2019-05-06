package com.tensor.rpc.common.serialize;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.Function;

/**
 * @author liaochuntao
 */
class KryoSerializer {

    private static final ThreadLocalKryoFactory FACTORY = new ThreadLocalKryoFactory();
    private static final KryoConsumer KRYO_CONSUMER = new KryoConsumer();
    private static final KryoPrivoder KRYO_PRIVODER = new KryoPrivoder();

    static byte[] serialize(Object obj) {
        KRYO_CONSUMER.obj = obj;
        ByteArrayOutputStream byteArrayOutputStream = FACTORY.serialize(KRYO_CONSUMER);
        return byteArrayOutputStream.toByteArray();
    }

    static Object deserialize(byte[] bytes) {
        KRYO_PRIVODER.bytes = bytes;
        return FACTORY.deserialize(KRYO_PRIVODER);
    }

    private static class KryoConsumer implements Function<Kryo, ByteArrayOutputStream> {

        private Object obj;

        @Override
        public ByteArrayOutputStream apply(Kryo kryo) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream);
            kryo.writeClassAndObject(output, obj);
            output.close();
            return byteArrayOutputStream;
        }
    }

    private static class KryoPrivoder implements Function<Kryo, Object> {

        private byte[] bytes;

        @Override
        public Object apply(Kryo kryo) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            input.close();
            return kryo.readClassAndObject(input);
        }
    }

}
