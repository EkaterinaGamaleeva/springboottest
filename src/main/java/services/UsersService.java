package services;


import models.Order;
import models.User;
import repositories.UsersRepository;
import response.UsersNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {
    private UsersRepository usersRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository) {

        this.usersRepository = usersRepository;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    public User findOne(int id) {
        Optional<User> foundPerson = usersRepository.findById(id);
        return foundPerson.orElseThrow(UsersNotFoundException::new);
    }

    @Transactional
    public void save(User user) {
        usersRepository.save(user);
    }

    @Transactional
    public void update(int id, User updatedUser) {
        updatedUser.setId(id);
        usersRepository.save(updatedUser);
    }

    @Transactional
    public void delete(int id) {
        usersRepository.deleteById(id);
    }

    public Optional<User> getUsersByFullName(String fullName) {

        return usersRepository.findByFullName(fullName);
    }

    public Optional<User> getUsersByEmail(String email) {

        return usersRepository.findByEmail(email);
    }

    public List<Order> getOrdersByPersonId(int id) {
        Optional<User> user = usersRepository.findById(id);
        return user.get().getOrders();
    }
}