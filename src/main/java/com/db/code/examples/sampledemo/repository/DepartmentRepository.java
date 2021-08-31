package com.db.code.examples.sampledemo.repository;

import com.db.code.examples.sampledemo.model.Department;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long> {
}
