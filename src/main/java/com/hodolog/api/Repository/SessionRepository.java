package com.hodolog.api.Repository;

import com.hodolog.api.domain.Session;
import com.hodolog.api.domain.Users;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Long> {

}
