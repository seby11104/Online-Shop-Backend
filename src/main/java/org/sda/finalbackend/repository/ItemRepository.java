package org.sda.finalbackend.repository;

import org.sda.finalbackend.entity.Category;
import org.sda.finalbackend.entity.Item;
import org.sda.finalbackend.service.UserService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer> {

    List<Item> findByCategory(Category category);
}
