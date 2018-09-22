package com.oa.employee.controller;

import java.util.Date;

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
import com.oa.department.entity.Department;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.entity.EmployeeQueryDTO;
import com.oa.employee.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;
	
	//private id
	//添加
	@PostMapping
	public String save(@RequestBody EmployeeDTO employeeDTO) 
	{	
		System.out.println(employeeDTO);
		Employee employee = new Employee();
		BeanUtils.copyProperties(employeeDTO, employee);
		Employee leader=null;
		if (employeeDTO.getLeaderid()!=null) {
			leader=employeeService.findById(employeeDTO.getLeaderid()).orElse(null);
		}
		//Department department=null;
		employee.setLeader(leader);
		employee.setDepartment(null);
		System.out.println(employee);
		try {
			//Employee entity = employeeService.findById(id).get();
			employeeService.save(employee);
			return "success:true";
		} catch (Exception e) {
			return "success:false";
		}
	}
		
	//查找分页显示
	@GetMapping
	public Page<Employee> getPage(EmployeeQueryDTO employeeQueryDTO , ExtjsPageRequest pageRequest) 
	{
		return employeeService.findAll(employeeQueryDTO.getWhereClause(employeeQueryDTO), pageRequest.getPageable());
	}
	
	/*//根据id查找
	@GetMapping(value="{id}")
	public Employee getOne(@PathVariable("id") String id) 
	{
		return employeeService.findById(id).get();
	}
	*/
	
	//根据id删除
	@DeleteMapping(value="{id}")
	public ExtAjaxResponse delete(@PathVariable("id") String id) 
	{
		try {
			if(id!=null) {
				employeeService.deleteById(id);
			}
			return new ExtAjaxResponse(true,"删除成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"删除失败！");
		}
	}
	
	//批量删除
	@PostMapping("/deletes")
	public ExtAjaxResponse deleteRows(@RequestParam(name="ids") String[] ids) 
	{
		try {
			if(ids!=null) {
				employeeService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true,"批量删除成功！");
		} catch (Exception e) {
			return new ExtAjaxResponse(true,"批量删除失败！");
		}
	}
	
	//修改更新
	@PutMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse update(@PathVariable("id") String id,@RequestBody Employee employee) {
    	try {
    		Employee entity = employeeService.findById(id).get();
			if(entity!=null) {
				BeanUtils.copyProperties(employee, entity);//使用自定义的BeanUtils
				employeeService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	/**
	 * 测试数据
	 */
	@RequestMapping("/data")
	public String testData() {
		try {
			for (int i = 1; i < 10; i++) {
				Employee employee = new Employee();
				employee.setId(i+"");
				employee.setName("a"+i);
				employee.setPassword("111");
				employee.setEmail(i+"@qq.com");
				//employee.setDepartment("测试部");
				employee.setPosition("测试员"+i);
				employee.setStatus(i%2);
				employee.setEntryTime(new Date());
				employeeService.save(employee);
			}
			return "success:true";
		} catch (Exception e) {
			return "success:false";
		}
	}
/*	
	public Object updateUser(@RequestBody UserInfo userEntity)
	{
		UserInfo user = userRepositoy.findUserInfoById(userEntity.getId());
		if (user != null)
		{
			user.setName(userEntity.getName());
			userRepositoy.save(user);
		}
		ResultMsg resultMsg = new ResultMsg(ResultStatusCode.OK.getErrcode(), ResultStatusCode.OK.getErrmsg(), null);
		return resultMsg;
	}
*/
}
