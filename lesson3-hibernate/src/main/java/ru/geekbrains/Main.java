package ru.geekbrains;

import org.hibernate.cfg.Configuration;
import ru.geekbrains.entity.Customer;
import ru.geekbrains.entity.Item;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        EntityManager em = entityManagerFactory.createEntityManager();
// создаем и пишем в БД покупателей и товары
        /*
        Customer customer0 = new Customer();
        Customer customer10 = new Customer( "Vasa");
        Customer customer20 = new Customer( "Preta");
        Customer customer30 = new Customer( "Olya");
        Customer customer40 = new Customer( "Kesha");
        Customer customer50 = new Customer( "Igor");
        Item item0 = new Item();
        Item item10 = new Item( "iphone7", 2850.00);
        Item item20 = new Item( "samsung", 5000.00);
        Item item30 = new Item( "acer", 2000.00);
        Item item40 = new Item( "lenovo", 1500.00);
        Item item50 = new Item( "asus", 3851.50);
        Item item60 = new Item( "iphoneX", 9999.99);
        Item item70 = new Item( "Nokia", 36.55);

        em.getTransaction().begin();
        em.persist(customer10);
        em.persist(customer20);
        em.persist(customer30);
        em.persist(customer40);
        em.persist(customer50);
        em.persist(item10);
        em.persist(item20);
        em.persist(item30);
        em.persist(item40);
        em.persist(item50);
        em.persist(item60);
        em.persist(item70);
        em.getTransaction().commit();
*/
//запрашиваем из БД всех покупателей и все товары чтоб сделать покупки
        em.getTransaction().begin();
        Customer customer1 = em.find(Customer.class, 1L);
        Customer customer2 = em.find(Customer.class, 7L);
        Customer customer3 = em.find(Customer.class, 3L);
        Customer customer4 = em.find(Customer.class, 4L);
        Customer customer5 = em.find(Customer.class, 5L);
        Customer customer6 = em.find(Customer.class, 6L);
        Item item1 = em.find(Item.class, 1L);
        Item item2 = em.find(Item.class, 2L);
        Item item3 = em.find(Item.class, 3L);
        Item item4 = em.find(Item.class, 4L);
        Item item5 = em.find(Item.class, 5L);
        Item item6 = em.find(Item.class, 6L);
        Item item7 = em.find(Item.class, 7L);
        Item item8 = em.find(Item.class, 8L);
        em.getTransaction().commit();
//покупка - каждому покупателб - разные товары
        List<Item> forCus1 = Arrays.asList(item1, item2, item3);
        List<Item> forCus2 = Arrays.asList(item4, item5, item6);
        List<Item> forCus3 = Arrays.asList(item2, item3, item7);
        List<Item> forCus4 = Arrays.asList(item6, item7);
        List<Item> forCus5 = Arrays.asList(item1);
        List<Item> forCus6 = Arrays.asList(item1, item2);
//записываем в БД
        em.getTransaction().begin();
        customer1.setItems(forCus1);
        customer2.setItems(forCus2);
        customer3.setItems(forCus3);
        customer4.setItems(forCus4);
        customer5.setItems(forCus5);
        customer6.setItems(forCus6);
        em.getTransaction().commit();
//инициализация закончена
//какие товары покупал клиент?
        Scanner in = new Scanner(System.in);
        System.out.print("Input name of customer: ");
        String name = in.nextLine();

        em.getTransaction().begin();
        Customer customerItems = null;
        try {
            customerItems = em.createQuery("select c from Customer c where c.name = :name", Customer.class)
                    .setParameter("name", name).getSingleResult();
        } catch (Exception e) {
            System.out.println("There is no such customer!");
        }
        em.getTransaction().commit();
        List<Item> itemList = null;
        if (customerItems != null)
            itemList = customerItems.getItems();
        System.out.println("List ot purchases of " + name);
        if (itemList != null)
            itemList.stream().map(p -> p.getTitle() + " " + p.getPrice()).forEach(System.out::println);
        else
            System.out.println("no purchases found");
//какие клиенты купили определенный товар?
        System.out.print("Input title of product: ");
        String title = in.nextLine();

        em.getTransaction().begin();
        Item item = null;
        try {
            item = em.createQuery("select i from Item i where i.title = :title", Item.class)
                    .setParameter("title", title).getSingleResult();
        } catch (Exception e) {
            System.out.println("There is no such product!");
        }
        em.getTransaction().commit();
        List<Customer> customerList = null;
        if (item != null)
            customerList = item.getCustomers();
        System.out.println("List ot customers who bought " + title);
        if (customerList != null)
            customerList.stream().map(p -> p.getName()).forEach(System.out::println);
        else
            System.out.println("no customers found");
//можно удалять из базы товары/покупателей
        System.out.print("Input name of customer to delete: ");
        String nameDel = in.nextLine();

        em.getTransaction().begin();
        Customer customerToRemove = null;
        try {
            customerToRemove = em.createQuery("select c from Customer c where c.name = :name", Customer.class)
                    .setParameter("name", nameDel).getSingleResult();
            em.remove(customerToRemove);
        } catch (Exception e) {
            System.out.println("There is no such customer!");
        }

        em.getTransaction().commit();

        System.out.print("Input title of product to delete: ");
        String titleDel = in.nextLine();
        em.getTransaction().begin();
        try {
            Item itemToRemove = em.createQuery("select i from Item i where i.title = :title", Item.class)
                    .setParameter("title", titleDel).getSingleResult();
            em.remove(itemToRemove);
        } catch (Exception e) {
            System.out.println("There is no such product!");
        }
        em.getTransaction().commit();

//итог - посмотрим что осталось)
        em.getTransaction().begin();
        List<Customer> customers = em.createQuery("select c from Customer c", Customer.class).getResultList();
        List<Item> items = em.createQuery("select i from Item i", Item.class).getResultList();
        em.getTransaction().commit();
        System.out.println(customers);
        System.out.println(items);

        em.close();
        in.close();
    }
}
