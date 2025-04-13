package com.sensedia.sample.consents.domain.model;

import com.sensedia.sample.consents.domain.model.enums.StatusEnum;
import com.sensedia.sample.consents.domain.model.vo.CPFVO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_CONSENT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "cpf"))
    private CPFVO cpf;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "status_id")
    private StatusEnum status;

    @Column(name = "created_at")
    private LocalDateTime creationDateTime;

    @Column(name = "expires_at")
    private LocalDateTime expirationDateTime;

    @Column(name = "additional_info")
    @Size(min = 1, max = 50)
    private String additionalInfo;
}
