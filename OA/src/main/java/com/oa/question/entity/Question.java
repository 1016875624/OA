package com.oa.question.entity;

import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;
@Entity
@Table(name="question")
@Data
public class Question {
	//这里的设计格式为_为 填空的空
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	/**
	* @Fields question :题目 题目中可能有_作为空
	*	 这里的设计格式为_为 填空的空
	*/

	private String question;
	//这里的答案的格式为|这个为多个答案&为多选题中的答案,少选不得分,$为第二个空的答案
	//a&b&C|b&c&d$aaads
	/**
	* @Fields realanswer : 标准答案
	* 	这里的答案的格式为|这个为多个答案,&为多选题中的答案,少选不得分,$为第二个空的答案
	* a&b&C|b&c&d$aaads
	*/
	private String realanswer;
	//以#分割 不同的选项
	/**
	* @Fields answers : 用于选择题的选项
	* 	以#分割 不同的选项
	*/
	private String answers;
	
	/**
	* @Fields type : 题目类型
	*/
	private Integer type;
	
	
	/**
	 * @Fields status : 题目是否删除状态
	 */
	private Integer status;
	/**
	* <p>方法名称: isRight</p>
	* <p>描述：判断答案是否正确</p>
	* @param ans 这里是一个任意长度的数组,这里规定每一个数组元素为一个空的答案
	* @return boolean 返回类型
	*/
	public boolean isRight(String... ans) {
		String [] realans=realanswer.split("\\$");
		if (ans.length!=realans.length) {
			return false;
		}
		boolean flag=false;
		for (int i = 0; i < realans.length; i++) {
			String optional[]=realans[i].split("\\|");
			flag=false;
			//进行对比一个空是否正确
			for (int j = 0; j < optional.length; j++) {
				String a1=ans[i];
				String ra1=optional[j];
				//只要一个可选的正确了，那么此题正确
				if (compareAnswer(a1, ra1)) {
					flag=true;
					break;
				}
			}
			//如果没有答对此空，那么此题将会判断为错误
			if (!flag) {
				return false;
			}
		}
		return true;
	}
	
	public boolean compareAnswer(String a1,String a2) {
		//进行获取每个必要的答案
		String aa1[]=a1.split("\\&");
		String aa2[]=a2.split("\\&");
		if (aa1.length!=aa2.length) {
			return false;
		}
		//解决排序问题，避免因为考生填的顺序不同而判定为错误
		Arrays.sort(aa1,(s1,s2)->{
			return s1.compareToIgnoreCase(s2);
		});
		Arrays.sort(aa2,(s1,s2)->{
			return s1.compareToIgnoreCase(a2);
		});
		
		//验证答案
		for (int i = 0; i < aa2.length; i++) {
			if (!aa1[i].trim().equalsIgnoreCase(aa2[i].trim())) {
				return false;
			}
		}
		return true;
	}
}
