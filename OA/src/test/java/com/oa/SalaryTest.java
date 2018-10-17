package com.oa;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oa.common.okhttp.OkTool;
import com.oa.employee.entity.Employee;
import com.oa.salary.entity.Salary;
import com.oa.salary.service.ISalaryService;
import com.oa.worktime.controller.WorkTimeController;
import com.oa.worktime.entity.WorkTimeDTO;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SalaryTest {
	
	@Autowired
	ISalaryService salaryService;
	
	@Test
	public void addDatas() {
		Random random=new Random(System.currentTimeMillis());
		int temp=0;
		String []userIds= {"1","2","3","4","5","6","7","8","9","admin","salarypay","user1","user2","user3","user4","user5","user6","user7","user8","user9"};
		for (String string : userIds) {
			Employee employee=new Employee();
			employee.setId(string);
			Salary salary=new Salary();
			salary.setEmployee(employee);
			temp=random.nextInt(2000);
			salary.setBonus((double)temp);
			temp=random.nextInt(15000)+3000;
			salary.setSal((double)temp);
			temp=random.nextInt(50)+10;
			salary.setSubsidy((double)temp);
			salary.setWorkMonth(random.nextInt(30));
			temp=random.nextInt(20)+10;
			salary.setWorktimeMoney((double)temp);
			salaryService.save(salary);
		}
		
	}
	
	@Autowired
	OkTool ot;
	
	@Test
	public void testWork() throws Exception {
		WorkTimeController workTimeController=new WorkTimeController();
		MockMvc mockMvc=MockMvcBuilders.standaloneSetup(workTimeController).build();
		String []userIds= {"1","2","3","4","5","6","7","8","9","admin","salarypay","user1","user2","user3","user4","user5","user6","user7","user8","user9"};
		Random random=new Random(System.currentTimeMillis());
		for (String string : userIds) {
			//try {
				//String wts= mockMvc.perform(post("/workTime/savemore").accept("application/json").param("employeeid", string).param("StartDate", "2018/07/01").param("EndDate", "2018/09/30").param("hour", "8")).andDo(print()).andReturn().getResponse().getContentAsString();
//				String wts= mockMvc.perform(get("/workTime/savemore?employeeid=1&StartDate=2018/07/1&EndDate=2018/09/30&hour=2").accept("application/json").param("", "").andDo(print()).andReturn().getResponse().getContentAsString();
				//String wts= mockMvc.perform(get("/savemore?employeeid=1&StartDate=2018/07/1&EndDate=2018/09/30&hour=2").accept("application/json")).andDo(print()).andReturn().getResponse().getContentAsString();;
				/*ResultActions ra= mockMvc.perform(post("/workTime/savemore").param("employeeid", string).param("StartDate", "2018/09/01").param("EndDate", "2018/09/30").param("hour", "8"));
				ra.andReturn().getAsyncResult();*/
				String wts =ot.url("http://172.27.9.40:8080/workTime/savemore").addFormData("employeeid", string).addFormData("StartDate", "2018/01/01").addFormData("EndDate", "2018/02/28").addFormData("hour", "8").get();
				//System.out.println(wts);
				MediaType mt=MediaType.parse("application/json; charset=utf-8");
				//创建以json方式提交的body
				//ObjectMapper ob=new ObjectMapper();
				List<WorkTimeDTO>dtos=JSON.parseArray(wts, WorkTimeDTO.class);
				for (WorkTimeDTO dto : dtos) {
					if (dto.getIfholiday()==0) {
						dto.setHour(6+random.nextInt(4));
					}
				}
				
				String str=ot.url("http://172.27.9.40:8080/workTime/forApproval").json(JSON.toJSONString(dtos));
				System.out.println(str);
				/*RequestBody body=RequestBody.create(mt,wts);
				Request.Builder rb =new Request.Builder();
				OkHttpClient client=new OkHttpClient();
				Response  response=client.newCall(rb.url("http://172.27.9.40:8080/workTime/forApproval").post(body).build()).execute();
				System.out.println(response.body().string());*/
				//TimeUnit.SECONDS.sleep(2);
			/*} catch (Exception e) {
				e.printStackTrace();
			}*/
		}
	}
	
}
