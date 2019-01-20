package com.xs.jt.veh.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.veh.dao.RecordInfoOfCheckStaffRepository;
import com.xs.jt.veh.entity.RecordInfoOfCheckStaff;
import com.xs.jt.veh.manager.IRecordInfoOfCheckStaffManager;

@Service("recordInfoOfCheckStaffManager")
public class RecordInfoOfCheckStaffManagerImpl implements IRecordInfoOfCheckStaffManager {
	@Autowired
	private RecordInfoOfCheckStaffRepository recordInfoOfCheckStaffRepository;

	@Override
	public void deleteRecordInfo() {
		this.recordInfoOfCheckStaffRepository.deleteAll();
	}

	@Override
	public void saveRecordInfoOfCheckStaff(List<RecordInfoOfCheckStaff> list) {
		for (RecordInfoOfCheckStaff vo : list) {
			recordInfoOfCheckStaffRepository.save(vo);
		}

	}

	@Override
	public Map<String, Object> getRecordInfoOfCheckStaff(Integer page, Integer rows,
			RecordInfoOfCheckStaff recordInfoOfCheckStaff) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<RecordInfoOfCheckStaff> bookPage = recordInfoOfCheckStaffRepository
				.findAll(new Specification<RecordInfoOfCheckStaff>() {
					public Predicate toPredicate(Root<RecordInfoOfCheckStaff> root, CriteriaQuery<?> query,
							CriteriaBuilder criteriaBuilder) {
						List<Predicate> list = new ArrayList<Predicate>();

						Predicate[] p = new Predicate[list.size()];
						return criteriaBuilder.and(list.toArray(p));
					}
				}, pageable);

		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());

		return data;
	}

}
