package org.sda.finalbackend.service;

import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
        @Autowired
        private UserRepository userRepository ;
        UserService(UserRepository userRepository){
            this.userRepository=userRepository;
        }
    public User createUser(User user){
            // TODO : implementare logica pt. username,email  sa ne asiguram ca sunt unice
       return this.userRepository.save(user);
    }
    public User upedateUser(User user){
            //ToDO : de verificat daca id-ul exista in baza de date
        // TODO : implementare logica pt. username,email  sa ne asiguram ca sunt unice
        return this.userRepository.save(user);
    }

    public void deleteUser(Integer id){
        //ToDO : de verificat daca id-ul exista in baza de date
         this.userRepository.deleteById(id);

    }
    public List<User> findAll(){
            return this.userRepository.findAll();
    }
    public Optional<User> findById (Integer id){
            return  this.userRepository.findById(id);


    }
    public Optional<User> findByEmail(String email){
            return this.userRepository. getByEmail(email);


    }
    public Optional<User> findByUsername(String username){
        return this.userRepository. getByUsername(username);
    }
}
