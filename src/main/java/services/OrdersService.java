package services;


import models.Order;
import repositories.OrdersRepository;
import response.OrderNotFoundExcention;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrdersService {
    private OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public List<Order> findAll() {
        return ordersRepository.findAll();
    }

    public Order findOne(int id) {
        Optional<Order> foundBook = ordersRepository.findById(id);
        return foundBook.orElseThrow(OrderNotFoundExcention::new);
    }

    @Transactional
    public void save(Order order) {
        ordersRepository.save(order);
    }

    @Transactional
    public void update(int id, Order updatedOrder) {
        Order orderUpdated = ordersRepository.findById(id).get();
        updatedOrder.setId(id);
        updatedOrder.setOwner(orderUpdated.getOwner()); //добавляем владельца заказа
        ordersRepository.save(updatedOrder);
    }

    @Transactional
    public void delete(int id) {
        ordersRepository.deleteById(id);
    }

}
