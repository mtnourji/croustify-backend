package com.croustify.backend.repositories;

import com.croustify.backend.models.LocationOwner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationOwnerRepo extends JpaRepository<LocationOwner, Long> {
}
