package com.xs.jt.srms.manager.impl;

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

import com.xs.jt.base.module.common.Common;
import com.xs.jt.srms.dao.StoreRoomRepository;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IStoreRoomManager;
@Service
public class StoreRoomManagerImpl implements IStoreRoomManager {
	
	@Autowired
	private StoreRoomRepository storeRoomRepository;

	@Override
	public Map<String, Object> getStoreRoomList(Integer page, Integer rows, StoreRoom storeRoom) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<StoreRoom> bookPage = storeRoomRepository.findAll(new Specification<StoreRoom>() {
			public Predicate toPredicate(Root<StoreRoom> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if(Common.isNotEmpty(storeRoom.getStorageType())) {
					list.add(criteriaBuilder.equal(root.get("storageType").as(String.class), storeRoom.getStorageType()));
				}
							
				Predicate[] p = new Predicate[list.size()];
				return criteriaBuilder.and(list.toArray(p));
			}
		}, pageable);
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rows", bookPage.getContent());
		data.put("total", bookPage.getTotalElements());
		return data;
	}

	@Override
	public void saveStoreRoom(StoreRoom storeRoom) {
		storeRoomRepository.save(storeRoom);
		
	}

	@Override
	public void deleteStoreRoom(StoreRoom storeRoom) {
		storeRoomRepository.delete(storeRoom);
		
	}

	@Override
	public void deleteStoreRoomByArchivesNo(String archivesNo) {
		storeRoomRepository.deleteStoreRoomByArchivesNo(archivesNo);
		
	}

}
