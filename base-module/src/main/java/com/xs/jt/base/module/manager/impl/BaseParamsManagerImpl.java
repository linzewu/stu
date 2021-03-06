package com.xs.jt.base.module.manager.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import com.xs.jt.base.module.dao.BaseParamsRepository;
import com.xs.jt.base.module.entity.BaseParams;
import com.xs.jt.base.module.entity.SortDto;
import com.xs.jt.base.module.manager.IBaseParamsManager;
import com.xs.jt.base.module.util.SortTools;

@Service("baseParamsManager")
public class BaseParamsManagerImpl implements IBaseParamsManager {

	@Autowired
	private BaseParamsRepository baseParamsRepository;

	@Autowired
	private ServletContext servletContext;

	public List<BaseParams> getBaseParams() {
		List<BaseParams> bps = baseParamsRepository
				.findAll(SortTools.basicSort(new SortDto("asc", "type"), new SortDto("asc", "seq")));

		return bps;
	}

	public BaseParams save(BaseParams baseParams) {
		baseParams.setCreateTime(new Date());
		baseParams = this.baseParamsRepository.save(baseParams);
		return baseParams;
	}

	public void delete(Integer id) {
		BaseParams baseParams = this.baseParamsRepository.findById(id).get();
		if (baseParams != null) {
			this.baseParamsRepository.delete(baseParams);
		}
	}

	public Map<String, Object> getBaseParams(Integer page, Integer rows, final BaseParams param) {
		Pageable pageable = new QPageRequest(page, rows);

		@SuppressWarnings("serial")
		Page<BaseParams> bookPage = baseParamsRepository.findAll(new Specification<BaseParams>() {
			public Predicate toPredicate(Root<BaseParams> root, CriteriaQuery<?> query,
					CriteriaBuilder criteriaBuilder) {
				List<Predicate> list = new ArrayList<Predicate>();
				if (null != param.getParamName() && !"".equals(param.getParamName())) {
					list.add(criteriaBuilder.equal(root.get("paramName").as(String.class), param.getParamName()));
				}
				if (null != param.getType() && !"".equals(param.getType())) {
					list.add(criteriaBuilder.equal(root.get("type").as(String.class), param.getType()));
				}

				List<Order> orders = new ArrayList<Order>();
				orders.add(criteriaBuilder.asc(root.get("type")));
				orders.add(criteriaBuilder.asc(root.get("seq")));
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

	public List<BaseParams> getBaseParamsByType(String type) {
		return baseParamsRepository.findBaseParamsByType(type);
	}

	public BaseParams getBaseParam(String type, String paramName) {
		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		if (bps == null) {
			bps = getBaseParams();
		}
		for (BaseParams param : bps) {
			if (param.getType().equals(type) && param.getParamName().equals(paramName)) {
				return param;
			}
		}
		return null;

	}

	public Map<String, List<BaseParams>> convertBaseParam2Map() {

		Map<String, List<BaseParams>> mapParam = new HashMap<String, List<BaseParams>>();

		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		if (bps == null) {
			bps = getBaseParams();
		}

		for (BaseParams param : bps) {
			String key = param.getType();
			List<BaseParams> typeList = mapParam.get(key);
			if (typeList == null) {
				typeList = new ArrayList<BaseParams>();
				typeList.add(param);
				mapParam.put(key, typeList);
			} else {
				typeList.add(param);
			}
		}

		return mapParam;
	}

	@Override
	public BaseParams getBaseParamByValue(String type, String paramValue) {
		List<BaseParams> bps = (List<BaseParams>) servletContext.getAttribute("bps");
		if (bps == null) {
			bps = getBaseParams();
		}
		for (BaseParams param : bps) {
			if (param.getType().equals(type) && param.getParamValue().equals(paramValue)) {
				return param;
			}
		}
		return null;
	}

}
