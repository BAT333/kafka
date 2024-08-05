package org.example;

import org.eclipse.jetty.io.ssl.ALPNProcessor;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.ServletContext;

public class requestHttp {
    public static void main(String[] args) throws Exception {
        var serve = new Server(8080);

        var context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(new ServletHolder(new NewOrderSerVlet()),"/new");

        serve.setHandler(context);
        serve.start();
        serve.join();

    }
}
