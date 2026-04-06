package pants.pro.investment_watchlist.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pants.pro.investment_watchlist.model.enums.RiskLevel;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "assets")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Asset extends AbstractEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID uuid;

    @Column(nullable = false, length = 10)
    private String ticker;

    @Column(nullable = false)
    private String name;

    @Column(precision = 18, scale = 2)
    private BigDecimal currentPrice;

    @Column(precision = 18, scale = 0)
    private BigDecimal targetPrice;

    @Enumerated(EnumType.STRING)
    private RiskLevel riskLevel;

    @PrePersist
    public void initializeUuid() {
        if (uuid == null) uuid = UUID.randomUUID();
    }
}
