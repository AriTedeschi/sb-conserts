package com.sensedia.sample.consents.domain.model;

import com.sensedia.sample.consents.domain.model.enums.StatusEnum;
import com.sensedia.sample.consents.domain.model.vo.CPFVO;
import com.sensedia.sample.consents.domain.model.vo.DateVO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "status_id")
    private Long status;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "created_at"))
    private DateVO creationDateTime;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "expires_at"))
    private DateVO expirationDateTime;

    @Column(name = "additional_info")
    @Size(min = 1, max = 50)
    private String additionalInfo;

    @Transient
    public StatusEnum getStatus() {
        return StatusEnum.byId(this.status);
    }

    public void setStatus(StatusEnum status) {
        this.status = (status != null) ? status.getId() : null;
    }
}
