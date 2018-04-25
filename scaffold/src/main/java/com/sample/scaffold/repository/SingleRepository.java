package com.sample.scaffold.repository;


import com.sample.scaffold.model.Single;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SingleRepository extends JpaRepository<Single, Long>, JpaSpecificationExecutor<Single> {
}
