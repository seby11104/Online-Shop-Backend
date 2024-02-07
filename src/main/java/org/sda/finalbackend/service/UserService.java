package org.sda.finalbackend.service;

import org.mindrot.jbcrypt.BCrypt;
import org.sda.finalbackend.entity.User;
import org.sda.finalbackend.errors.InvalidEmailOrUsernameException;
import org.sda.finalbackend.errors.UserNotFoundException;
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
    public User createUser(User user)throws InvalidEmailOrUsernameException
    {
        //ToDO:De adaugat un regex pt adresa de email
        if(user.getEmail()==null|| user.getEmail().isEmpty()){
            throw new InvalidEmailOrUsernameException("Email is Invalid");

        }

        if(user.getUsername()==null|| user.getUsername().isEmpty()){
            throw new InvalidEmailOrUsernameException("Username  is Invalid");

        }
            // fixme : implementare logica pt. username,email  sa ne asiguram ca sunt unice
        Optional<User> userOptional = this.userRepository.getByEmailOrUsername(user.getEmail(),user.getUsername());

        if(userOptional.isPresent())
        {
            //inseamna ca in baza de date exista deja un user cu acceste date
            throw new InvalidEmailOrUsernameException("User Or Email in use");
        }

        User usr = new User();
        usr.setUsername(user.getUsername());
        usr.setEmail(user.getEmail());
        usr.setUserRole(user.getUserRole());
        // criptam parola folosind libraria Bcrypt, iar parola criptata o salvam in baza de date
        String encryptedPassword = this.encryptPassword(user.getPassword());
        usr.setPassword(encryptedPassword);

       return this.userRepository.save(usr);
    }

    public User upedateUser(User user)throws UserNotFoundException,InvalidEmailOrUsernameException
    {
            //ToDO : de verificat daca id-ul exista in baza de date
        Optional<User>userOptional=this.userRepository.findById(user.getId());
        if(userOptional.isEmpty()){
            throw new UserNotFoundException("User not found");

        }

        // TODO : implementare logica pt. username,email  sa ne asiguram ca sunt unice
        if(user.getEmail()==null|| user.getEmail().isEmpty()){
            throw new InvalidEmailOrUsernameException("Email is Invalid");

        }

        if(user.getUsername()==null|| user.getUsername().isEmpty()){
            throw new InvalidEmailOrUsernameException("Username  is Invalid");

        }
        //Fixme:De validat daca noul username sau email nu sant prezente in baza de date(Tema)

        String encryptedPassword = encryptPassword(user.getPassword());
        user.setPassword(encryptedPassword);
        return this.userRepository.save(user);
    }

    public void deleteUser(Integer id) throws UserNotFoundException
    {
        //ToDO : de verificat daca id-ul exista in baza de date

        Optional<User> userOptional =this.userRepository.findById(id);
        if(userOptional.isEmpty())
        {
            throw new UserNotFoundException("User not found in database");
        }
         this.userRepository.deleteById(id);

    }
    public List<User> findAll(){
            return this.userRepository.findAll();
    }
    public User findById (Integer id) throws UserNotFoundException
    {
        Optional<User>userOptional = this.userRepository.findById(id);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found ");
        }
            return  userOptional.get();


    }
    public User findByEmail(String email) throws UserNotFoundException
    {
        Optional<User>userOptional = this.userRepository.getByEmail(email);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found bay email");
        }
        return  userOptional.get();


    }
    public User findByUsername(String username) throws UserNotFoundException
    {
        Optional<User>userOptional = this.userRepository.getByUsername(username);
        if (userOptional.isEmpty()){
            throw new UserNotFoundException("User not found bay username");
        }
        return  userOptional.get();


    }
    /**
     * This method it is used to encrypt the password before save it in database
     *
     * @param password - clear password
     * @return - encrypted password
     */
    private String encryptPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String password, String bdPassword) {
        return BCrypt.checkpw(password, bdPassword);
    }



}
