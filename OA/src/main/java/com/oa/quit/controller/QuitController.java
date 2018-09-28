package com.oa.quit.controller;

import org.apache.commons.lang3.StringUtils;
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

import com.oa.common.beans.BeanUtils;
import com.oa.common.web.ExtAjaxResponse;
import com.oa.common.web.ExtjsPageRequest;
import com.oa.employee.entity.Employee;
import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;
import com.oa.quit.entity.QuitQueryDTO;
import com.oa.quit.service.IQuitService;


@RestController
@RequestMapping("/quit")
public class QuitController {
	@Autowired 
	private IQuitService quitService;
	
	@GetMapping
	public Page<QuitDTO> getPage(QuitQueryDTO quitQueryDTO,ExtjsPageRequest extjsPageRequest){
		/*if (quitQueryDTO.getEmployeeid()!=null) {
			quitQueryDTO.setEmployee1(employeeService.findById(quitQueryDTO.getEmployeeid()).orElse(null));
		}*/
		return quitService.findAllInDTO(QuitQueryDTO.getWhereClause(quitQueryDTO), extjsPageRequest.getPageable());
	}
	
	@PostMapping
	public ExtAjaxResponse save(@RequestBody QuitDTO quitDTO) 
	{
		System.out.println(quitDTO);
		Quit quit=new Quit();
		Employee emp=null;
		try {
			if (quitDTO.getEmployeeid()!=null&&!"".equals(quitDTO.getEmployeeid().trim())) {
				emp=new Employee();
				emp.setId(quitDTO.getEmployeeid());
			}
			BeanUtils.copyProperties(quitDTO, quit);
			if (quitDTO.getStatus()==null) {
				quit.setStatus(0);
			}
			quit.setEmployee(emp);
			quitService.save(quit);
			return new ExtAjaxResponse(true,"添加成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"添加失败");
		}
	}
	
	@PutMapping(value="{id}")
    public ExtAjaxResponse update(@PathVariable("id") Integer id,QuitDTO quitDTO) {
    	try {
    		Quit entity =new Quit();
			BeanUtils.copyProperties(quitDTO, entity);//使用自定义的BeanUtils
			entity.setId(id);
			if (StringUtils.isNotBlank(quitDTO.getEmployeeid())) {
				Employee employee=new Employee();
				employee.setId(quitDTO.getEmployeeid());
				entity.setEmployee(employee);
			}
			quitService.save(entity);
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
				Quit quit=quitService.findById(id);
				if (quit.getStatus()==2) {
					quit.setStatus(-2);
				}else {
					quit.setStatus(-1);
				}
				quitService.save(quit);
			}
			return new ExtAjaxResponse(true,"删除成功");
		} catch (Exception e) {
			return new ExtAjaxResponse(false,"删除失败");
		}
	}
	
	@RequestMapping("/deleteMore")
	public void deleteMore(Integer []id) {
		quitService.deleteAll(id);
	}
	
}
