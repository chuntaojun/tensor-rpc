package com.tensor.rpc.core.config;

import com.tensor.rpc.core.discovery.Service;

import java.util.Set;

/**
 * @author <a href="mailto:liaochunyhm@live.com">liaochuntao</a>
 * @since
 */
public class RPCServiceManager {

    private Set<Service> services;

    public Set<Service> getServices() {
        return services;
    }

    public void setServices(Set<Service> services) {
        this.services = services;
    }
}
