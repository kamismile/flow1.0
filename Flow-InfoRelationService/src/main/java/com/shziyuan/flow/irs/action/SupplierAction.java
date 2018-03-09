package com.shziyuan.flow.irs.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shziyuan.flow.global.domain.flow.InfoSupplier;
import com.shziyuan.flow.irs.service.BindSupplierService;

@RestController
@RequestMapping("/supplier")
public class SupplierAction {
	@Autowired
	private BindSupplierService bindSupplierService;
	
	@GetMapping("/all")
	public List<InfoSupplier> getSuppliers() {
		return bindSupplierService.getSuppliers();
	}
	
	@PutMapping("/direct/{id}")
	public InfoSupplier reloadSupplier(@PathVariable("id") int id) {
		return bindSupplierService.reloadSupplier(id,false);
	}
}
