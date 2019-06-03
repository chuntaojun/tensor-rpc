package com.tensor.rpc.test;

import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Service(value = "test-1")
public class Test1Impl implements Test1 {
    @Override
    public void print() {
        System.out.println(getClass().getCanonicalName());
    }
}
