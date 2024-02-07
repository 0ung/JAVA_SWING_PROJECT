package test;

import org.junit.jupiter.api.Test;

import models.service.ExcelService;

public class ExcelTest {
	public ExcelService excelService = new ExcelService();

	@Test
	void asd() {
		excelService.createExcel("김영웅", "01075743839");
	}
}
