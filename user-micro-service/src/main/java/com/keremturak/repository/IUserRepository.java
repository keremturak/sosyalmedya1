package com.keremturak.repository;

import com.keremturak.repository.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface IUserRepository extends MongoRepository<User, String> {
    Optional<User> findOptionalByAuthid(Long authId);
}
