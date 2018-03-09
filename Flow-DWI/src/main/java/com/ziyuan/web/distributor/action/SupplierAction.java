package com.ziyuan.web.distributor.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ziyuan.web.distributor.service.SupplierReportService;

@RestController
@RequestMapping("/open-api/spi")
public class SupplierAction {
	@Autowired
	private SupplierReportService supplierReportService;
	
	@RequestMapping("/{id}")
	@ResponseBody
	public String passiveReport(@PathVariable("id") int supplier_id,@RequestBody String input) {
		supplierReportService.receiveSupplierReport(supplier_id, input);
		return "ok";
	}
}
