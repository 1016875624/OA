package com.oa.salary.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryQueryDTO;
import com.oa.salary.service.ISalaryService;

@RestController
@RequestMapping("/salary")
public class SalaryController {
	@Autowired
	private ISalaryService salaryService;

	@GetMapping
	public Page<Salary> getPage(SalaryQueryDTO salaryQueryDTO, ExtjsPageRequest pageRequest) {
		return salaryService.findAll(salaryQueryDTO.getWhereClause(salaryQueryDTO), pageRequest.getPageable());
	}

	@GetMapping(value = "{id}")
	public Salary getOne(@PathVariable("id") Integer id) {
		return salaryService.findByid(id).get();
	}

	@DeleteMapping(value = "{id}")
	public ExtAjaxResponse delete(@PathVariable("id") Integer id) {
		try {
			if (id != null) {
				salaryService.deleteById(id);
			}
			return new ExtAjaxResponse(true, "删除成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "删除失败！");
		}
	}

	@DeleteMapping("/deletes")
	public ExtAjaxResponse deleteRows(@RequestParam(name = "ids") Integer[] ids) {
		try {
			if (ids != null) {
				salaryService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true, "批量删除成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "批量删除失败！");
		}
	}

	@PutMapping(value = "{id}")
	public ExtAjaxResponse update(@PathVariable("id") Integer myId, @RequestBody Salary dto) {
		try {
			Salary entity = salaryService.findByid(myId).get();
			if (entity != null) {
				BeanUtils.copyProperties(dto, entity);
				salaryService.save(entity);
			}
			return new ExtAjaxResponse(true, "更新成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "更新失败");
		}
	}

	@PostMapping
	public ExtAjaxResponse save(@RequestBody Salary salary) {
		try {
			salaryService.save(salary);
			return new ExtAjaxResponse(true, "保存成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "保存失败！");
		}
	}
	

//	@RequestMapping("/salary")
//	public String testData() {
//		try {
//			for (int i = 0; i < 100; i++) {
//				Salary salary = new Salary();
//				salary.setId(i);
//				salary.setBonus(100D);
//				salary.setSal(1000D);
//				salary.setSubsidy(1D);
//				salary.setSalaryNumber("A100" + i);
//				salary.setWorktimeMoney(38D);
//				salary.setWorkMonth(48);
//				salaryService.save(salary);
//			}
//			return "success:true";
//		} catch (Exception e) {
//			return "success:false";
//		}
//	}
}
