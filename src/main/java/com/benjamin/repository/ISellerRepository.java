package com.benjamin.repository;

import com.benjamin.domain.EAccountStatus;
import com.benjamin.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISellerRepository extends JpaRepository<SellerModel, Long> {
    SellerModel findByEmail(String email);
    List<SellerModel> findByAccountStatus(EAccountStatus status);
}
