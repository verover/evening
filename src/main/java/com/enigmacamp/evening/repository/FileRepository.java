package com.enigmacamp.evening.repository;

import com.enigmacamp.evening.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,String> {
}
