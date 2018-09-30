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
import org.springframework.web.bind.annotation.RestController;


import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.department.entity.DepartmentQueryDTO;
import com.oa.salary.entity.SalaryPay;
import com.oa.salary.entity.SalaryPayDTO;
import com.oa.salary.entity.SalaryPayQueryDTO;
import com.oa.salary.service.ISalaryPayService;



@RestController
@RequestMapping("/salarypay")
public class SalaryPayController {
	@Autowired
	private ISalaryPayService salaryPayService;
	
	@GetMapping
	public Page<SalaryPayDTO> getPage(SalaryPayQueryDTO salaryPayQueryDTO,ExtjsPageRequest extjsPageRequest){
		System.out.println(salaryPayQueryDTO);
		//return salaryPayService.findAll(departmentQueryDTO.getWhereClause(departmentQueryDTO), extjsPageRequest.getPageable());
		return salaryPayService.findAllInDTO(salaryPayQueryDTO.getWhereClause(salaryPayQueryDTO), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(@RequestBody SalaryPayDTO salaryPayDTO) 
	{
		try {
			if (salaryPayDTO.getStatus()==null) {
				salaryPayDTO.setStatus(0);
			}
			salaryPayService.save(salaryPayDTO);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,@RequestBody SalaryPayDTO salaryPayDTO) {
    	try {
    		salaryPayDTO.setId(id);
    		salaryPayService.update(salaryPayDTO);
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse delete(@PathVariable Integer id) {
		try {
			if(id!=null) {
				salaryPayService.deleteById(id);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	@RequestMapping("/deleteMore")
	public ExtAjaxResponse deleteMore(Integer []id) {
		try {
			if(id!=null) {
				salaryPayService.deleteAll(id);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	
}
