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
import com.oa.officeresource.entity.EmployeeResourceDTO;
import com.oa.officeresource.entity.EmployeeResourceQueryDTO;
import com.oa.officeresource.service.IEmployeeResourceService;

@RestController
@RequestMapping(value="/employeeResource")
public class EmployeeResourceController {
	@Autowired
	private IEmployeeResourceService employeeResourceService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	//添加保存
	@PostMapping
    public ExtAjaxResponse save(HttpSession session,@RequestBody EmployeeResource employeeResource) {
    	try {
    		String applicantId = SessionUtil.getUserName(session);
    		Optional<Employee> employee = employeeService.findById(applicantId);
    		if(applicantId!=null) {
    			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    			String time= sdf.format( new Date());
    			Date date=sdf.parse(time); 
    			employeeResource.setEmployee(employee.orElse(null));
    			employeeResource.setStatus(1);
    			employeeResource.setRecentChangeTime(date);
    			employeeResourceService.save(employeeResource);
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
    		EmployeeResource entity = employeeResourceService.findOne(id);
			if(entity!=null) {
				employeeResourceService.delete(id);
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
				employeeResourceService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true,"批量删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"批量删除失败！");
		}
	}
	
	//根据当前登录用户的id来分页查询,我的资源
	@GetMapping
    public Page<EmployeeResourceDTO> findAllOfficeResourceById(EmployeeResourceQueryDTO employeeResourceQueryDTO,HttpSession session,ExtjsPageRequest pageable) 
	{
		Page<EmployeeResourceDTO> page;
		String applicantId = (String) session.getAttribute("userId");
		if(applicantId!=null) {
			employeeResourceQueryDTO.setEmployeeId(applicantId);
			page = employeeResourceService.findAllInDto(EmployeeResourceQueryDTO.getWhereClause(employeeResourceQueryDTO), pageable.getPageable());
			System.out.println(EmployeeResourceQueryDTO.getWhereClause(employeeResourceQueryDTO));
		}else {
			page = new PageImpl<EmployeeResourceDTO>(new ArrayList<EmployeeResourceDTO>(),pageable.getPageable(),0);
		}
		return page;
    }
	
	//根据资源状态来查询资源
	@GetMapping("/sellAndBuy")
    public Page<EmployeeResourceDTO> findAllOfficeResource(EmployeeResourceQueryDTO employeeResourceQueryDTO,HttpSession session,ExtjsPageRequest pageable) 
	{
		Page<EmployeeResourceDTO> page;
		String applicantId = (String) session.getAttribute("userId");
		if(applicantId!=null) {
			employeeResourceQueryDTO.setStatus(1);
			employeeResourceQueryDTO.setNoNeedEmployeeId(applicantId);
			page = employeeResourceService.findAllInDto(EmployeeResourceQueryDTO.getWhereClause(employeeResourceQueryDTO), pageable.getPageable());
			System.out.println(EmployeeResourceQueryDTO.getWhereClause(employeeResourceQueryDTO));
		}else {
			page = new PageImpl<EmployeeResourceDTO>(new ArrayList<EmployeeResourceDTO>(),pageable.getPageable(),0);
		}
		return page;
    }
	
	//修改
	@PutMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse update(@PathVariable("id") Long id,@RequestBody EmployeeResource employeeResource) {
    	try {
    		EmployeeResource entity = employeeResourceService.findOne(id);
    		employeeResource.setStatus(0);
			if(entity!=null) {
				BeanUtils.copyProperties(employeeResource, entity);//使用自定义的BeanUtils
				employeeResourceService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	//交易
	@PostMapping("/trade")
	public ExtAjaxResponse trade(HttpSession session, @RequestParam(name="id") Long id, @RequestParam(name="buyNum") int buyNum, @RequestParam(name="sellNum") int sellNum, @RequestParam(name="resourceNameForTrade") String resourceNameForTrade) {
		try {
			EmployeeResource employeeResource = employeeResourceService.findOne(id);
			String tradeId = (String) session.getAttribute("userId");
			String tempRemark = "";
			EmployeeResource newEmployeeResource = new EmployeeResource();
			EmployeeResource employeeResourceInTradePeople = employeeResourceService.findEmployeeResourceByStatus(tradeId, resourceNameForTrade, 1);
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time= sdf.format( new Date());
			Date date=sdf.parse(time);
			if(employeeResourceInTradePeople==null||employeeResourceInTradePeople.getCount()<sellNum) {
				return new ExtAjaxResponse(true,"申请交易失败!请检查你是否有足够的"+resourceNameForTrade+"资源");
			}
			else if(employeeResource.getCount()<buyNum) {
				return new ExtAjaxResponse(true,"申请交易失败!该同事没有那么多的"+resourceNameForTrade+"资源");
			}else {
				newEmployeeResource.setRecentChangeTime(date);
				newEmployeeResource.setCount(sellNum);
				newEmployeeResource.setResourceName(resourceNameForTrade);
				newEmployeeResource.setStatus(0);
				newEmployeeResource.setEmployee(employeeResource.getEmployee());
				newEmployeeResource.setTraderId(tradeId);
				tempRemark = "失去" + buyNum + "个" + employeeResource.getResourceName() + "资源,获得" + sellNum + "个" + resourceNameForTrade + "资源";
				newEmployeeResource.setRemark(tempRemark);
				newEmployeeResource.setLoseCount(buyNum);
				newEmployeeResource.setLoseResourceName(employeeResource.getResourceName());
				employeeResourceService.save(newEmployeeResource);
			}
    		return new ExtAjaxResponse(true,"申请交易成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"申请交易失败!");
	    }
	}
	
	//同意交易
	@PostMapping("/sureTrade")
    public @ResponseBody ExtAjaxResponse sureTrade(HttpSession session, @RequestParam(name="id") Long id) {
    	try {
    		EmployeeResource employeeResource = employeeResourceService.findOne(id);
    		String sureId = (String) session.getAttribute("userId");
    		String tradeId = employeeResource.getTraderId();
    		EmployeeResource employeeResourceInSure = employeeResourceService.findEmployeeResourceByStatus(sureId, employeeResource.getResourceName(), 1);
    		EmployeeResource employeeResourceInCut = employeeResourceService.findEmployeeResourceByStatus(sureId, employeeResource.getLoseResourceName(), 1);
    		Optional<Employee> sureEmployee = employeeService.findById(sureId);
    		
    		EmployeeResource employeeResourceInTradeSure = employeeResourceService.findEmployeeResourceByStatus(tradeId, employeeResource.getLoseResourceName(), 1);
    		EmployeeResource employeeResourceInTradeCut = employeeResourceService.findEmployeeResourceByStatus(tradeId, employeeResource.getResourceName(), 1);
    		Optional<Employee> tradeEmployee = employeeService.findById(tradeId);
    		SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			String time= sdf.format( new Date());
			Date date=sdf.parse(time);
			int tempCount = 0;
			if(employeeResource.getCount()>employeeResourceInTradeCut.getCount()) {
				return new ExtAjaxResponse(false,"交易失败，对方没有那么多资源!");
			}
			if(employeeResource.getLoseCount()>employeeResourceInCut.getCount()) {
				return new ExtAjaxResponse(false,"交易失败，你没有那么多资源!");
			}
    		if(employeeResourceInSure==null) {
    			EmployeeResource newEmployeeResource = new EmployeeResource();
    			newEmployeeResource.setRecentChangeTime(date);
    			newEmployeeResource.setStatus(1);
    			newEmployeeResource.setCount(employeeResource.getCount());
    			newEmployeeResource.setResourceName(employeeResource.getResourceName());
    			newEmployeeResource.setEmployee(sureEmployee.orElse(null));
    			employeeResourceService.save(newEmployeeResource);
    		}else {
    			tempCount = employeeResourceInSure.getCount() + employeeResource.getCount();
    			employeeResourceInSure.setCount(tempCount);
    			employeeResourceInSure.setRecentChangeTime(date);
    			employeeResourceService.save(employeeResourceInSure);
    		}
    		if(employeeResourceInCut!=null) {
    			tempCount = employeeResourceInCut.getCount() - employeeResource.getLoseCount();
    			employeeResourceInCut.setCount(tempCount);
    			employeeResourceInCut.setRecentChangeTime(date);
    			employeeResourceService.save(employeeResourceInCut);
    		}
    		
    		if(employeeResourceInTradeSure==null) {
    			EmployeeResource newEmployeeResource = new EmployeeResource();
    			newEmployeeResource.setRecentChangeTime(date);
    			newEmployeeResource.setStatus(1);
    			newEmployeeResource.setCount(employeeResource.getLoseCount());
    			newEmployeeResource.setResourceName(employeeResource.getLoseResourceName());
    			newEmployeeResource.setEmployee(tradeEmployee.orElse(null));
    			employeeResourceService.save(newEmployeeResource);
    		}else {
    			tempCount = employeeResourceInTradeSure.getCount() + employeeResource.getLoseCount();
    			employeeResourceInTradeSure.setCount(tempCount);
    			employeeResourceInTradeSure.setRecentChangeTime(date);
    			employeeResourceService.save(employeeResourceInTradeSure);
    		}
    		
    		if(employeeResourceInTradeCut!=null) {
    			tempCount = employeeResourceInTradeCut.getCount() - employeeResource.getCount();
    			employeeResourceInTradeCut.setCount(tempCount);
    			employeeResourceInTradeCut.setRecentChangeTime(date);
    			employeeResourceService.save(employeeResourceInTradeCut);
    		}
    		employeeResourceService.delete(id);
    		return new ExtAjaxResponse(true,"交易成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"交易失败!");
	    }
    }
	
	//取消交易
	@PostMapping("/cancelTrade")
	public ExtAjaxResponse cancelTrade(@RequestParam(name="id") Long id) 
	{
		try {
			EmployeeResource entity = employeeResourceService.findOne(id);
			if(entity!=null) {
				employeeResourceService.delete(id);
			}
			return new ExtAjaxResponse(true,"取消交易成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"取消交易失败！");
		}
	}
	
	//兑换资源
	@PostMapping("/exchangeResource")
	public ExtAjaxResponse exchangeResource(@RequestParam(name="id") Long id, @RequestParam(name="exchangeCount") int exchangeCount) 
	{
		try {
			EmployeeResource employeeResource = employeeResourceService.findOne(id);
			int tempCount = employeeResource.getCount() - exchangeCount;
			if(tempCount>0) {
				if(employeeResource!=null) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					String time= sdf.format( new Date());
					Date date=sdf.parse(time);
					EmployeeResource newEmployeeResource = new EmployeeResource();
					employeeResource.setCount(tempCount);
					employeeResource.setRecentChangeTime(date);
					employeeResourceService.save(employeeResource);
					
					newEmployeeResource.setResourceName(employeeResource.getResourceName());
					newEmployeeResource.setStatus(2);
					newEmployeeResource.setEmployee(employeeResource.getEmployee());
					newEmployeeResource.setCount(exchangeCount);
					newEmployeeResource.setRecentChangeTime(date);
					employeeResourceService.save(newEmployeeResource);
				}
				return new ExtAjaxResponse(true,"兑换资源成功！");
			}else {
				return new ExtAjaxResponse(true,"您没有那么多资源！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"兑换资源失败！");
		}
	}
	
	//兑奖完成，将状态值设为3，表示已兑奖状态
	@PostMapping("/finishExchange")
	public ExtAjaxResponse finishExchange(@RequestParam(name="id") Long id) 
	{
		try {
			EmployeeResource entity = employeeResourceService.findOne(id);
			if(entity!=null) {
				entity.setStatus(3);
				employeeResourceService.save(entity);
			}
			return new ExtAjaxResponse(true,"兑奖完成！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"兑奖失败！");
		}
	}
}
