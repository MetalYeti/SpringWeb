package ru.geekbains;

import ru.geekbains.data.Product;
import ru.geekbains.data.ProductRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.lang3.math.NumberUtils;

@WebServlet(urlPatterns = "/product/*")
public class FirstServlet extends HttpServlet {

    private ProductRepository pr;

    @Override
    public void init() throws ServletException {
        pr = new ProductRepository();
        pr.insert(new Product("Продукт № 1", 1000));
        pr.insert(new Product("Продукт № 2", 1500));
        pr.insert(new Product("Продукт № 3", 3000));
        pr.insert(new Product("Продукт № 4", 500));
        pr.insert(new Product("Продукт № 5", 2500));
        pr.insert(new Product("Продукт № 6", 6000));
        pr.insert(new Product("Продукт № 7", 655));
        pr.insert(new Product("Продукт № 8", 4550));
        pr.insert(new Product("Продукт № 9", 2700));
        pr.insert(new Product("Продукт № 10", 1100));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter wr = resp.getWriter();
        if (req.getPathInfo() == null) {
            wr.println("<table>");
            wr.println("<tr>");
            wr.println("<th>Id</th>");
            wr.println("<th>Title</th>");
            wr.println("</tr>");

            for (Product product : pr.findAll()) {
                long productId = product.getId();
                wr.println("<tr>");
                wr.println("<td><a href='" + req.getContextPath() + req.getServletPath() + "/" + productId + "'>" + productId + "</a></td>");
                wr.println("<td>" + product.getTitle() + "</td>");
                wr.println("</tr>");
            }

            wr.println("</table>");
        } else {
            String productId = req.getPathInfo().replace("/", "");
            if (NumberUtils.isParsable(productId)) {
                Product product = pr.findById(Long.parseLong(productId));
                if (product != null) {
                    wr.println("<table>");
                    wr.println("<tr><td>ID продукта:</td>" + "<td>" + product.getId() + "</td></tr>");
                    wr.println("<tr><td>Название продукта:</td>" + "<td>" + product.getTitle() + "</td></tr>");
                    wr.println("<tr><td>Цена:</td>" + "<td>" + product.getCost() + "</td></tr>");
                    wr.println("</table>");
                } else {
                    wr.println("Продукта с указанным ID не существует.");
                }
            } else {
                wr.println("Получен неверный ID продукта.");
            }

        }
    }
}
