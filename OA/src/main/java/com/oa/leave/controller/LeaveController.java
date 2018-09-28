package com.oa.leave.controller;

import java.util.ArrayList;
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
import com.oa.leave.entity.Leave;
import com.oa.leave.entity.LeaveDTO;
import com.oa.leave.entity.LeaveQueryDTO;
import com.oa.leave.service.ILeaveService;

@RestController
@RequestMapping(value="/leave")
public class LeaveController {
	@Autowired
	private ILeaveService leaveService;
	
	@Autowired
	private IEmployeeService employeeService;
	
	//添加保存，状态设置为0,表示该表为待申请状态
	@PostMapping
    public ExtAjaxResponse save(HttpSession session,@RequestBody Leave leave) {
    	try {
    		String applicantId = SessionUtil.getUserName(session);
    		Optional<Employee> employee = employeeService.findById(applicantId);
    		if(applicantId!=null) {
    			leave.setEmployee(employee.orElse(null));
    			leave.setStatus(0);
        		leaveService.save(leave);
    		}
    		return new ExtAjaxResponse(true,"保存成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"保存失败!");
	    }
    }
	
	//修改，状态设置为0,表示该表为待申请状态
	@PutMapping("update")
    public @ResponseBody ExtAjaxResponse update(@RequestParam(name="id") Long id,@RequestBody Leave leave) {
    	try {
    		Leave entity = leaveService.findOne(id);
    		leave.setStatus(0);
			if(entity!=null) {
				BeanUtils.copyProperties(leave, entity);//使用自定义的BeanUtils
				leaveService.save(entity);
			}
    		return new ExtAjaxResponse(true,"更新成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"更新失败!");
	    }
    }
	
	//发出申请,状态设置为1,表示该表为待审核状态
	@PostMapping("/application")
	public ExtAjaxResponse application(@RequestParam(name="id") Long id) {
		try {
			Leave leave = leaveService.findOne(id);
			leave.setStatus(1);
			leaveService.save(leave);
			leaveService.sendMail(leave);
			return new ExtAjaxResponse(true,"发出申请成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"发出申请失败！");
		}
	}
	
	//审核,状态设置为2,表示该表为已审核状态
	@GetMapping("/approval")
	public ExtAjaxResponse approval(@RequestParam(name="id") Long id) {
		try {
			Leave leave = leaveService.findOne(id);
			leave.setStatus(2);
			leaveService.save(leave);
			return new ExtAjaxResponse(true,"审核成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"审核失败！");
		}
	}
	
	//驳回,将状态变回0,表示该表为待申请状态
	@PutMapping("/reject")
	public ExtAjaxResponse reject(@RequestParam(name="id") Long id, @RequestBody Leave leave) {
		try {
    		Leave entity = leaveService.findOne(id);
    		leave.setStatus(0);
			if(entity!=null) {
				BeanUtils.copyProperties(leave, entity);//使用自定义的BeanUtils
				leaveService.save(entity);
			}
    		return new ExtAjaxResponse(true,"驳回成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"驳回失败!");
	    }
	}

	
	//销假,将状态变回3,表示该表为已销假状态
	@PutMapping("/endleave")
    public @ResponseBody ExtAjaxResponse endleave(@RequestParam(name="id") Long id,@RequestBody Leave leave) {
    	try {
    		Leave entity = leaveService.findOne(id);
    		leave.setStatus(3);
			if(entity!=null) {
				BeanUtils.copyProperties(leave, entity);//使用自定义的BeanUtils
				leaveService.save(entity);
			}
    		return new ExtAjaxResponse(true,"销假成功!");
	    } catch (Exception e) {
	    	e.printStackTrace();
	        return new ExtAjaxResponse(false,"销假失败!");
	    }
    }
	
	//删除
	@DeleteMapping(value="{id}")
    public @ResponseBody ExtAjaxResponse delete(@PathVariable("id") Long id) {
    	try {
    		Leave entity = leaveService.findOne(id);
			if(entity!=null) {
				leaveService.delete(id);
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
				leaveService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true,"批量删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false,"批量删除失败！");
		}
	}
	
	//根据当前登录用户的id来分页查询
	@GetMapping
    public Page<LeaveDTO> findLeaveByApplicantId(LeaveQueryDTO leaveQueryDTO,HttpSession session,ExtjsPageRequest pageable) 
	{
		Page<LeaveDTO> page;
		String applicantId = SessionUtil.getUserName(session);
		if(applicantId!=null) {
			leaveQueryDTO.setEmployeeId(applicantId);
			page = leaveService.findAllInDto(LeaveQueryDTO.getWhereClause(leaveQueryDTO), pageable.getPageable());
		}else {
			page = new PageImpl<LeaveDTO>(new ArrayList<LeaveDTO>(),pageable.getPageable(),0);
		}
		return page;
    }
	
	//根据上级ID为当前用户的来对审批表分页查询
	@GetMapping("/approvalTable")
    public Page<LeaveDTO> findLeaveByLeaderId(LeaveQueryDTO leaveQueryDTO,HttpSession session,ExtjsPageRequest pageable) 
	{
		Page<LeaveDTO> page;
		//获得当前用户ID
		String applicantId = SessionUtil.getUserName(session);
		if(applicantId!=null) {
			leaveQueryDTO.setLeaderId(applicantId);
			leaveQueryDTO.setStatus(1);
			page = leaveService.findAllInDto(LeaveQueryDTO.getWhereClause(leaveQueryDTO), pageable.getPageable());
		}else {
			page = new PageImpl<LeaveDTO>(new ArrayList<LeaveDTO>(),pageable.getPageable(),0);
		}
		return page;
    }
	
}
