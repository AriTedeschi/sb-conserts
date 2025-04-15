package com.sensedia.sample.consents.domain.repository;

import com.sensedia.sample.consents.domain.model.ConsentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ConsentRepository extends JpaRepository<ConsentEntity, String> {

    @Query(nativeQuery = true, value = """
            select * 
            from tb_consent
            where
            (:id is null or id = :id)
            and (:cpf is null or cpf = :cpf)
            and (:status is null or status_id = :status)
            and (:starts_at is null or created_at >= :starts_at)
            and (:ends_at is null or created_at <= :ends_at)
            """)
    Page<ConsentEntity> search(@Param("id") String id,
                               @Param("cpf") String cpf,
                               @Param("status") Long status,
                               @Param("starts_at") LocalDate start,
                               @Param("ends_at") LocalDate end,
                               Pageable pageable);
}