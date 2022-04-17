package ru.geekbrains.persist;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name="title", nullable = false)
    private String title;

    @Max(value = 100000)
    @Min(value = 0)
    @Column(name="cost")
    private long cost;

    public Product(Long id, String title, long cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public Product(String title, long cost) {
        this.title = title;
        this.cost = cost;
    }

    public Product(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public String getTitle() {
        return title;
    }

    public long getCost() {
        return cost;
    }
}
