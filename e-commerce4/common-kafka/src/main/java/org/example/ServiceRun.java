package org.example;

import java.util.concurrent.Executors;

public class ServiceRun <T>{
    private final ServiceProvider<T> provader;
    public ServiceFactory<T> factory;
    public ServiceRun(ServiceFactory<T> factory) {
        this.provader = new ServiceProvider<T>(factory);
    }

    public void start(int threadCont) throws Exception {
        var pool = Executors.newFixedThreadPool(threadCont);
        for (int i =0;i<=threadCont;i++){
            pool.submit(provader);
        }
    }
}
