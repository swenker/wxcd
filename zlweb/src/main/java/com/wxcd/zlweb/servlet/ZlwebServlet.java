package com.wxcd.zlweb.servlet;

import com.wxcd.zlweb.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by wenjusun on 6/27/16.
 */

@WebServlet(urlPatterns = "/home")
public class ZlwebServlet extends HttpServlet{
    private OrderService  orderService = new OrderService();
    private SecureRandom random;

    @Override
    public void init() throws ServletException {
        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = random.nextInt(10000);
        int productId = 10 * random.nextInt(100);
        int num = random.nextInt(5);

        orderService.placeOrder(userId, productId, num);

        resp.setContentType("text/html");
        String content = "HelloWorld:"+userId;
        resp.setContentLength(content.getBytes("UTF-8").length);
        OutputStream os = resp.getOutputStream();
        os.write(content.getBytes("UTF-8"));
        os.flush();
        os.close();
    }

    @Override
    public void destroy() {
        orderService.closeDataSource();
    }
}
