package com.oa.quit.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.NativeWebRequest;

import com.oa.common.beans.BeanUtils;
import com.oa.common.date.utils.DateUtils;
import com.oa.common.tool.mail.MailMsgSingle;
import com.oa.quit.entity.Quit;
import com.oa.quit.entity.QuitDTO;
import com.oa.quit.repository.QuitRepository;




@Service
@Transactional
public class QuitService implements IQuitService {
	@Autowired
	private QuitRepository quitRepository;
	
	@Autowired
	private MailMsgSingle msg;
	
	@Override
	public Quit save(Quit entity) {
		return quitRepository.save(entity);
	}

	@Override
	public List<Quit> saveAll(List<Quit> entities) {
		return quitRepository.saveAll(entities);
	}

	@Override
	public Quit findById(Integer id) {
		return quitRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Integer id) {
		return quitRepository.existsById(id);
	}

	@Override
	public List<Quit> findAll() {
		return quitRepository.findAll();
	}

	@Override
	public List<Quit> findAllById(List<Integer> ids) {
		return quitRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return quitRepository.count();
	}

	@Override
	public void deleteById(Integer id) {
		Quit quit=quitRepository.findById(id).get();
		//设置状态值为-1就是删除
		quit.setStatus(-1);
	}

	@Override
	public void delete(Quit entity) {
		entity.setStatus(-1);
		quitRepository.save(entity);
	}

	@Override
	public void deleteAll(List<Quit> entities) {
		for (Quit quit : entities) {
			delete(quit);
		}
	}

	@Override
	public void deleteAll(Integer[] ids) {
		for (Integer integer : ids) {
			deleteById(integer);
		}
	}

	@Override
	public void deleteAll() {
		List<Quit>quits=quitRepository.findAll();
		for (Quit quit : quits) {
			delete(quit);
		}
	}

	@Override
	public Page<Quit> findAll(Pageable pageable) {
		return quitRepository.findAll(pageable);
	}

	@Override
	public List<Quit> findAll(Sort sort) {
		return quitRepository.findAll(sort);
	}

	@Override
	public Optional<Quit> findOne(Specification<Quit> spec) {
		return quitRepository.findOne(spec);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec) {
		return quitRepository.findAll(spec);
	}

	@Override
	public Page<Quit> findAll(Specification<Quit> spec, Pageable pageable) {
		return quitRepository.findAll(spec, pageable);
	}

	@Override
	public List<Quit> findAllById(Integer[] ids) {
		List<Integer> idLists = new ArrayList<Integer>(Arrays.asList(ids));
		return quitRepository.findAllById(idLists);
	}

	@Override
	public List<Quit> findAll(Specification<Quit> spec, Sort sort) {
		return quitRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<Quit> spec) {
		return quitRepository.count(spec);
	}

	@Override
	public void deleteAllById(Integer[] ids) {
		deleteAll(ids);
	}

	@Override
	public Page<QuitDTO> findAllInDTO(Specification<Quit> spec, Pageable pageable) {
		Page<Quit>qPage=findAll(spec, pageable);
		List<Quit>quits=qPage.getContent();
		List<QuitDTO>quitDTOs=new ArrayList<>();
		for (Quit quit : quits) {
			quitDTOs.add(QuitDTO.entityToDTO(quit));
		}
		return new PageImpl<>(quitDTOs, pageable, qPage.getTotalElements());
	}

	@Override
	public void update(Quit quit) {
		Quit entity=quitRepository.findById(quit.getId()).get();
		/*if (quit.getApplyDate()!=null) {
			entity.setApplyDate(quit.getApplyDate());
		}
		if (quit.getEmployee()!=null) {
			entity.setEmployee(quit.getEmployee());
		}
		if (quit.getQuitDate()!=null) {
			entity.setQuitDate(quit.getQuitDate());
		}
		if (quit.getReason()!=null) {
			entity.setReason(quit.getReason());
		}
		if (quit.getStatus()!=null) {
			entity.setStatus(quit.getStatus());
		}*/
		BeanUtils.copyProperties(quit, entity);
		
	}

	@Override
	public void approvalPass(Integer[] ids) {
		Date date=DateUtils.toDate(LocalDateTime.now().plusDays(3));
		quitRepository.approvalPass(date, ids); 
	}

	@Override
	public void approvalNoPass(Integer[] ids) {
		quitRepository.approvalNoPass(ids);
	}

