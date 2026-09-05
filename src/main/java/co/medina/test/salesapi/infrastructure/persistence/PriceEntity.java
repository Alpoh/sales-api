package co.medina.test.salesapi.infrastructure.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "prices")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PriceEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Long brandId;

	private LocalDateTime startDate;

	private LocalDateTime endDate;

	private Long priceListId;

	private Long productId;

	private Integer priority;

	private BigDecimal price;

	private String currency;

}
