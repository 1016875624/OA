package com.example.demo;

import static org.hamcrest.CoreMatchers.both;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeUtility;

import org.junit.Test;

import com.sun.mail.util.MimeUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class Test2 {
	@Test
	public  void receive() throws InterruptedException{
		
		//Scanner sc=new Scanner(System.in);
		int n=3;
		List<Integer> a1=new ArrayList<>();
		List<Integer> a2=new ArrayList<>();
		int m=n;
		for (int i = 0; i < n; i++) {
			a1.add((i+1));
		}
		System.out.println(a1);
		
		a2.add(n);
		System.out.println(a2);
		while (true) {
			int a01=LCM(a1);
			int a02=LCM(a2);
			if(a01==a02) {
				System.out.println(m);
				break;
			}
			m++;
			a1.add(m);
			a2.add(m);
			System.out.println("m="+m);
			System.out.println("a01="+a01);
			System.out.println("a02="+a02);
			System.out.println("---------------------------------------");
			Thread.sleep(5000);
		}
	}
	
	public int LCM(List<Integer>a) {
		if (a.size()==1) {
			return a.get(0);
		}
		int max=a.get(a.size()-1);
		int result=max;
		boolean bool=false;
		for (Integer integer : a) {
			bool=false;
			result=integer*max;
			for (Integer b : a) {
				System.out.println("result%="+result%b);
				if (result%b!=0) {
					bool=true;
					break;
				}
			}
			if (bool) {
				continue;
			}
			return result;
		}
		for (int i = max+1;  ; i++) {
			result=max*i;
			for (Integer b : a) {
				if (result%b!=0) {
					bool=true;
					break;
				}
			}
			if (bool) {
				continue;
			}
			return result;
		}
		
	}
}
