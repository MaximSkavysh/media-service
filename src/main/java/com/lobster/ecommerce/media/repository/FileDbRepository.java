package com.lobster.ecommerce.media.repository;

import com.lobster.ecommerce.media.model.FileDb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileDbRepository extends JpaRepository<FileDb, Long> {
}
