package com.croustify.backend.repositories;

import com.croustify.backend.models.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureRepo extends JpaRepository<Picture, Long> {
}
