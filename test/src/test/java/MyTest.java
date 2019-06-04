import com.tensor.rpc.core.bean.factory.RpcServiceInjectBeanPostProcessor;
import org.junit.Test;

public class MyTest {

    @Test
    public void test() {
        System.out.println(RpcServiceInjectBeanPostProcessor.class.getCanonicalName());
    }

}
