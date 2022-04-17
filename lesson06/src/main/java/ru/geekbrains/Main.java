package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.persist.CustomerDao;
import ru.geekbrains.persist.ProductDao;
import ru.geekbrains.util.CustomerProductInfo;

public class Main {

    private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    public static void main(String[] args) {

        CustomerDao cDao = context.getBean("customerDao", CustomerDao.class);
        ProductDao pDao = context.getBean("productDao", ProductDao.class);

        CustomerProductInfo cpi = context.getBean("info", CustomerProductInfo.class);

    }
}
