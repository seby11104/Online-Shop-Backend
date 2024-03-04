package org.sda.finalbackend.controller;

import org.sda.finalbackend.entity.Item;
import org.sda.finalbackend.errors.InvalidItemFIeldsException;
import org.sda.finalbackend.errors.ItemNotFoundException;
import org.sda.finalbackend.errors.UserNotFoundException;
import org.sda.finalbackend.service.ItemService;
import org.sda.finalbackend.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:4200")
public class ItemController {
    @Autowired
    private ItemService itemService;
    public ItemController(ItemService itemService)
    {
        this.itemService=itemService;
    }

    @PostMapping("/item")
    public ResponseEntity<ApiResponse> createItem(@RequestBody Item item)
    {
        try
        {
            Item itemDB = itemService.createItem(item);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item adaugat cu succes")
                    .data(itemDB)
                    .build();
            return  ResponseEntity.ok(response);
        }catch (InvalidItemFIeldsException exception)
        {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

    @PatchMapping("/item")
    public ResponseEntity<ApiResponse> updateItem(@RequestBody Item item){

        try
        {
            Item itemDB = itemService.updateItem(item);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item modificat cu succes")
                    .data(itemDB)
                    .build();
            return  ResponseEntity.ok(response);
        }catch (InvalidItemFIeldsException | ItemNotFoundException exception)
        {
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(exception.getMessage())
                    .data(null)
                    .build();
            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);
        }
    }

    @DeleteMapping("/item/{id}")
    public ResponseEntity<ApiResponse> deleteItem(@PathVariable("id") Integer id)
    {
        try{
            itemService.deleteItem(id);
            ApiResponse response = new ApiResponse.Builder()
                    .status(200)
                    .message("Item delete with success")
                    .data(null)
                    .build();
            return  ResponseEntity.ok(response);

        }catch ( ItemNotFoundException e){
            ApiResponse response = new ApiResponse.Builder()
                    .status(400)
                    .message(e.getMessage())
                    .data(null)
                    .build();

            return ResponseEntity.status(HttpStatusCode.valueOf(400)).body(response);

        }
    }

    @GetMapping("/items")
    public ResponseEntity<ApiResponse> getAllItems()
    {
        List<Item> items=itemService.findAll();

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista items")
                .data(items)
                .build();

        return ResponseEntity.ok(response);
    }
    @GetMapping("/items/{category}")
    public ResponseEntity<ApiResponse> getItemsByCategory(@PathVariable("category") String category)
    {
        List<Item> items=itemService.findByCategory(category);

        ApiResponse response = new ApiResponse.Builder()
                .status(200)
                .message("Lista items")
                .data(items)
                .build();

        return ResponseEntity.ok(response);
    }


}
