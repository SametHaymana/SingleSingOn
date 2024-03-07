package com.sso.api.repositories;

import com.sso.api.models.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
  Optional<User> findByEmail(String email);
}
