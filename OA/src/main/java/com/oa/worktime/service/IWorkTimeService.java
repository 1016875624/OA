package com.oa.worktime.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;

import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;





public interface IWorkTimeService {
	public WorkTime save(WorkTime entity);


    public List<WorkTime> saveAll(List<WorkTime> entities);

	public WorkTime findById(Integer id);


	public boolean existsById(Integer id);

    public List<WorkTime> findAll();

    public List<WorkTime> findAllById(List<Integer> ids);


	public long count();

	public void deleteById(Integer id);


	public void delete(WorkTime entity);


	public void deleteAll(List<WorkTime> entities);
	
	void deleteAll(Integer[]ids);
	
	public void deleteAll();
	public Page<WorkTime> findAll(Pageable pageable);
	
	public List<WorkTime> findAll(Sort sort);
	
	
	Optional<WorkTime> findOne(@Nullable Specification<WorkTime> spec);

	List<WorkTime> findAll(@Nullable Specification<WorkTime> spec);
	Page<WorkTime> findAll(@Nullable Specification<WorkTime> spec, Pageable pageable);
	List<WorkTime> findAllById(Integer ids[]);
	List<WorkTime> findAll(@Nullable Specification<WorkTime> spec, Sort sort);
	long count(@Nullable Specification<WorkTime> spec);
	
	Page<WorkTimeDTO> findAllInDto(@Nullable Specification<WorkTime> spec, Pageable pageable);
	
	void deleteAllById(Integer[]ids);
	
	List<WorkTime> findWorkTimes(String id);
	
	public WorkTime findWorkTime(Date date); 
	
	public WorkTime checkIfWorkTime(String employeeid,Date date);
	
	public List<WorkTime> attendence(String em1,Integer if1,Date d1,Date d2);
	
	
	//计算出勤率
	/**
	 * 计算某个员工一个月的出勤率
	 * @param em1 ：员工ID
	 * @param monthTime :yyyy/MM 月份 
	 * @return 
	 * @throws ParseException
	 * @throws IOException
	 */
	public double attendance(String em1,String monthTime) throws ParseException, IOException;
	
	/**
	 * 计算某个员工一个月的出勤率
	 * @param em1 ：员工ID
	 * @param d1 ：yyyy/MM 月份
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public double attendance(String em1,Date d1) throws ParseException, IOException;
	
	/**
	 * 计算某个员工一个月的出勤率
	 * @param em1 :员工ID
	 * @param d1 :开始时间
	 * @param d2  :结束时间 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public double attendance(String em1,Date d1,Date d2) throws ParseException, IOException;
	
	/**
	 * 计算某个员工一个月的出勤率
	 * @param em1 :员工ID
	 * @param d1 ：开始时间
	 * @param d2 ::结束时间 
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public double attendance(String em1,String d1,String d2) throws ParseException, IOException;
	
	//计算加班时间
	/**
	 * 计算某个员工一个月的加班时间
	 * @param em1 ：员工ID
	 * @param monthTime : yyyy/MM 月份 
	 * @return
	 * @throws ParseException
	 */
	public Integer workOvertime(String em1,String monthTime) throws ParseException;
	
	/**
	 * 计算某个员工一个月的加班时间
	 * @param em1 员工ID
	 * @param d1 ：yyyy/MM 月份 
	 * @return
	 * @throws ParseException
	 */
	public Integer workOvertime(String em1,Date d1) throws ParseException;
	
	/**
	 * 计算某个员工一个月的加班时间
	 * @param em1 ：员工ID
	 * @param d1 ：开始时间
	 * @param d2 ：结束时间
	 * @return
	 * @throws ParseException
	 */
	public Integer workOvertime(String em1,Date d1,Date d2) throws ParseException;
	
	/**
	 * 计算某个员工一个月的加班时间
	 * @param em1 ：员工ID
	 * @param d1 :开始时间
	 * @param d2 ：结束时间
	 * @return
	 * @throws ParseException
	 */
	public Integer workOvertime(String em1,String d1,String d2) throws ParseException;
	
	public List<WorkTimeDTO> savemore(WorkTimeDTO workTimeDTO)throws IOException ;
}

