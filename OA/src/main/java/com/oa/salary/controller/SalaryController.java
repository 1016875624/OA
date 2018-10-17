package com.oa.salary.controller;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.salary.entity.Salary;
import com.oa.salary.entity.SalaryDTO;
import com.oa.salary.entity.SalaryQueryDTO;
import com.oa.salary.service.ISalaryService;

@RestController
@RequestMapping("/salary")

public class SalaryController {
	// 注入调用ISalaryService方法
	@Autowired
	private ISalaryService salaryService;

	// 查找分页显示
	@GetMapping
	public Page<SalaryDTO> getPage(SalaryQueryDTO salaryQueryDTO, ExtjsPageRequest pageRequest) {
		return salaryService.findAllInDto(salaryQueryDTO.getWhereClause(salaryQueryDTO), pageRequest.getPageable());
	}

	// 根据ID查找
	@GetMapping(value = "{id}")
	public Salary getOne(@PathVariable("id") Integer id) {
		return salaryService.findById(id).get();
	}

	// 根据ID删除
	@DeleteMapping(value = "{id}")
	public ExtAjaxResponse delete(@PathVariable("id") Integer id) {
		try {
			if (id != null) {
				salaryService.deleteById(id);
			}
			return new ExtAjaxResponse(true, "删除成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "删除失败!");
		}
	}

	// 批量删除
	@RequestMapping(value = "/deletes")
	public ExtAjaxResponse deleteRows(@RequestParam(name = "ids") Integer[] ids) {
		try {
			if (ids != null) {
				salaryService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true, "批量删除成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "批量删除失败!");
		}
	}

	// 修改更新
	@PutMapping(value = "{id}")
	public @ResponseBody ExtAjaxResponse update(@PathVariable("id") Integer id, @RequestBody SalaryDTO salaryDTO) {
		try {
			Salary entity = salaryService.findById(id).orElse(null);
			if (entity != null) {
				BeanUtils.copyProperties(salaryDTO, entity);
				salaryService.save(entity);
			}
			return new ExtAjaxResponse(true, "更新成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "更新失败!");
		}
	}

	// 添加
	@PostMapping
	public ExtAjaxResponse save(@RequestBody SalaryDTO salaryDTO) {
		try {
			Salary salary = SalaryDTO.DtoToEntity(salaryDTO);
			salaryService.save(salary);
			return new ExtAjaxResponse(true, "保存成功!");
		} catch (Exception e) {
			return new ExtAjaxResponse(true, "保存失败!");
		}
	}
//	@RequestMapping("/salary")
//	public String testData() {
//		try {
//	employee.findAll()
//			for (Employee employee:) {
//				Salary salary = new Salary();
//				salary.setBonus(100D);
//				salary.setSal(1000D);
//				salary.setSubsidy(1D);
//				salary.setWorktimeMoney(38D);
//				salary.setWorkMonth(48);
//	
//	salary.setEmp(emp);
//				salaryService.save(salary);
//			}
//			return "success:true";
//		} catch (Exception e) {
//			return "success:false";
//		}
//	}
//	@RequestMapping("/salary")
//	public void testData() {
//		try {
//			List<Employee> employees = (List<Employee>) employeeRepository.findAll();
//			for (Employee emp : employees) {
//				Salary salary = new Salary();				
//				salary.setEmployee(emp);
//				salaryService.save(salary);
//			}
//			return "true";
//		} catch (Exception e) {
//			return "false";
//		}
//	}
}
