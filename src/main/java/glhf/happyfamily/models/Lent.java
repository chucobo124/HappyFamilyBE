package glhf.happyfamily.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Setter
@Getter
public class Lent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private Integer lent;
    private String toUserID;
}
