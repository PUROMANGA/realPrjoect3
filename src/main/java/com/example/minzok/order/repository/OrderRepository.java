package com.example.minzok.order.repository;

import com.example.minzok.member.entity.Member;
import com.example.minzok.order.entity.Order;
import com.example.minzok.order.entity.OrderStatus;
import com.example.minzok.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    int countByStoreAndMemberAndOrderStatusNot(Store store, Member member, OrderStatus status);

}
