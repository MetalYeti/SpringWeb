package ru.geekbrains;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.geekbrains.data.Cart;
import ru.geekbrains.data.Product;
import ru.geekbrains.data.ProductRepository;

import java.util.*;

public class Main {

    List<Cart> carts = new ArrayList<>();
    ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    private void run() {
        ProductRepository repo = context.getBean("products", ProductRepository.class);

        Scanner sc = new Scanner(System.in);

        Cart currentCart = addCart();
        ;
        while (true) {
            System.out.println("В репозитории находятся следующие продукты:");
            for (Product p : repo.findAll()) {
                System.out.println(p);
            }

            System.out.println("Корзин: " + carts.size() + " текущая корзина с индексом " + carts.indexOf(currentCart));
            System.out.println("Содержимое текущей корзины: " + currentCart);

            System.out.println("Команды: 1 - выбрать и добавить в текущую корзину продукт; 2 - удалить из текущей корзины продукт; 3 - выбрать корзину(создать новую); quit - выйти");
            String command = sc.next();

            if (command.equals("quit")) {
                break;
            } else if (command.equals("1")) {
                System.out.println("Введите id продукта для добавления");
                long id = sc.nextLong();
                currentCart.addProduct(id);
            } else if (command.equals("2")) {
                System.out.println("Введите id продукта для удаления");
                long id = sc.nextLong();
                currentCart.removeProduct(id);
            } else if (command.equals("3")) {
                System.out.println("Введите индекс корзины, если такого индекса нет будет создана новая корзина");
                int id = sc.nextInt();
                if (id >= carts.size()) {
                    currentCart = addCart();
                } else {
                    currentCart = carts.get(id);
                }
            }
        }
    }


    private Cart addCart() {
        Cart currentCart = context.getBean("cart", Cart.class);
        carts.add(currentCart);
        return currentCart;
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
