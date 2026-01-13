package com.terrasystem.user_shop_service.Controller;

import com.terrasystem.user_shop_service.Entity.Item;
import com.terrasystem.user_shop_service.Service.ItemService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService service;

    public ItemController(ItemService service) {
        this.service = service;
    }

    // PUBLIC / USER
    @GetMapping
    public List<Item> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Item getOne(@PathVariable Integer id) {
        return service.get(id);
    }

    // ADMIN ONLY
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Item create(@Valid @RequestBody Item item) {
        return service.create(item);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Item update(@PathVariable Integer id,
                       @Valid @RequestBody Item item) {
        return service.update(id, item);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
