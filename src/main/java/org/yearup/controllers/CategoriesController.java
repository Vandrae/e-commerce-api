package org.yearup.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.service.CategoryService;
import org.yearup.service.ProductService;

import java.util.List;


@RestController
@RequestMapping("/categories")
@CrossOrigin
public class CategoriesController
{
    private CategoryService categoryService;
    private ProductService productService;


    //@Autowired on a constructor tells spring to inject everything
    @Autowired
    public CategoriesController(CategoryService categoryService, ProductService productService){
        this.categoryService = categoryService;
        this.productService = productService;
    }



    @GetMapping
    @PreAuthorize("permitAll()")
    public List<Category> getAll()
    {
        return categoryService.getAllCategories();
    }

    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public Category getById(@PathVariable int id)
    {
        Category category = categoryService.getById(id);

        if (category == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        return category;
    }

    @GetMapping("{categoryId}/products")
    public List<Product> getProductsById(@PathVariable int categoryId)
    {
        return productService.listByCategoryId(categoryId);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Category> addCategory(@RequestBody Category category)
    {
        // insert the category and return it with status 201 Created
        return null;
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Category updateCategory(@PathVariable int id, @RequestBody Category category)
    {
        // update the category by id and return the updated category (200 OK)
        return null;
    }


    @DeleteMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteCategory(@PathVariable int id)
    {
        // delete the category by id and return status 204 No Content
        return null;
    }
}
