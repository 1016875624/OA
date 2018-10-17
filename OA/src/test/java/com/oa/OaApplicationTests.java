package com.oa;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.dom4j.IllegalAddException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oa.common.holiday.HolidayQuery;
import com.oa.common.okhttp.OkTool;
//import com.oa.common.holiday.HolidayQuery;
//import com.oa.common.okhttp.OkTool;
import com.oa.department.entity.Department;
import com.oa.department.repository.DepartmentRepository;
import com.oa.department.service.DepartmentService;
import com.oa.department.service.IDepartmentService;
import com.oa.employee.entity.Employee;
import com.oa.employee.service.EmployeeService;
import com.oa.question.entity.Question;
import com.oa.question.repository.QuestionRepository;
import com.oa.question.service.IQuestionService;
import com.oa.worktime.controller.WorkTimeController;
import com.oa.worktime.entity.HolidayTime;
import com.oa.worktime.entity.WorkTime;
import com.oa.worktime.entity.WorkTimeDTO;
import com.oa.worktime.service.IHolidayTimeService;
import com.oa.worktime.service.IWorkTimeService;
import com.oa.worktime.service.WorkTimeService;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


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
	@Autowired
	QuestionRepository questionRepository;
	@Autowired
	IQuestionService questionService;
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
	
	@Test
	public void testDay() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM");
			Date date=sdf.parse("2018/10");
			int days=workTimeService.workDay("user2", date);
			System.out.println("the worktime day is :"+days);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testQues() throws NoSuchAlgorithmException {
		//List<Integer> ids=questionRepository.findAllId();
		
		long count=questionRepository.count();
		int danxuan=0,duoxuan=0,tiankong=0;
		int max=(int)count;
		//System.out.println("所有ID为："+ids);
		//Random rand = new Random();
		//long x=ids.get();
		
		Set<Integer> lists=new HashSet<>();
		while(lists.size()<10) {
			int number=SecureRandom.getInstanceStrong().nextInt(max);
			Question textquestion=questionRepository.findById(number).orElse(null);
			if(textquestion!=null) {
				if(textquestion.getType()==0) {
					if(danxuan<3) {
						lists.add(textquestion.getId());
						danxuan++;
					}else {
						continue;
					}
				}else if(textquestion.getType()==1) {
					if(duoxuan<3) {
						lists.add(textquestion.getId());
						duoxuan++;
					}else {
						continue;
					}
				}else if(textquestion.getType()==2) {
					if(tiankong<4) {
						lists.add(textquestion.getId());
						tiankong++;
					}else {
						lists.add(textquestion.getId());
						tiankong++;
						continue;
					}
				}
			}
		}
		System.out.println(lists);
		
	}
	@Test
	public void getques() {
		int singleNum=0,doubleNum=0,fillsNum=0;
		List<Integer> singlesId=questionRepository.findAllId(0);
		List<Integer> doublesId=questionRepository.findAllId(1);
		List<Integer> fillsId=questionRepository.findAllId(2);
		System.out.println(singlesId);
		System.out.println(doublesId);
		System.out.println(fillsId);
	}
	@Test
	public void testPaper() throws NoSuchAlgorithmException {
		List<Integer> lists=new ArrayList<>();
		int singleNum=0,doubleNum=0,fillsNum=0;
		List<Integer> singlesId=questionRepository.findAllId(0);
		List<Integer> doublesId=questionRepository.findAllId(1);
		List<Integer> fillsId=questionRepository.findAllId(2);
		List<Integer> ids=new ArrayList<Integer>();//有序的id集合
		
		while(lists.size()<10) {
			if(singleNum<3) {
				int number=SecureRandom.getInstanceStrong().nextInt(singlesId.size());
				if(!lists.contains(singlesId.get(number))) {
					lists.add(singlesId.get(number));
					singleNum++;
				}
				
			}
			if(doubleNum<3) {
				int number=SecureRandom.getInstanceStrong().nextInt(doublesId.size());
				if(!lists.contains(doublesId.get(number))) {
					lists.add(doublesId.get(number));
					doubleNum++;
				}
				
			}
			if(fillsNum<4) {
				int number=SecureRandom.getInstanceStrong().nextInt(fillsId.size());
				if(!lists.contains(fillsId.get(number))) {
					lists.add(fillsId.get(number));
					fillsNum++;
				}
				
			}
				
		}
//		while(!lists.isEmpty()) {
//			for (Integer questionid : lists) {
//				//Question question=new Question();
//				//question=questionRepository.findById(questionid).orElse(null);
//				ids.add(questionid);
//				lists.remove(questionid);
//			}
//		}
		System.out.println("产生的随机id为： "+lists);
		for(int i=0;i<lists.size();i++) {
			System.out.println(lists.get(i));
		}
	}
	
	@Test
	public void testgetpaper() {
		try {
			List<Question> q=questionService.randomTestpaper();
			System.out.println(q);
			for (Question question : q) {
				System.out.println(question);
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
//	@Test
//	public void testWorkTiME() throws ParseException, IOException {
//		Random random=new Random(System.currentTimeMillis());
//		int temp=0;
//		String []userIds= {"1","2","3","4","5","6","7","8","9","admin","salarypay","user1","user2","user3","user4","user5","user6","user7","user8","user9"};
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//		Date startTime=sdf.parse("2018/01");
//		Date endTime=sdf.parse("2018/10");
//		List<HolidayTime> holidayTimes=holidayTimeService.checkDateHoliday(startTime, endTime);
//		
//		for (String string : userIds) {
//			
//			for (HolidayTime holidayTime : holidayTimes) {
//				if(holidayTime.getIfholiday()==0) {
//					Employee employee=new Employee();
//					employee.setId(string);
//					
//					WorkTime workTime=new WorkTime();
//					workTime.setDate(holidayTime.getDate());
//					workTime.setEmployee(employee);
//					temp=random.nextInt(8)+2;
//					workTime.setHour(temp);
//					workTime.setIfholiday();
//				}else if()
//				
//			}
//				
//			
//			
//			
//		}
//	}
//	
	
	@Test
	public void testWork() {
		WorkTimeController workTimeController=new WorkTimeController();
		MockMvc mockMvc=MockMvcBuilders.standaloneSetup(workTimeController).build();
		String []userIds= {"1","2","3","4","5","6","7","8","9","admin","salarypay","user1","user2","user3","user4","user5","user6","user7","user8","user9"};
		for (String string : userIds) {
			try {
				List<WorkTimeDTO> wts= (List<WorkTimeDTO>) mockMvc.perform(post("/workTime/savemore").param("employeeid", string).param("StartDate", "2018/09/01").param("EndDate", "2018/09/30").param("hour", "8")).andReturn().getAsyncResult();
				/*ResultActions ra= mockMvc.perform(post("/workTime/savemore").param("employeeid", string).param("StartDate", "2018/09/01").param("EndDate", "2018/09/30").param("hour", "8"));
				ra.andReturn().getAsyncResult();*/
				
				MediaType mt=MediaType.parse("application/json; charset=utf-8");
				//创建以json方式提交的body
				ObjectMapper ob=new ObjectMapper();
				RequestBody body=RequestBody.create(mt, ob.writeValueAsString(wts));
				Request.Builder rb =new Request.Builder();
				OkHttpClient client=new OkHttpClient();
				Response  response=client.newCall(rb.url("http://localhost:8080/workTime/forApproval").post(body).build()).execute();
				System.out.println(response.body().string());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
