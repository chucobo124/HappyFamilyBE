package glhf.happyfamily.models;

import java.util.Set;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;
    private String accountingActivityID;
    private String title;
    private String description;
    private int paid;
    @OneToMany
    private Set<Lent> lents;
    private String userID;
}
