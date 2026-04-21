package pants.pro.analyst_registry.model.static_data;

import jakarta.persistence.*;
import lombok.*;
import pants.pro.analyst_registry.model.Analyst;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Table(name = "firms")
public class Firm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @Getter(AccessLevel.PROTECTED)
    @Setter(AccessLevel.PRIVATE)
    @OneToMany(mappedBy = "firm", fetch = FetchType.LAZY)
    private Set<Analyst> analysts = new HashSet<>();

    public Set<Analyst> getAllAnalysts() {
        return Collections.unmodifiableSet(analysts);
    }

    public void addAnalyst(Analyst analyst) {
        if (analysts == null) analysts = new HashSet<>();
        analysts.add(analyst);
        analyst.setFirm(this);
    }

    public void removeAnalyst(Analyst analyst) {
        if (analysts == null) return;
        analysts.remove(analyst);
        analyst.setFirm(null);
    }
}
