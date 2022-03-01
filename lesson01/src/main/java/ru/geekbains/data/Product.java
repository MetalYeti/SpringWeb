package ru.geekbains.data;

public class Product {
    private long id;
    private String title;
    private long cost;

    public Product(long id, String title, long cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Product(String title, long cost) {
        this.title = title;
        this.cost = cost;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getCost() {
        return cost;
    }
}
