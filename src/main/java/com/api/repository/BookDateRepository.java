package com.api.repository;

import com.api.model.BookDate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDateRepository extends JpaRepository<BookDate,Long> {
}
