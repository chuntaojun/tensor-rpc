package com.tensor.rpc.common.serialize.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @author liaochuntao
 */
public class KryoSerializer {

    private static final KryoPoolFactory FACTORY = new KryoPoolFactory();

    public static byte[] serialize(Object obj) {
        ByteArrayOutputStream byteArrayOutputStream = FACTORY.serialize(new KryoConsumer(obj));
        return byteArrayOutputStream.toByteArray();
    }

    public static Object deserialize(byte[] bytes) {
        return FACTORY.deserialize(new KryoPrivoder(bytes));
    }

    public static Object deserialize(byte[] bytes, Class type) {
        return FACTORY.deserialize(new KryoPrivoder2(bytes), type);
    }

    private static class KryoConsumer implements Function<Kryo, ByteArrayOutputStream> {

        private Object obj;

        public KryoConsumer(Object obj) {
            this.obj = obj;
        }

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

        public KryoPrivoder(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public Object apply(Kryo kryo) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            return kryo.readClassAndObject(input);
        }
    }

    private static class KryoPrivoder2 implements BiFunction<Kryo, Class, Object> {

        private byte[] bytes;

        public KryoPrivoder2(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public Object apply(Kryo kryo, Class cls) {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream);
            return kryo.readObject(input, cls);
        }
    }

}
