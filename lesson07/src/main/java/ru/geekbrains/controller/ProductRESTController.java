package ru.geekbrains.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.dto.ProductDto;
import ru.geekbrains.service.ProductService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/products")
public class ProductRESTController {

    private final ProductService productService;

    @Autowired
    public ProductRESTController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping
    @ResponseBody
    public Page<ProductDto> getAllProducts(
            @RequestParam(name = "minCost",required = false) Long minCost,
            @RequestParam(name = "maxCost",required = false) Long maxCost,
            @RequestParam(name = "page",required = false) Integer page,
            @RequestParam(name = "size",required = false) Integer size,
            @RequestParam(name = "sortField", required = false) String sort){

        Integer pageValue = page == null ? 0 : page - 1;
        Integer sizeValue = size == null ? 5 : size;
        String sortValue = sort == null ? "id" : sort;

        return productService.getProductsByFilter(minCost, maxCost, pageValue, sizeValue, sortValue);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ProductDto form(@PathVariable("id") long id) {
        return productService.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public void remove(@PathVariable("id") long id) {
        productService.deleteById(id);
    }

    @PostMapping
    @ResponseBody
    public ProductDto save(@RequestBody ProductDto product) {
        if (product.getId() != null){
            throw new IllegalArgumentException("New product must not have an ID");
        }
        return productService.save(product);
    }

    @PutMapping
    @ResponseBody
    public ProductDto update(@RequestBody ProductDto product) {
        if (product.getId() == null){
            throw new IllegalArgumentException("Updated product must have an ID");
        }
        return productService.save(product);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler
    public String illegalArgumentHandler(IllegalArgumentException ex){
        return ex.getMessage();
    }
}
