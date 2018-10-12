package com.oa.officeresource.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.oa.common.web.SessionUtil;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.officeresource.entity.EmployeeResource;
import com.oa.officeresource.entity.OfficeResource;
import com.oa.officeresource.service.IEmployeeResourceService;
import com.oa.officeresource.service.IOfficeResourceService;

@RestController
@RequestMapping(value="/officeResource")
public class OfficeResourceController {
	@Autowired
	private IOfficeResourceService officeResourceService;
	
	@Autowired
	private IEmployeeResourceService employeeResourceService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	//添加保存，状态设置为0,表示该表为待发起状态
	@PostMapping
    public ExtAjaxResponse save(HttpSession session,@RequestBody OfficeResource officeResource) {
    	try {
    		String applicantId = SessionUtil.getUserName(session);
    		Optional<Employee> employee = employeeService.findById(applicantId);
    		if(applicantId!=null) {
    			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    			String time= sdf.format( new Date());
    			Date date=sdf.parse(time); 
    			officeResource.setEmployee(employee.orElse(null));
    			officeResource.setStatus(0);
    			officeResource.setApplyTime(date);
    			officeResourceService.save(officeResource);
    		}
    		return new ExtAjaxResponse(true,"保存成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"保存失败!");
	    }
    }
	
	//修改，状态设置为0,表示该表为待发起状态
	@PutMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse update(@PathVariable("id") Long id,@RequestBody OfficeResource officeResource) {
    	try {
    		OfficeResource entity = officeResourceService.findOne(id);
    		officeResource.setStatus(0);
			if(entity!=null) {
				BeanUtils.copyProperties(officeResource, entity);//使用自定义的BeanUtils
				officeResourceService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	//开始抢资源,状态设置为1,表示该表为可抢状态
	@PostMapping("/startGrabResource")
	public ExtAjaxResponse startGrabResource(@RequestParam(name="id") Long id) {
		try {
			OfficeResource officeResource = officeResourceService.findOne(id);
			officeResource.setStatus(1);
			officeResourceService.save(officeResource);
			return new ExtAjaxResponse(true,"开始抢资源！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"开始抢资源！");
		}
	}
	
	//抢资源
	@PostMapping("/grabResource")
	public ExtAjaxResponse grabResource(HttpSession session, @RequestParam(name="id") Long id) {
		try {
			OfficeResource officeResource = officeResourceService.findOne(id);
			String grabId = SessionUtil.getUserName(session);
    		Optional<Employee> employee = employeeService.findById(grabId);
			int grabResourceNum = officeResourceService.grabResourceNum();
			int tempCount = 0;
			int tempCount2 = 0;
			EmployeeResource employeeResource = employeeResourceService.findEmployeeResource(grabId, officeResource.getResourceName());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time= sdf.format( new Date());
			Date date=sdf.parse(time); 
			if(employeeResource!=null) {
				if(grabResourceNum>=officeResource.getLeftCount()) {
					tempCount = employeeResource.getCount() + officeResource.getLeftCount();
				}else {
					tempCount = employeeResource.getCount() + grabResourceNum;
				}
				employeeResource.setCount(tempCount);
				employeeResource.setRecentChangeTime(date);
				employeeResourceService.save(employeeResource);
			}else {
				EmployeeResource newEmployeeResource = new EmployeeResource();
				if(grabResourceNum>=officeResource.getLeftCount()) {
					tempCount = officeResource.getLeftCount();
				}else {
					tempCount = grabResourceNum;
				}
				newEmployeeResource.setCount(tempCount);
				newEmployeeResource.setResourceName(officeResource.getResourceName());
				newEmployeeResource.setRecentChangeTime(date);
				newEmployeeResource.setStatus(1);
				newEmployeeResource.setEmployee(employee.orElse(null));
				employeeResourceService.save(newEmployeeResource);
			}
			if(grabResourceNum>=officeResource.getLeftCount()) {
				tempCount2 = 0;
				officeResource.setStatus(2);
			}else {
				tempCount2 = officeResource.getLeftCount() - grabResourceNum;
			}
			officeResource.setLeftCount(tempCount2);
			officeResourceService.save(officeResource);
			return new ExtAjaxResponse(true,"抢资源成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"抢资源失败！");
		}
	}
}
