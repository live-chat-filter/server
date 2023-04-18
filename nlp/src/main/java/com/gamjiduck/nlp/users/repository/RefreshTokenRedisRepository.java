package com.gamjiduck.nlp.users.repository;

import com.gamjiduck.nlp.users.domain.RefreshToken;
import org.springframework.data.repository.CrudRepository;

public interface RefreshTokenRedisRepository extends CrudRepository<RefreshToken, String> {
}
