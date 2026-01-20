package se.iths.stefan.webbserver;

import se.iths.stefan.webbserver.servlets.HelloServlet;
import se.iths.stefan.webbserver.servlets.RootServlet;

public class Main {
    public static void main(String[] args) throws Exception {
//starta webbservern
        var server = new org.eclipse.jetty.server.Server(8080);
//i denna context kan man registrera servlets
        var context = new org.eclipse.jetty.servlet.ServletContextHandler();
        context.addServlet(RootServlet.class, "/");
        context.addServlet(HelloServlet.class, "/hello/*");//lägg till /*

        server.setHandler(context);
//starta servern
        server.start();
        server.join();

    }
}