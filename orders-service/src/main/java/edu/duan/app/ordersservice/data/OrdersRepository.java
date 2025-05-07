package edu.duan.app.ordersservice.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrderEntity, Long> {

    Optional<OrderEntity> findById(Long id);
    List<OrderEntity> findAllByUserId(Long userId);
    List<OrderEntity> findAllByCreatedDateBetween(Date startDate, Date endDate);
    List<OrderEntity> findAllByUserIdAndState(Long userId, OrderStateEntity state);
    List<OrderEntity> findAllByState(OrderStateEntity state);
}
