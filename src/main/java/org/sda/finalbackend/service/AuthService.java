package org.sda.finalbackend.service;

import dto.UserDto;
import org.aspectj.apache.bcel.classfile.Module;
import org.mindrot.jbcrypt.BCrypt;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.InvalidCredentialsException;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.errors.UserNotFoundException;
import org.sda.finalbackend.repository.UserRepository;
import org.sda.finalbackend.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDto login(String email, String password) throws InvalidCredentialsException, UserNotFoundException {
        Optional<User> userOptional = this.userRepository.getByEmail(email);
        if (userOptional.isEmpty()) {
            throw new InvalidCredentialsException("Email invalid");
        }
        String passwordFromDB = userOptional.get().getPassword();
        if(Utils.getInstance().checkPassword(password,passwordFromDB)){
            UserDto userDto=new UserDto();
            userDto.setId(userOptional.get().getId());
            userDto.setUsername(userOptional.get().getUsername());
            userDto.setEmail(userOptional.get().getEmail());
            userDto.setUserRole(userOptional.get().getUserRole());

            return userDto;
        } else
        {
            throw new InvalidCredentialsException("Parola invalida");
        }
    }

    public UserDto register(User user) throws  InvalidEmailOrUsernameException,InvalidCredentialsException
    {
        if(user.getEmail()==null|| user.getEmail().isEmpty()){
            throw new InvalidEmailOrUsernameException("Email is Invalid");

        }

        if(user.getUsername()==null|| user.getUsername().isEmpty()){
            throw new InvalidEmailOrUsernameException("Username  is Invalid");

        }
        if(user.getPassword()== null || user.getPassword().isEmpty())
        {
            throw new InvalidCredentialsException("Passowrd is invalid");
        }
        Optional<User> userOptional = this.userRepository.getByEmailOrUsername(user.getEmail(),user.getUsername());

        if(userOptional.isPresent())
        {
            throw new InvalidEmailOrUsernameException("User Or Email in use");
        }

        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(user.getUserRole());
        // criptam parola folosind libraria Bcrypt, iar parola criptata o salvam in baza de date
        String encryptedPassword = Utils.getInstance().encryptPassword(user.getPassword());
        usr.setPassword(encryptedPassword);

        User userDB = this.userRepository.save(usr);
        UserDto userDto = new UserDto();
        userDto.setUsername(userDB.getUsername());
        userDto.setId(userDB.getId());
        userDto.setEmail(userDB.getEmail());
        userDto.setUserRole(userDB.getUserRole());
        return userDto;
    }

}
