package ru.geekbrains.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class ProductDto {

    private Long id;

    @NotBlank
    private String title;

    @Max(value = 100000)
    @Min(value = 0)
    private long cost;

    public ProductDto(Long id, String title, long cost) {
        this.id = id;
        this.title = title;
        this.cost = cost;
    }

    public ProductDto(String title, long cost) {
        this.title = title;
        this.cost = cost;
    }

    public ProductDto(){}

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
