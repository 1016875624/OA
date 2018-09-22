package com.oa.quit.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;
import com.oa.quit.entity.QuitQueryDTO;
import com.oa.quit.service.IQuitService;


@RestController
@RequestMapping("/quit")
public class QuitController {
	@Autowired 
	private IQuitService quitService;
	@Autowired
	private IEmployeeService employeeService;
	@GetMapping
	public Page<Quit> getPage(QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest){
		if (quitQueryDTO.getEmployeeid1()!=null) {
			quitQueryDTO.setEmployee1(employeeService.findById(quitQueryDTO.getEmployeeid1()).orElse(null));
		}
		return quitService.findAll(QuitQueryDTO.getWhereClause(quitQueryDTO), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(QuitDTO quitDTO) 
	{
		Employee em= null;
		try {
			if (quitDTO.getQuitEmployeeid()!=null&&!"".equals(quitDTO.getQuitEmployeeid().trim())) {
				em= employeeService.findById(quitDTO.getQuitEmployeeid()).orElse(null);
			}
			Quit quit=new Quit();
			BeanUtils.copyProperties(quitDTO, quit);
			quit.setStatus(0);
			quit.setEmployee(em);
			quitService.save(quit);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,Quit quit) {
    	try {
    		Quit entity = quitService.findById(id);
			if(entity!=null) {
				BeanUtils.copyProperties(quit, entity);//使用自定义的BeanUtils
				quitService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	@DeleteMapping(value="/{id}")
	public ExtAjaxResponse deleteQuestion(@PathVariable Integer id) {
		try {
			if(id!=null) {
				Quit quit=quitService.findById(id);
				quit.setStatus(0);
				quitService.save(quit);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	
}
