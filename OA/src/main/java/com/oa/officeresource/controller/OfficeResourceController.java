package com.oa.officeresource.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import com.oa.common.web.SessionUtil;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.IEmployeeService;
import com.oa.officeresource.entity.EmployeeResource;
import com.oa.officeresource.entity.OfficeResource;
import com.oa.officeresource.entity.OfficeResourceDTO;
import com.oa.officeresource.entity.OfficeResourceQueryDTO;
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
	
	//删除
	@DeleteMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse delete(@PathVariable("id") Long id) {
    	try {
    		OfficeResource entity = officeResourceService.findOne(id);
			if(entity!=null) {
				officeResourceService.delete(id);
			}
    		return new ExtAjaxResponse(true,"删除成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"删除失败!");
	    }
    }
	
	//删除多个
	@PostMapping("/deletes")
	public ExtAjaxResponse deleteRows(@RequestParam(name="ids") Long[] ids) 
	{
		try {
			if(ids!=null) {
				officeResourceService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true,"批量删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"批量删除失败！");
		}
	}
	
	//根据当前登录用户的id来分页查询
	@GetMapping
    public Page<OfficeResourceDTO> findAllOfficeResource(OfficeResourceQueryDTO officeResourceQueryDTO,HttpSession session,ExtjsPageRequest pageable) 
	{
		Page<OfficeResourceDTO> page;
		String applicantId = (String) session.getAttribute("userId");
		if(applicantId!=null) {
			page = officeResourceService.findAllInDto(OfficeResourceQueryDTO.getWhereClause(officeResourceQueryDTO), pageable.getPageable());
			System.out.println(OfficeResourceQueryDTO.getWhereClause(officeResourceQueryDTO));
		}else {
			page = new PageImpl<OfficeResourceDTO>(new ArrayList<OfficeResourceDTO>(),pageable.getPageable(),0);
		}
		return page;
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
	
	//抢资源,可抢资源为0时，设状态值为2，表示已抢完
	@PostMapping("/grabResource")
	public ExtAjaxResponse grabResource(HttpSession session, @RequestParam(name="id") Long id) {
		try {
			OfficeResource officeResource = officeResourceService.findOne(id);
			String grabId = SessionUtil.getUserName(session);
    		Optional<Employee> employee = employeeService.findById(grabId);
			int grabResourceNum = officeResourceService.grabResourceNum(10);
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
	
	//开始抽奖资源,状态设置为3,表示该表为可抽取状态
	@PostMapping("/startLuckyDraw")
	public ExtAjaxResponse startLuckyDraw(@RequestParam(name="id") Long id) {
		try {
			OfficeResource officeResource = officeResourceService.findOne(id);
			officeResource.setStatus(3);
			officeResourceService.save(officeResource);
			return new ExtAjaxResponse(true,"开始抽资源！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"开始抽资源！");
		}
	}
	
	//抽奖资源,可抽资源为0时，设状态值为4，表示已抽完
	@PostMapping("/luckyDraw")
	public ExtAjaxResponse luckyDraw(HttpSession session, @RequestParam(name="id") Long id) {
		try {
			OfficeResource officeResource = officeResourceService.findOne(id);
			String grabId = SessionUtil.getUserName(session);
			int luckyDrawResourceNum = officeResourceService.grabResourceNum(10);
			EmployeeResource employeeResource = employeeResourceService.findEmployeeResource(grabId, officeResource.getResourceName());
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time= sdf.format( new Date());
			Date date=sdf.parse(time); 
			int tempCount = 0;
			int tempCount2 = 0;
			
			if(employeeResource!=null) {
				if(employeeResource.getCount()>=6) {
					if(luckyDrawResourceNum>=officeResource.getLeftCount()) {
						tempCount = employeeResource.getCount() + officeResource.getLeftCount();
						employeeResource.setCount(tempCount);
						employeeResource.setRecentChangeTime(date);
						officeResource.setLeftCount(0);
						officeResource.setStatus(4);
					}else {
						tempCount = employeeResource.getCount() - 6 + luckyDrawResourceNum;
						employeeResource.setCount(tempCount);
						employeeResource.setRecentChangeTime(date);
						tempCount2 = officeResource.getLeftCount() - luckyDrawResourceNum + 6;
						officeResource.setLeftCount(tempCount2);
					}
					employeeResourceService.save(employeeResource);
				}else {
					return new ExtAjaxResponse(true,"你的"+officeResource.getResourceName()+"资源不足6个");
				}
			}else {
				return new ExtAjaxResponse(true,"你没有"+officeResource.getResourceName()+"资源");
			}
			
			officeResourceService.save(officeResource);
			return new ExtAjaxResponse(true,"抽资源成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"抽资源失败！");
		}
	}
	
	//分配资源
}
