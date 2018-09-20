package com.oa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.oa.question.entity.Question;


public class Tests2 {
	@Test
	public void testAnswer() {
		Question question=new Question();
		question.setRealanswer("键盘|手机&1$鼠标|2&2$水杯|3");
		//question.setRealanswer("a");
		
		if (question.isRight("1&手机","2&2","3")) {
			System.out.println("你答对了");
		}
		else {
			System.out.println("你答错了");
		}
	}

}
