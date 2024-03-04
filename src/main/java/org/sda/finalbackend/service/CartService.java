package org.sda.finalbackend.service;

import dto.CartDto;
import org.sda.finalbackend.entity.Cart;
import org.sda.finalbackend.entity.Item;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.CartNotFoundException;
import org.sda.finalbackend.errors.ItemNotFoundException;
import org.sda.finalbackend.errors.UserNotFoundException;
import org.sda.finalbackend.repository.CartRepository;
import org.sda.finalbackend.repository.ItemRepository;
import org.sda.finalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private UserRepository userRepository;

    public CartService(CartRepository cartRepository,ItemRepository itemRepository, UserRepository userRepository)
    {
        this.cartRepository=cartRepository;
        this.itemRepository=itemRepository;
        this.userRepository=userRepository;
    }

    public Cart createCart(CartDto cartDto) throws Exception
    {
        if(cartDto.getUserId()==null|| cartDto.getUserId()<=0)
        {
            throw new Exception("Invalid user id");
        }

        Optional<User> userOptional = userRepository.findById(cartDto.getUserId());
        if(userOptional.isEmpty())
        {
            throw new  UserNotFoundException("user not found");
        }

        if(cartDto.getItems() == null || cartDto.getItems().isEmpty())
        {
            throw new Exception("Invalid items lsit");
        }

        List<Item> items = cartDto.getItems();
        for(Item item : items)
        {
            if(item.getId()!=null)
            {
                Optional<Item> itemOptional=this.itemRepository.findById(item.getId());
                if(itemOptional.isEmpty())
                {
                    throw new ItemNotFoundException("Item with id:" + item.getId()+" not found");
                }
            }
        }

        Cart cart = new Cart();
        cart.setUser(userOptional.get());
        cart.setItems(items);
        return this.cartRepository.save(cart);
    }

    public List<Cart> getAllCarts(){
        return this.cartRepository.findAll();
    }

    public void deleteCart(Integer id) throws CartNotFoundException {
        Optional<Cart> cartOptional = this.cartRepository.findById(id);
        if(cartOptional.isEmpty()){
            throw new CartNotFoundException("Cart-ul nu a fost gasit");
        }
        this.cartRepository.deleteById(id);
    }

}