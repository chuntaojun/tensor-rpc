package com.tensor.rpc.test;

import org.springframework.stereotype.Service;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
@Service(value = "test-2")
public class Test2Impl implements Test2 {
    @Override
    public void print() {
        System.out.println(getClass().getCanonicalName());
    }
}
