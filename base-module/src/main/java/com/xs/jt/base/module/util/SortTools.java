package com.xs.jt.base.module.util;

import org.springframework.data.domain.Sort;

import com.xs.jt.base.module.entity.SortDto;

/**
 * 排序
 * @author linzewu
 *
 */
public class SortTools {

	/**
	 * 单个字段排序
	 * @param orderType
	 * @param orderField
	 * @return
	 */
	public static Sort basicSort(String orderType, String orderField) {
        Sort sort = new Sort(Sort.Direction.fromString(orderType), orderField);
        return sort;
    }
	
	/**
	 * 多个字段排序
	 * @param dtos
	 * @return
	 */
	public static Sort basicSort(SortDto... dtos) {
	    Sort result = null;
	    for(int i=0; i<dtos.length; i++) {
	        SortDto dto = dtos[i];
	        if(result == null) {
	            result = new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField());
	        } else {
	            result = result.and(new Sort(Sort.Direction.fromString(dto.getOrderType()), dto.getOrderField()));
	        }
	    }
	    return result;
	}



}
