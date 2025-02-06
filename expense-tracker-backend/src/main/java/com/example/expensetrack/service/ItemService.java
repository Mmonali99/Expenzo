package com.example.expensetrack.service;

import com.example.expensetrack.dto.ItemDTO;
import com.example.expensetrack.model.Item;
import com.example.expensetrack.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public List<ItemDTO> getAllItems() {
        return itemRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public ItemDTO getItemById(Long itemId) {
        return itemRepository.findById(itemId).map(this::convertToDTO).orElse(null);
    }

    public ItemDTO createItem(ItemDTO itemDTO) {
        Item item = convertToEntity(itemDTO);
        Item savedItem = itemRepository.save(item);
        return convertToDTO(savedItem);
    }

    public ItemDTO updateItem(Long itemId, ItemDTO itemDTO) {
        Item item = convertToEntity(itemDTO);
        item.setItemId(itemId);
        Item updatedItem = itemRepository.save(item);
        return convertToDTO(updatedItem);
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public List<ItemDTO> getItemsByExpenseId(Long expenseId) {
        return itemRepository.findByExpenseExpenseId(expenseId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public List<ItemDTO> getItemsByCategoryId(Integer categoryId) {
        return itemRepository.findByCategoryCategoryId(categoryId).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private ItemDTO convertToDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setItemId(item.getItemId());
        itemDTO.setItemName(item.getItemName());
        itemDTO.setItemAmount(item.getItemAmount());
        return itemDTO;
    }

    private Item convertToEntity(ItemDTO itemDTO) {
        Item item = new Item();
        item.setItemId(itemDTO.getItemId());
        item.setItemName(itemDTO.getItemName());
        item.setItemAmount(itemDTO.getItemAmount());
        return item;
    }
}
