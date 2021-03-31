package Servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import pojo.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orderServlet")
public class OrderServlet extends HttpServlet {
    static int OrderId_counter;
    private List<Order> orderList;
    Order order;

    @Override
    public void init() throws ServletException {
        System.out.println("In INIT Method");
        OrderId_counter=1;
        orderList = new ArrayList<>();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        String name = req.getParameter("name");
        int quantity=Integer.parseInt(req.getParameter("quantity"));
        if (name != null  && quantity != 0){
            String status = addOrder(OrderId_counter,name,quantity,resp);
            OrderId_counter++;

            out.println(status);
            out.close();
        } else{
            resp.setContentType("text/plain");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Please try again later!");
            out.close();
        }
    }
    private String addOrder(int id, String name,int quantity ,HttpServletResponse response){
        for(Order order : orderList)
        {
            if(order.getOrderId()==id)
            {
                this.orderList.add(order);
            }
        }
        this.orderList.add(new Order(id, name, quantity));
        response.setStatus(HttpServletResponse.SC_CREATED);
        int sts=response.getStatus();
        return "Status is"+ "" + sts;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        String accept = req.getHeader("accept");
        Gson gson = new GsonBuilder().create();
        PrintWriter out = resp.getWriter();
        if (id != null && !id.equals("")){
           Order order = getOrderById(Integer.parseInt(id));
            if (order == null){
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println(resp.getStatus()+" "+"Not Found");

            }
            resp.setContentType("application/json");
            out.println(gson.toJson(order));
            out.close();
        }
        else
        {   resp.setContentType("application/json");
            out.println(gson.toJson(orderList));
            out.close();
        }
    }

    private Order getOrderById(int id){
        for (Order order : orderList){
            if (order.getOrderId()== id){
                return order;
            }
        }
        return null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        if (id != null && !id.equals("")){
            String status = deleteById(Integer.parseInt(id), resp);
            out.println(status);
            out.close();
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Please try again later!");
            out.close();
        }
    }

    private String deleteById(int id, HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().create();
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        for (Order order : orderList){
            if (order.getOrderId()== id){
                orderList.remove(order);
                out.println(gson.toJson(order));
                out.close();
                response.setStatus(HttpServletResponse.SC_OK);
                out.println(response.getStatus());
                return "Order Deleted";
            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "Order id not found";
    }
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = req.getParameter("name");
        String quantity = req.getParameter("quantity");
        String id = req.getParameter("id");
        PrintWriter out = resp.getWriter();

        if (name != null  && quantity != null && id != null){
            String status = updateById(Integer.parseInt(id), name,Integer.parseInt(quantity), resp);

            out.println(status);
            out.close();
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Please try again later!");
            out.close();
        }
    }

    private String updateById(int id, String name, int quantity,HttpServletResponse response) throws IOException {
        Gson gson = new GsonBuilder().create();

        for (Order order : orderList){
            if (order.getOrderId()== id){
                order.setName(name);
                order.setQuantity(quantity);
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                out.println(gson.toJson(order));
                out.close();
                return "Order changed successfully";

            }
        }
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        return "Order id not found";
    }

    @Override
    public void destroy() {
        this.orderList.clear();
        OrderId_counter=1;
        System.out.println("In DESTROY Method");
        System.out.println("Is List Empty returns="+ " " +orderList.isEmpty());
        System.out.println("OrderCounter Value"+" "+OrderId_counter);
    }

}
