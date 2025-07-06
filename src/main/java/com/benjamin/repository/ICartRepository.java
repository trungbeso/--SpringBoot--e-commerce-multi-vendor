package com.benjamin.repository;

import com.benjamin.model.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<CartModel, Long> {
}
