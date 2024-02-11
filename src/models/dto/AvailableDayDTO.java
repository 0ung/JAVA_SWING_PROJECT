package models.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableDayDTO{
	private String availableYearMonth;
	private String className;
	private int availableDay;

}
