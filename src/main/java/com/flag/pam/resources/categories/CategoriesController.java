package com.flag.pam.resources.categories;

import com.flag.pam.common.AppResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.Optional;

/**
 * @author rameshalagumalai
 * @Date 28/01/2025
 * */

@RestController
@RequestMapping("/api/categories")
public class CategoriesController {

    @Autowired
    private CategoriesRespository categoriesRespository;

    @GetMapping
    public AppResponse getAllCategories() {
        return new AppResponse(categoriesRespository.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppResponse createNewCategory(@Valid @RequestBody CategoryPayload categoryPayload) {
        Category category = Category.builder()
                .name(categoryPayload.getName())
                .type(categoryPayload.getTypeValue())
                .color(categoryPayload.getColor())
                .icon(categoryPayload.getIcon())
                .build();
        categoriesRespository.save(category);

        return new AppResponse("Category created successfully", category);
    }

    @GetMapping("/{categoryId:\\d+}")
    public AppResponse getCategoryById(@PathVariable long categoryId) throws Exception {
        Optional<Category> categoryResult = categoriesRespository.findById(categoryId);
        if (!categoryResult.isPresent()) {
            throw new EntityNotFoundException("Category doesn't exist");
        }
        return new AppResponse(categoryResult.get());
    }

    @PutMapping(path = "/{categoryId:\\d+}")
    public AppResponse editCategoryById(@PathVariable long categoryId, @Valid @RequestBody CategoryPayload payload) throws Exception {
        Optional<Category> categoryResult = categoriesRespository.findById(categoryId);
        if (!categoryResult.isPresent()) {
            throw new EntityNotFoundException("Category doesn't exist");
        }

        Category category = categoryResult.get();
        category.setName(payload.getName());
        category.setType(payload.getTypeValue());
        category.setColor(payload.getColor());
        category.setIcon(payload.getIcon());
        categoriesRespository.save(category);

        return new AppResponse("Category edited successfully", category);
    }

    @DeleteMapping("/{categoryId:\\d+}")
    public AppResponse deleteCategoryById(@PathVariable long categoryId) throws Exception {
        Optional<Category> categoryResult = categoriesRespository.findById(categoryId);
        if (!categoryResult.isPresent()) {
            throw new EntityNotFoundException("Category doesn't exist");
        }
        categoriesRespository.delete(categoryResult.get());

        return new AppResponse("Category deleted successfully");
    }

}
