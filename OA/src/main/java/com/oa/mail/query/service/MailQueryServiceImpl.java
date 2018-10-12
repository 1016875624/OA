package com.oa.mail.query.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oa.common.date.utils.DateUtils;
import com.oa.mail.query.entity.MailQuery;
import com.oa.mail.query.repository.MailQueryRepository;

@Service
@Transactional
public class MailQueryServiceImpl implements MailQueryService {

	@Autowired
	private MailQueryRepository mailQueryRepository;
	@Override
	public MailQuery save(MailQuery entity) {
		return mailQueryRepository.save(entity);
	}

	@Override
	public List<MailQuery> saveAll(List<MailQuery> entities) {
		return mailQueryRepository.saveAll(entities);
	}

	@Override
	public MailQuery findById(Date id) {
		return mailQueryRepository.findById(id).get();
	}

	@Override
	public boolean existsById(Date id) {
		return mailQueryRepository.existsById(id);
	}

	@Override
	public List<MailQuery> findAll() {
		return mailQueryRepository.findAll();
	}

	@Override
	public List<MailQuery> findAllById(List<Date> ids) {
		return mailQueryRepository.findAllById(ids);
	}

	@Override
	public long count() {
		return mailQueryRepository.count();
	}

	@Override
	public void deleteById(Date id) {
		mailQueryRepository.deleteById(id);
	}

	@Override
	public void delete(MailQuery entity) {
		mailQueryRepository.delete(entity);
	}

	@Override
	public void deleteAll(List<MailQuery> entities) {
		mailQueryRepository.deleteAll(entities);
	}

	@Override
	public void deleteAll(Date[] ids) {
		mailQueryRepository.deleteAllByIds(ids);
	}

	@Override
	public void deleteAll() {
		mailQueryRepository.deleteAll();
	}

	@Override
	public Page<MailQuery> findAll(Pageable pageable) {
		
		return mailQueryRepository.findAll(pageable);
	}

	@Override
	public List<MailQuery> findAll(Sort sort) {
		return mailQueryRepository.findAll(sort);
	}

	@Override
	public MailQuery findOne(Specification<MailQuery> spec) {
		return mailQueryRepository.findOne(spec).get();
	}

	@Override
	public List<MailQuery> findAll(Specification<MailQuery> spec) {
		return mailQueryRepository.findAll(spec);
	}

	@Override
	public Page<MailQuery> findAll(Specification<MailQuery> spec, Pageable pageable) {
		return mailQueryRepository.findAll(spec, pageable);
	}

	@Override
	public List<MailQuery> findAllById(Date[] ids) {
		return mailQueryRepository.findAllById(Arrays.asList(ids));
	}

	@Override
	public List<MailQuery> findAll(Specification<MailQuery> spec, Sort sort) {
		return mailQueryRepository.findAll(spec, sort);
	}

	@Override
	public long count(Specification<MailQuery> spec) {
		return mailQueryRepository.count(spec);
	}

	@Override
	public void deleteAllById(Date[] ids) {
		mailQueryRepository.deleteAllByIds(ids);
	}

	@Override
	public boolean isMaxTimes() {
		Date today=DateUtils.getToday();
		MailQuery mq=mailQueryRepository.findById(today).orElse(null);
		if (mq==null) {
			mq=new MailQuery();
			mq.setDate(today);
			mq.setTimes(1);
			save(mq);
			return false;
		}
		else {
			if (mq.getTimes()<180) {
				mq.setTimes(mq.getTimes()+1);
				save(mq);
				return false;
			}
		}
		return true;
	}

}
