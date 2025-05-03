package edu.duan.app.ordersservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Integer> {

    Optional<OrderEntity> findById(Integer id);
    List<OrderEntity> findAllByUserId(Integer userId);
    List<OrderEntity> findAllByCreatedDateBetween(Date startDate, Date endDate);
    List<OrderEntity> findAllByUserIdAndState(Integer userId, OrderStateEntity state);
    List<OrderEntity> findAllByState(OrderStateEntity state);
}
