package com.oa.employee.service;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.JSONToken;
import com.oa.common.beans.BeanUtils;
import com.oa.employee.entity.Employee;
import com.oa.employee.entity.EmployeeDTO;
import com.oa.employee.repository.EmployeeRepository;



@Service
@Transactional
public class EmployeeService implements IEmployeeService{
	
	//调用Repository方法
	@Autowired
	private EmployeeRepository employeeRepository;
	
	//增加
	@Override
	public Employee save(Employee entity) {
		return employeeRepository.save(entity);
	};
	
	//根据ID查询
	@Override
	public Optional<Employee> findById(String id){
		return employeeRepository.findById(id);
		
	};
	
	//根据ID判断是否存在
	@Override
	public boolean existsById(String id) {
		return employeeRepository.existsById(id);
		
	};
	
	//统计
	@Override
	public long count() {
		return employeeRepository.count();
		
	};
	
	//根据ID删除
	@Override
	public void deleteById(String id) {
		employeeRepository.deleteById(id);
	};
	
	//批量删除
	@Override
	public void deleteAll(String[] ids) {
		List<String> idLists = new ArrayList<String>(Arrays.asList(ids));
		List<Employee> employees = (List<Employee>) employeeRepository.findAllById(idLists);
		if(employees!=null) {
			employeeRepository.deleteAll(employees);
		}
	};
	
	//查询
	public Page<Employee> findAll(Specification<Employee> spec, Pageable pageable){
		return employeeRepository.findAll(spec,pageable);
	}
	
	public EmployeeDTO entityToDto(Employee employee) {
		EmployeeDTO employeeDTO=new EmployeeDTO();
		BeanUtils.copyProperties(employee, employeeDTO);
		if (employee.getDepartment()!=null) {
			employeeDTO.setDepartmentName(employee.getDepartment().getName());
			employeeDTO.setDepartmentid(employee.getDepartment().getId());
		}
		if (employee.getLeader()!=null) {
			employeeDTO.setLeaderid(employee.getLeader().getId());
			employeeDTO.setLeaderName(employee.getLeader().getName());
		}
		//employeeDTO.setDepartmentName(employee.getDepartment().getName());
		return employeeDTO;
	}
	
	@Override
	public Page<EmployeeDTO> findAllInDto(Specification<Employee> spec, Pageable pageable) {
		Page<Employee> page=findAll(spec, pageable);
		List<Employee> employees= page.getContent();
		List<EmployeeDTO> employeeDTOs=new ArrayList<>();
		for (Employee employee : employees) {
			employeeDTOs.add(entityToDto(employee));
		}
		return new PageImpl<>(employeeDTOs, pageable, page.getTotalElements());
	}

	public void deleteAllemployee() {
		// TODO Auto-generated method stub
		
	}

	public void setEntryTime(Date date) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<EmployeeDTO> findRemberShip(String id) {
		Employee root=employeeRepository.findById(id).orElse(null);
		if (root==null) {
			return null;
		}
		
		List<Employee> employees=new ArrayList<>();
		List<EmployeeDTO> results=new ArrayList<>();
		
		employees.add(root);
		
		while (!employees.isEmpty()) {
			Employee e=employees.get(0);
			employees.addAll(employeeRepository.findByLeader(e.getId()));
			results.add(EmployeeDTO.entityToDto(e));
			employees.remove(e);
		}
		results.get(0).setLeaderid("");
		
		return results;
	}

	@Override
	public String findRemberShipToJson(String id) {
		
		Employee root=employeeRepository.findById(id).orElse(null);
		if (root==null) {
			return null;
		}
		JSONObject result=new JSONObject();
		JSONObject rootObject=(JSONObject) JSONObject.toJSON(EmployeeDTO.entityToDto(root));
		result.put("root", rootObject);
		List<Employee> employees=new ArrayList<>();
		List<JSONObject> jsonObjects=new ArrayList<>();
		employees.add(root);
//		jsonObjects.add(JSON.parseObject(JSON.toJSONString(root)));
		jsonObjects.add(rootObject);
		while (!employees.isEmpty()) {
			Employee e=employees.get(0);
			JSONObject jroot=null;
			for (JSONObject jsonObject : jsonObjects) {
				if (jsonObject.getString("id").equals(e.getId())) {
					jroot=jsonObject;
					break;
				}
			}
			List<Employee> childrens=employeeRepository.findByLeader(e.getId());
			JSONArray jchil=new JSONArray();
			for (Employee employee : childrens) {
				JSONObject jo=JSON.parseObject(JSON.toJSONString(EmployeeDTO.entityToDto(employee)));
				jchil.add(jo);
				jsonObjects.add(jo);
			}
			employees.addAll(childrens);
			jroot.put("children", jchil);
			employees.remove(e);
		}
//		while (!results.isEmpty()) {
//			String leaderid=results.get(0).getLeaderid();
//			results.remove(0);
//			int count=0;
//			JSONArray tempchild=new JSONArray();
//			while (results.get(count++).getLeaderid().equals(leaderid)) {
//				tempchild.add
//			}
//		}
		System.out.println(result.toJSONString());
		return result.toJSONString();
	}
	
	
//	//深度优先搜索
//	private void name(String id) {
//		List<Employee> employees= employeeRepository.findByLeader(id);
//		for (Employee employee : employees) {
//			List<Employee> temp=employeeRepository.findByLeader(employee.getId());
//			if (condition) {
//				
//			}
//		}
//	}
	
}
