package org.example;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class GenerateAllReportsSerVlet extends HttpServlet {
    private final KafkaDispatcher<String> dispatcher = new KafkaDispatcher<>();

    @Override
    public void destroy() {
        super.destroy();
        dispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try {

            dispatcher.send("ECOMMERCE_SEND_MASSAGE_TO_ALL_USERS","ECOMMERCE_USER_GENERATE_READING_REPORT","ECOMMERCE_USER_GENERATE_READING_REPORT",new CorrelationId(GenerateAllReportsSerVlet.class.getSimpleName()));

            System.out.println("enviado");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().println("enviado");
        }catch (Exception ex){
            throw new ServletException();
        }
    }
}
