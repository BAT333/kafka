package org.example;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderSerVlet extends HttpServlet {
    private final KafkaDispatcher<Order> dispatcherorder = new KafkaDispatcher<Order>();
    private final KafkaDispatcher<Email> dispatcher = new KafkaDispatcher<Email>();

    @Override
    public void destroy() {
        super.destroy();
        dispatcherorder.close();
        dispatcher.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var userId = UUID.randomUUID().toString();
            var orderId = UUID.randomUUID().toString();
            var amount = new BigDecimal(req.getParameter("amount"));
            var emails =req.getParameter("email");
            var order = new Order(userId,orderId, amount,emails);
            var email = new Email("subobject", "body");
            dispatcherorder.send("ECOMMERCE_NEW_ORDER_4",userId,order,new CorrelationId(NewOrderSerVlet.class.getSimpleName()));
            dispatcher.send("ECOMMERCE_SEND_EMAIL_1",userId,email,new CorrelationId(NewOrderSerVlet.class.getSimpleName()));
            System.out.println("enviado");
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().println("enviado");
        }catch (Exception ex){
            throw new ServletException();
        }
    }

}
