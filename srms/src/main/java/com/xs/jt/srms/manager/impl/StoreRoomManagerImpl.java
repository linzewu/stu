package com.xs.jt.srms.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.common.Common;
import com.xs.jt.srms.dao.ArchivalCaseRepository;
import com.xs.jt.srms.dao.StoreRoomRepository;
import com.xs.jt.srms.entity.ArchivalCase;
import com.xs.jt.srms.entity.StoreRoom;
import com.xs.jt.srms.manager.IStoreRoomManager;
import com.xs.jt.srms.util.NumberFormatUtil;
@Service
public class StoreRoomManagerImpl implements IStoreRoomManager {
	
	@Autowired
	private StoreRoomRepository storeRoomRepository;
	@Autowired
	private ArchivalCaseRepository archivalCaseRepository;

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
				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.asc(root.get("archivesNo")));
				orders.add(criteriaBuilder.asc(root.get("rackNo")));
				query.orderBy(orders);			
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
		if (storeRoom.getId() != null) {
			StoreRoom oldStoreRoom = this.storeRoomRepository.findById(storeRoom.getId()).get();
			//修改
			
			if (oldStoreRoom.getCellCapacity() != storeRoom.getCellCapacity()
					|| oldStoreRoom.getRackCols() != storeRoom.getRackCols()
					|| oldStoreRoom.getRackRows() != storeRoom.getRackRows()) {
				this.archivalCaseRepository.deleteArchivalCaseByRackNo(storeRoom.getRackNo());
				newStoreRoom(storeRoom);
			}else {
				storeRoomRepository.save(storeRoom);
			}
			
		}else {
			//新增
			newStoreRoom(storeRoom);
		}
		
	}
	
	private void newStoreRoom(StoreRoom storeRoom) {
		storeRoomRepository.save(storeRoom);
		
		int rows = storeRoom.getRackRows();
		int cols = storeRoom.getRackCols();
		int capacity = storeRoom.getCellCapacity();
		List<ArchivalCase> acList = new ArrayList<ArchivalCase>();
		for(int i=1;i<=rows;i++) {			
			for(int j=1;j<=cols;j++) {
				String fileNo = "000";
				for(int k=1;k<=capacity;k++) {
					ArchivalCase archivalCase = new ArchivalCase();
					archivalCase.setArchivesNo(storeRoom.getArchivesNo());
					archivalCase.setRackCol(j);
					archivalCase.setRackNo(storeRoom.getRackNo());
					archivalCase.setRackRow(i);
					archivalCase.setZt(ArchivalCase.ZT_WSY);
					fileNo = NumberFormatUtil.autoGenericCode(fileNo, 3);
					archivalCase.setFileNo(fileNo);
					//archivalCase.setBarCode(archivalCase.getArchivesNo()+archivalCase.getRackNo()+archivalCase.getRackCol()+archivalCase.getRackRow()+archivalCase.getFileNo());
					acList.add(archivalCase);
				}
			}
		}
		this.archivalCaseRepository.saveAll(acList);
	}

	@Override
	public void deleteStoreRoom(StoreRoom storeRoom) {
		storeRoomRepository.delete(storeRoom);
		archivalCaseRepository.deleteArchivalCaseByRackNo(storeRoom.getRackNo());
		
	}

	@Override
	public void deleteStoreRoomByArchivesNo(String archivesNo) {
		storeRoomRepository.deleteStoreRoomByArchivesNo(archivesNo);
		archivalCaseRepository.deleteArchivalCaseByArchivesNo(archivesNo);
	}

	@Override
	public List<Map> getArchiveRackStatistics(String zt) {
		
		return storeRoomRepository.getArchiveRackStatistics(zt);
	}

}
