package com.projet.buyback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projet.buyback.model.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
