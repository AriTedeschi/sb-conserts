package com.sensedia.sample.consents.domain.repository;

import com.sensedia.sample.consents.domain.model.ConsentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRepository extends JpaRepository<ConsentEntity, String> {
}