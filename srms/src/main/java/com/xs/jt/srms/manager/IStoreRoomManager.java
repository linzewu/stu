package com.xs.jt.srms.manager;

import java.util.List;
import java.util.Map;

import com.xs.jt.srms.entity.StoreRoom;

public interface IStoreRoomManager {

	public Map<String, Object> getStoreRoomList(Integer page, Integer rows, StoreRoom storeRoom);

	public void saveStoreRoom(StoreRoom storeRoom);

	public void deleteStoreRoom(StoreRoom storeRoom);
	
	public void deleteStoreRoomByArchivesNo(String archivesNo);
	
	public List<Map> getArchiveRackStatistics(String zt);
	
	public StoreRoom findByRackNo(String rackNo);

}
