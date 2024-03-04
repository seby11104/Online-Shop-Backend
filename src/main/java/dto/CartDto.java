package dto;


import lombok.Data;
import org.sda.finalbackend.entity.Item;

import java.util.List;

@Data
public class CartDto {
    private Integer id;
    private Integer userId;
    private List<Item> items;
}
