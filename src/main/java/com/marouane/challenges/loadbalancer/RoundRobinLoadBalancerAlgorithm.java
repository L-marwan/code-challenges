package com.marouane.challenges.loadbalancer;

import java.net.URI;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinLoadBalancerAlgorithm implements LoadBalancerAlgorithm {

    private static volatile RoundRobinLoadBalancerAlgorithm instance;

    private List<String> servers;
    private AtomicInteger currentServerIndex;

    private RoundRobinLoadBalancerAlgorithm() {
        currentServerIndex = new AtomicInteger(0);
        servers = List.of("http://localhost:8080","http://localhost:8081");

    }

    public static LoadBalancerAlgorithm getInstance() {
        LoadBalancerAlgorithm lba = instance;

        if (lba == null) {
            synchronized (RoundRobinLoadBalancerAlgorithm.class) {
                lba = instance;
                if (lba == null) {
                    lba = new RoundRobinLoadBalancerAlgorithm();
                }
            }
        }
        return lba;
    }

    @Override
    public URI getNextAvailableServer() {
        int index = currentServerIndex.getAndIncrement() % servers.size();
        var server = servers.get(index);
        return URI.create(server);
    }


}
