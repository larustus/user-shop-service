package com.terrasystem.user_shop_service.Service;

import com.terrasystem.user_shop_service.Entity.Item;
import com.terrasystem.user_shop_service.Repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;

@Service
public class ItemService {

    private final ItemRepository repo;

    public ItemService(ItemRepository repo) {
        this.repo = repo;
    }

    public Item create(Item item) {
        item.setId(null); // zabezpieczenie
        return repo.save(item);
    }

    public Item update(Integer id, Item updated) {
        Item existing = repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Item not found"
                ));

        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setCategory(updated.getCategory());
        existing.setAvailableQty(updated.getAvailableQty());
        existing.setImageUrl(updated.getImageUrl());

        return repo.save(existing);
    }

    public void delete(Integer id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Item not found"
            );
        }
        repo.deleteById(id);
    }

    public Item get(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Item not found"
                ));
    }

    public List<Item> getAll() {
        return repo.findAll();
    }
}
