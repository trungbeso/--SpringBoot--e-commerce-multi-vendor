package com.benjamin.repository;

import com.benjamin.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ISellerRepository extends JpaRepository<SellerModel, Long> {
    SellerModel findByEmail(String email);
}
