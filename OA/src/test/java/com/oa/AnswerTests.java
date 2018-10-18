package com.oa;

import org.junit.Test;

import com.oa.question.entity.Question;

public class AnswerTests {
	@Test
	public void testAnswer() {
		Question question=new Question();
//		question.setRealanswer("键盘|手机&1$鼠标|2&2$水杯|3");
		question.setRealanswer("封装了jdbc，简化了很多重复性代码&简化了DAO层编码工作，使开发更对象化&移植性好，支持各种数据库，如果换个数据库只要在配置文件中变换配置就可以了，不用改变hibernate代码&支持透明持久化，因为hibernate操作是纯粹的java类，没有实现任何接口，没有侵入性，是一个低量级框架"  
				);
		
		
		//question.setRealanswer("a");
		
		if (question.isRight("封装了jdbc，简化了很多重复性代码&简化了DAO层编码工作，使开发更对象化&移植性好，支持各种数据库，如果换个数据库只要在配置文件中变换配置就可以了，不用改变hibernate代码&支持透明持久化，因为hibernate操作是纯粹的java类，没有实现任何接口，没有侵入性，是一个低量级框架")) {
			System.out.println("你答对了");
		}
		else {
			System.out.println("你答错了");
		}
	}

}
