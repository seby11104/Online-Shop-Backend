package org.sda.finalbackend.repository;

import org.sda.finalbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User,Integer>{

    Optional<User> getByEmail(String email);

    Optional<User> getByUsername(String username);

    Optional<User>GetByEmailEndUsername(String email,String username);


}
