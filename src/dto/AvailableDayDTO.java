package dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AvailableDayDTO extends ClassDTO{
	
	public AvailableDayDTO() {
		super();
	}
	
	private String availableYearMonth;
	private String className;
	private int availableDay;

}