	@Override
	public void sendQuitMail(String toMail,String toName,String name,String id) throws MessagingException, IOException{
		StringBuffer sb=new StringBuffer();
		String html="<!DOCTYPE html>" + 
				"<html>" + 
				"    <head>" + 
				"        <meta charset=\"utf-8\"/>" + 
				"        <title>" + 
				"            test" + 
				"        </title>" + 
				"        <style media=\"screen\" type=\"text/css\">" + 
				"			.card {" + 
				"			    position: relative;" + 
				"			    display: -ms-flexbox;" + 
				"			    display: flex;" + 
				"			    -ms-flex-direction: column;" + 
				"			    flex-direction: column;" + 
				"			    min-width: 0;" + 
				"			    word-wrap: break-word;" + 
				"			    background-color: #fff;" + 
				"			    background-clip: border-box;" + 
				"			    border: 1px solid rgba(0,0,0,.125);" + 
				"			    border-radius: .25rem;" + 
				"			}" + 
				"			.card-img-top {" + 
				"			    width: 100%;" + 
				"			    border-top-left-radius: calc(.25rem - 1px);" + 
				"			    border-top-right-radius: calc(.25rem - 1px);" + 
				"			}" + 
				"			.card-body {" + 
				"			    -ms-flex: 1 1 auto;" + 
				"			    flex: 1 1 auto;" + 
				"			    padding: 1.25rem;" + 
				"			}" + 
				"			.h5,h5 {" + 
				"			    font-size: 1.25rem;" + 
				"			    margin-top: 0;" + 
				"		        margin-bottom: .5rem;" + 
				"			    font-family: inherit;" + 
				"			    font-weight: 500;" + 
				"			    line-height: 1.2;" + 
				"			    color: inherit;" + 
				"			}" + 
				"			p {" + 
				"				margin-top: 0;" + 
				"    			margin-bottom: 1rem;" + 
				"			    display: block;" + 
				"			    margin-block-start: 1em;" + 
				"			    margin-block-end: 1em;" + 
				"			    margin-inline-start: 0px;" + 
				"			    margin-inline-end: 0px;" + 
				"			}" + 
				"			.btn-success {" + 
				"			    color: #fff;" + 
				"			    background-color: #28a745;" + 
				"			    border-color: #28a745;" + 
				"			}" + 
				"" + 
				"			.btn {" + 
				"			    display: inline-block;" + 
				"			    font-weight: 400;" + 
				"			    text-align: center;" + 
				"			    white-space: nowrap;" + 
				"			    vertical-align: middle;" + 
				"			    -webkit-user-select: none;" + 
				"			    -moz-user-select: none;" + 
				"			    -ms-user-select: none;" + 
				"			    user-select: none;" + 
				"			    border: 1px solid transparent;" + 
				"			    padding: .375rem .75rem;" + 
				"			    font-size: 1rem;" + 
				"			    line-height: 1.5;" + 
				"			    border-radius: .25rem;" + 
				"			    transition: color .15s ease-in-out,background-color .15s ease-in-out,border-color .15s ease-in-out,box-shadow .15s ease-in-out;" + 
				"			}" + 
				"" + 
				"			a {" + 
				"			    color: #007bff;" + 
				"			    text-decoration: none;" + 
				"			    background-color: transparent;" + 
				"			    -webkit-text-decoration-skip: objects;" + 
				"			}" + 
				"			.btn-danger {" + 
				"			    color: #fff;" + 
				"			    background-color: #dc3545;" + 
				"			    border-color: #dc3545;" + 
				"			}" + 
				"        </style>" + 
				"    </head>" + 
				"    <body>" + 
				"        <div class=\"container\">" + 
				"            <div class=\"offset-md-3 col-md-6\">" + 
				"                <div class=\"card\" style=\"width: 18rem;\">" + 
				"                    <img alt=\"Card image cap\" class=\"card-img-top\" src=\"http://211.159.186.201:8080/myimagesstore/cry.jpg\">" + 
				"                        <div class=\"card-body\">" + 
				"                            <h5 class=\"card-title\">" + 
				"                                离职审批" + 
				"                            </h5>" + 
				"                            <p class=\"card-text\">"
				
				;
		sb.append(html);
		sb.append(name);
		//html+=name;
		String html2="申请了离职,现在请你做出审批" + 
				"                            </p>" + 
				"                            <a class=\"btn btn-success\" href=\"http://211.159.186.201:8080/quit/approvalPass?status=1&id="+id
				+"\">" + 
				"                                通过申请" + 
				"                            </a>" + 
				"                            <a class=\"btn btn-danger\" href=\"http://211.159.186.201:8080/quit/approvalNoPass?status=2&id="+id
				+"\">" + 
				"                                驳回申请" + 
				"                            </a>" + 
				"                        </div>" + 
				"                    </img>" + 
				"                </div>" + 
				"            </div>" + 
				"        </div>" + 
				"    </body>" + 
				"</html>"
				;
		sb.append(html2);
		msg.setToMail(toMail).setToName(toName).setContetnText(sb.toString()).setSubject("离职提醒").sendMsg();
/*		String html="<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<meta charset=\"utf-8\" />"
				+ "<title>test</title>"
				+ "</head>"
				+ "<body>"
				+ "<script src=\"https://cdn.bootcss.com/twitter-bootstrap/4.1.3/js/bootstrap.min.js\"></script>"
				+ "<link href=\"https://cdn.bootcss.com/twitter-bootstrap/4.1.3/css/bootstrap.min.css\" rel=\"stylesheet\">"
				+ "<div class=\"container\">"
				+ "<div class=\"offset-md-3 col-md-6\">"
				+ "<div class=\"card\" style=\"width: 18rem;\">"
				+ "<img class=\"card-img-top\" src=\"http://211.159.186.201:8080/images/cry.jpg\" alt=\"Card image cap\">"
				+ "<div class=\"card-body\">"
				+ "<h5 class=\"card-title\">离职审批</h5>"
				+ "<p class=\"card-text\">"
				
				;
		
		html+=name;
		html+="申请了离职,现在请你做出审批</p>"
				+ "<a href=\"http://localhost:8080/quit/approvalPass?id="+id
				+ " \" class=\"btn btn-success\">通过申请</a>"
				+ "<a href=\"http://localhost:8080/quit/approvalNoPass?id="+id
				+ " \" class=\"btn btn-danger\">驳回申请</a>"
				+ "</div>"
				+ "</div>"
				+ "</div>"
				+ "</div>"
				+ "</body>"
				+ "</html>"
				;
		msg.setToMail(toMail).setToName(toName).setContetnText(html).setSubject("离职提醒").sendMsg();
*/	}

}
