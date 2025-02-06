package com.example.expensetrack.service;

import com.example.expensetrack.dto.ItemDTO;
import com.example.expensetrack.model.Item;
import com.example.expensetrack.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ItemServiceTest {

    @Autowired
    private ItemService itemService;

    @MockBean
    private ItemRepository itemRepository;

    private Item item;
    private ItemDTO itemDTO;

    @BeforeEach
    public void setup() {
        item = new Item(1L, "Laptop", 1000.0);
        itemDTO = new ItemDTO(1L, "Laptop", 1000.0);
    }

    @Test
    public void testGetAllItems() {
        when(itemRepository.findAll()).thenReturn(Collections.singletonList(item));
        assertFalse(itemService.getAllItems().isEmpty());
    }

    @Test
    public void testGetItemById() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        ItemDTO found = itemService.getItemById(1L);
        assertEquals(item.getItemId(), found.getItemId());
    }

    @Test
    public void testCreateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        ItemDTO created = itemService.createItem(itemDTO);
        assertEquals(item.getItemId(), created.getItemId());
    }

    @Test
    public void testUpdateItem() {
        when(itemRepository.save(any(Item.class))).thenReturn(item);
        ItemDTO updated = itemService.updateItem(1L, itemDTO);
        assertEquals(item.getItemId(), updated.getItemId());
    }

    @Test
    public void testDeleteItem() {
        doNothing().when(itemRepository).deleteById(1L);
        itemService.deleteItem(1L);
        verify(itemRepository, times(1)).deleteById(1L);
    }
}
