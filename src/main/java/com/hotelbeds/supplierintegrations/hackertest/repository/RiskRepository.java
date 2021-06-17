package com.hotelbeds.supplierintegrations.hackertest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hotelbeds.supplierintegrations.hackertest.entity.FailedLogin;

@Repository
public interface RiskRepository extends CrudRepository<FailedLogin, String>{
	List<FailedLogin> findByIp(String ip);
}
