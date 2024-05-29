package com.hodolog.api.Repository;

import com.hodolog.api.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
