package com.oa.employee.controller;

import java.util.List;

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
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.entity.EmployeeQueryDTO;
import com.oa.employee.service.IEmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	@Autowired
	private IEmployeeService employeeService;

	@Autowired
	private IDepartmentService departmentService;

	// 添加
	/**
	 * 添加
	 * 
	 * @param employeeDTO
	 * @return
	 */
	@PostMapping
	public String save(@RequestBody EmployeeDTO employeeDTO) {
		System.out.println(employeeDTO);
		Employee employee = new Employee();
		// 把employeeDTO中的字段拷到employee中
		BeanUtils.copyProperties(employeeDTO, employee);
		// 对两个类中不同的字段进行操作
		Employee leader = null;
		Department department = new Department();
		if (employeeDTO.getLeaderid() != null) {
			leader = employeeService.findById(employeeDTO.getLeaderid()).orElse(null);
		}
		if (employeeDTO.getDepartmentid() != null) {
			department = departmentService.findById(employeeDTO.getDepartmentid());
		}
		// 重写set方法
		employee.setStatus(0);
		employee.setLeader(leader);
		employee.setDepartment(department);
		System.out.println(employee);
		try {
			// Employee entity = employeeService.findById(id).get();
			employeeService.save(employee);
			System.out.println("添加员工成功!");
			return "添加成功";
		} catch (Exception e) {
			e.printStackTrace();
			return "添加失败";
		}
	}

	// 查找分页显示
	/**
	 * 查找分页显示
	 * 
	 * @param employeeQueryDTO
	 * @param extjsPageRequest
	 * @return
	 */
	@GetMapping
	public Page<EmployeeDTO> getPage(EmployeeQueryDTO employeeQueryDTO, ExtjsPageRequest extjsPageRequest) {
		return employeeService.findAllInDto(EmployeeQueryDTO.getWhereClause(employeeQueryDTO),
				extjsPageRequest.getPageable());
	}

	@GetMapping("/{empid}")
	public String getOne(@PathVariable("empid") String empid, EmployeeQueryDTO employeeQueryDTO,
			ExtjsPageRequest extjsPageRequest) {

		return employeeService.findRemberShipToJson(empid);
	}
//	@GetMapping("/{empid}")
//	public List<EmployeeDTO> getOne(@PathVariable("empid")String empid,EmployeeQueryDTO employeeQueryDTO,ExtjsPageRequest extjsPageRequest){
//		return employeeService.findRemberShip(empid);
//	}

	/**
	 * 交换部门人员时加载
	 * 
	 * @param employeeDTOs
	 * @return
	 */
	@RequestMapping(value = "/changeDepartment")
	public ExtAjaxResponse changeDepartment(@RequestBody EmployeeDTO[] employeeDTOs) {
		try {
			for (EmployeeDTO employeeDTO : employeeDTOs) {
				Employee employee = employeeDTO.DtoToentity(employeeDTO);
				employeeService.save(employee);
			}
			return new ExtAjaxResponse(true, "交换成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(true, "交换失败!");
		}
	}

	// 根据id删除
	/**
	 * 删除一条
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping(value = "{id}")
	public ExtAjaxResponse delete(@PathVariable("id") String id) {
		try {
			if (id != null) {
				Employee employee = employeeService.findById(id).orElse(null);
				// 将状态置为-1而不是真正的删除
				employee.setStatus(-1);
				employeeService.save(employee);
			}
			return new ExtAjaxResponse(true, "删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(true, "删除失败！");
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */
	@PostMapping("/deletes")
	public ExtAjaxResponse deleteMoreRows(@RequestParam(name = "ids") String[] ids) {
		try {
			if (ids != null) {
				// 遍历数组把状态设为-1
				for (int i = 0; i < ids.length; i++) {
					Employee employee = employeeService.findById(ids[i]).orElse(null);
					employee.setStatus(-1);
					employeeService.save(employee);
				}
				// employeeService.deleteAll(ids);
			}
			return new ExtAjaxResponse(true, "删除多条成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "删除多条失败");
		}
	}

	/**
	 * 修改更新
	 * 
	 * @param id
	 * @param employeeDTO
	 * @return
	 */
	@PutMapping(value = "{id}")
	public @ResponseBody ExtAjaxResponse update(@PathVariable("id") String id, @RequestBody EmployeeDTO employeeDTO) {
		System.out.println(employeeDTO);
		try {
			Employee entity = employeeService.findById(id).orElse(null);
			if (entity != null) {
				// 把DTO的字段复制到实体中再进行保存
				BeanUtils.copyProperties(employeeDTO, entity);
				if (employeeDTO.getDepartmentid()!=null) {
					Department department = new Department();
					department.setId(employeeDTO.getDepartmentid());
					entity.setDepartment(department);
				}
				if (employeeDTO.getLeaderid()!=null) {
					Employee leader = new Employee();
					leader.setId(employeeDTO.getLeaderid());
					entity.setLeader(leader);
				}
				employeeService.save(entity);
				return new ExtAjaxResponse(true, "更新成功!");
			}
			employeeService.save(EmployeeDTO.DtoToentity(employeeDTO));
			return new ExtAjaxResponse(true, "更新成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return new ExtAjaxResponse(false, "更新失败!");
		}
	}
}
