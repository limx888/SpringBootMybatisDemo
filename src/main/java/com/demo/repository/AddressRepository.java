package com.demo.repository;

import com.demo.model.Addresslist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Addresslist, Integer> {
    List<Addresslist> findAllByName(String name);
}
