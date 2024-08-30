package com.project.memmem.domain.repository.image;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.memmem.domain.entity.ImageEntity;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, Long> {

}
