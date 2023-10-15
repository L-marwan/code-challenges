package com.marouane.challenges.loadbalancer;

import java.net.URI;

public interface LoadBalancerAlgorithm {

    public URI getNextAvailableServer();
}
