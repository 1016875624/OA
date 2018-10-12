package com.oa;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dom4j.IllegalAddException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.common.holiday.HolidayQuery;
//import com.oa.common.holiday.HolidayQuery;
//import com.oa.common.okhttp.OkTool;
import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;
import com.oa.department.service.DepartmentService;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.service.IHolidayTimeService;
import com.oa.worktime.service.IWorkTimeService;
import com.oa.worktime.service.WorkTimeService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class OaApplicationTests {
	@Autowired
	EmployeeService employeeService;
	@Autowired
	IDepartmentService departmentService;
	
	@Autowired
	HolidayQuery holidayQuery;
	@Autowired
	IHolidayTimeService holidayTimeService;
	@Autowired
	IWorkTimeService workTimeService;
	@Test
	public void test() {
		for (int i = 1; i < 10; i++) {
			Employee employee = new Employee();
			employee.setId(i+"");
			employee.setName("a"+i);
			employee.setPassword("111");
			employee.setEmail(i+"@qq.com");
			Department department=new Department();
			department.setId("1");
			employee.setPosition("测试员"+i);
			employee.setStatus(i%2);
			employee.setEntryTime(new Date());
			//employee.setLeader("1");
			employeeService.save(employee);
		}
	}
	@Test
	public void testholidayQu() throws IOException {
		//System.out.println(holidayQuery.queryByApi("20180922"));
		//System.out.println(holidayQuery.queryByApi("20180922"));
		Map<String, String> map=holidayQuery.queryByApi("20181001");
		String value=map.get("20180922");
		System.out.println("saaaa:"+value);
		System.out.println(holidayQuery.queryByApi("20181001"));
		//String []strings= {"20180920","20180921","20180922","20180923"};
		//System.out.println(holidayQuery.queryByApi(strings));
	}
	@Test
	public void aaa() {
		String start = "2018-08-08";
        String end = "2018-08-08";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dStart = null;
        Date dEnd = null;
        try {
            dStart = sdf.parse(start);
            dEnd = sdf.parse(end);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Date> dateList = findDates(dStart, dEnd);
        for (Date date : dateList) {
            System.out.println(sdf.format(date));
        }
	}
	public static List<Date> findDates(Date dStart, Date dEnd) {
        Calendar cStart = Calendar.getInstance();
        cStart.setTime(dStart);

        List dateList = new ArrayList();
        //别忘了，把起始日期加上
        dateList.add(dStart);
        // 此日期是否在指定日期之后
        while (dEnd.after(cStart.getTime())) {
        // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            cStart.add(Calendar.DAY_OF_MONTH, 1);
            dateList.add(cStart.getTime());
        }
    return dateList;
    }

	@Test
	public void testHoliday() throws IOException, ParseException {
		
		List<HolidayTime> holidayTimes=holidayTimeService.checkStringHoliday("2018/10/01","2018/10/29");
		System.out.println(holidayTimes);
		
	}
	@Test
	public void testHo() {
		try {
			Map<String, String> maps=holidayTimeService.checkMapHoiday("2018/10/01", "2018/10/29");
			System.out.println(maps);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	@Test
	public void testMonth() {
		
		try {
			Map<String, String> maps = holidayTimeService.checkMapMonth("2018/02");
			System.out.println(maps);
			System.out.println(maps.size());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Test
	public void testWo() {
		try {
			double a=workTimeService.attendance("user2", "2018/10");
			System.out.println("the attendence is: "+a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test 
	public void testDmo(){
		try {
			int a=workTimeService.workOvertime("2", "2018/10");
			System.out.println("the workOverTime is :"+a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test 
	public void testovetime(){
		try {
			int a=workTimeService.workOvertime("2", "2018/10/01","2018/10/31");
			System.out.println("the workOverTime time is :"+a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Test
	public void testAttence() {
		try {
			double a=workTimeService.attendance("2", "2018/10/01","2018/10/31");
			System.out.println("the worktime time is :"+a);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
