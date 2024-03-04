package org.sda.finalbackend.service;

import org.sda.finalbackend.entity.Category;
import org.sda.finalbackend.entity.Item;
import org.sda.finalbackend.errors.InvalidItemFIeldsException;
import org.sda.finalbackend.errors.ItemNotFoundException;
import org.sda.finalbackend.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    public Item createItem(Item item)throws InvalidItemFIeldsException
    {

        validateItemFields(item);

        return  this.itemRepository.save(item);
    }
    public Item updateItem(Item item)throws InvalidItemFIeldsException, ItemNotFoundException
    {

        Optional<Item> itemDB = this.itemRepository.findById(item.getId());
        if(itemDB.isEmpty()){
            throw new ItemNotFoundException("Itemul nu a fost gasit ");
        }

        validateItemFields(item);

        return  this.itemRepository.save(item);
    }

    public void deleteItem (Integer id) throws ItemNotFoundException
    {
        Optional<Item> itemDB = this.itemRepository.findById(id);
        if(itemDB.isEmpty()){
            throw new ItemNotFoundException("Itemul nu a fost gasit ");
        }
        this.itemRepository.deleteById(id);
    }

    public List<Item> findAll(){ return this.itemRepository.findAll(); }

    public void validateItemFields(Item item)throws InvalidItemFIeldsException
    {

        if(item.getTitle()==null || item.getTitle().isEmpty()){
            throw new InvalidItemFIeldsException("Titlul este invalid");
        }
        if(item.getDescription()==null || item.getDescription().isEmpty()){
            throw new InvalidItemFIeldsException("Descrierea este invalida!");
        }
        if(item.getCategory() == null){
            throw new InvalidItemFIeldsException("Categoria este invalida!");
        }
        if(item.getPrice() == null || item.getPrice() <= 0.0d){
            throw new InvalidItemFIeldsException("Pretul este invalid!");
        }
        if(item.getImageUrl() == null || item.getImageUrl().isEmpty()){
            throw new InvalidItemFIeldsException("Imaginea lipseste!");
        }
    }

    public Item findById (Integer id) throws ItemNotFoundException
    {
        Optional<Item> itemDB = this.itemRepository.findById(id);
        if(itemDB.isEmpty()){
            throw new ItemNotFoundException("Itemul nu a fost gasit");
        }
        return itemDB.get();
    }

    public List<Item> findByCategory (String category){
        if(category.equals("ALL"))
        {
            return findAll();
        }
        return this.itemRepository.findByCategory(Category.valueOf(category));
    }
}
