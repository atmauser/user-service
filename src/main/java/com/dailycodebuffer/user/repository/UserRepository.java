package com.dailycodebuffer.user.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dailycodebuffer.user.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{

	User findByUserId(Long userid);

}
