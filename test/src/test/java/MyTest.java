import com.tensor.rpc.client.bean.factory.RpcInjectBeanPostProcessor;
import org.junit.Test;

public class MyTest {

    @Test
    public void test() {
        System.out.println(RpcInjectBeanPostProcessor.class.getCanonicalName());
    }

}
