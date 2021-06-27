/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neu.metrics;

import com.timgroup.statsd.NonBlockingStatsDClient;
import com.timgroup.statsd.StatsDClient;

/**
 *
 * @author abhishek
 */
public class Metrics {

    private StatsDClient statsDClient;

    private Metrics() {
        statsDClient = new NonBlockingStatsDClient("CloudApp", "127.0.0.1", 8125);
    }

    public StatsDClient getInstance() {
        return statsDClient;
    }
}
