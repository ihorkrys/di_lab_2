package edu.duan.app.warehouseservice.service;

import edu.duan.app.api.*;
import edu.duan.app.warehouseservice.data.*;
import edu.duan.app.warehouseservice.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;

@Service
public class WarehouseService {
    private WarehouseRepository warehouseRepository;

    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public List<WarehouseItem> getAll() {
        return warehouseRepository.findAll().stream().map(this::convertToApi).toList();
    }

    public WarehouseItem getById(long id) {
        return warehouseRepository.findById(id).map(this::convertToApi).orElseThrow(warehouseItemNotFoundException(id));
    }

    @Transactional
    public WarehouseItem addItemToWarehouse(WarehouseItem warehouseItem) {
        return convertToApi(warehouseRepository.save(convertToDomain(warehouseItem)));
    }

    @Transactional
    public WarehouseItem updateStock(long itemId, int count) {
        WarehouseEntity warehouseEntity = warehouseRepository.findFirstByItemId(itemId).orElseThrow(itemStockNotFoundException(itemId));
        warehouseEntity.setInStock(count);
        return convertToApi(warehouseEntity);
    }

    private WarehouseItem convertToApi(WarehouseEntity warehouseEntity) {
        WarehouseItem warehouseItem = new WarehouseItem();
        warehouseItem.setId(warehouseEntity.getId());
        warehouseItem.setName(warehouseEntity.getItem().getName());
        warehouseItem.setDescription(warehouseEntity.getItem().getDescription());
        warehouseItem.setPrice(warehouseEntity.getItem().getPrice());
        warehouseItem.setInStockCount(warehouseEntity.getInStock());
        return warehouseItem;
    }

    private WarehouseEntity convertToDomain(WarehouseItem item) {
        WarehouseEntity warehouseEntity = new WarehouseEntity();
        warehouseEntity.setInStock(item.getInStockCount());
        ItemEntity itemEntity = new ItemEntity();
        itemEntity.setId(item.getId());
        itemEntity.setName(item.getName());
        itemEntity.setDescription(item.getDescription());
        itemEntity.setPrice(item.getPrice());
        warehouseEntity.setItem(itemEntity);
        return warehouseEntity;
    }

    private static Supplier<WarehouseItemNotFoundException> warehouseItemNotFoundException(long id) {
        return () -> new WarehouseItemNotFoundException("Warehouse item with `" + id + "` not found");
    }

    private static Supplier<ItemStockNotFoundException> itemStockNotFoundException(long id) {
        return () -> new ItemStockNotFoundException("Item with `" + id + "` not found in warehouse");
    }
}
