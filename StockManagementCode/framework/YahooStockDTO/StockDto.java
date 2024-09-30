
package YahooStockDTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StockDto {
	public StockDto(String name2, BigDecimal price2, BigDecimal change2, String currency2, BigDecimal bid2) {
		name=name2;
		price = price2;
		change = change2;
		currency = currency2;
		bid=bid2;
	}
	public String toString() {
		return name + " Share Price: "+ price+ "\n"+bid;
	}
	private String name;
	private BigDecimal price;
	private BigDecimal change;
	private String currency;
	private BigDecimal bid;

}