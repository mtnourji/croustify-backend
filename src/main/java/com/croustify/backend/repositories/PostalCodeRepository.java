package com.croustify.backend.repositories;

import com.croustify.backend.models.PostalCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostalCodeRepository extends JpaRepository<PostalCode, Long> {
    List<PostalCode> findAllByPostalCodeContainingOrLocalityContaining(String query, String query1);
}
