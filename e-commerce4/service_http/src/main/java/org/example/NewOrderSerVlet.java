package org.example;

import org.example.dispatcher.KafkaDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

public class NewOrderSerVlet extends HttpServlet {
    private final KafkaDispatcher<Order> dispatcherorder = new KafkaDispatcher<Order>();

    @Override
    public void destroy() {
        super.destroy();
        dispatcherorder.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            var userId = UUID.randomUUID().toString();
            var orderId = UUID.randomUUID().toString();
            var amount = new BigDecimal(req.getParameter("amount"));
            var emails =req.getParameter("email");
            var order = new Order(userId,orderId, amount,emails);
            dispatcherorder.send("ECOMMERCE_NEW_ORDER_4",userId,order,new CorrelationId(NewOrderSerVlet.class.getSimpleName()));

            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            resp.getWriter().println("enviado");
        }catch (Exception ex){
            throw new ServletException();
        }
    }

}
