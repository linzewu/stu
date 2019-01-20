package com.xs.jt.veh.common;

import java.util.Comparator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xs.jt.veh.entity.Flow;
import com.xs.jt.veh.entity.VehFlow;

public class VheFlowComparator implements Comparator<VehFlow> {

	private static Log logger = LogFactory.getLog(VheFlowComparator.class);

	public VheFlowComparator(Flow flow) {
		this.flow = flow;
	}

	private Flow flow;

	public Flow getFlow() {
		return flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	@Override
	public int compare(VehFlow o1, VehFlow o2) {

		String xmlx1 = o1.getJyxm().substring(0, 1);
		String xmlx2 = o2.getJyxm().substring(0, 1);

		// 优先按工位排序
		if (o1.getGwsx() > o2.getGwsx()) {
			return 1;
		} else if (o1.getGwsx() < o1.getGwsx()) {
			return -1;
		} else {

			// 制动项目 并且交叉检测的
			if (flow.getCzzdjc() == Flow.CZZDJC_YES
					&& ((xmlx1.equals("B") || xmlx1.equals("L"))
					&& (xmlx2.equals("B") || xmlx2.equals("L")))) {
				// 称重制动交叉检测
				Integer sx1 = Integer.parseInt(o1.getJyxm().substring(1, 2));
				Integer sx2 = Integer.parseInt(o2.getJyxm().substring(1, 2));

				if (sx1 == 0) {
					sx1 = Integer.parseInt(o1.getMemo());
				}
				if (sx2 == 0) {
					sx2 = Integer.parseInt(o2.getMemo());
				}

				if (sx1 > sx2) {
					return 1;
				} else if (sx1 < sx2) {
					return -1;
				} else {
					
					if (o1.getSbsx() > o2.getSbsx()) {
						return 1;
					} else if (o1.getSbsx() < o2.getSbsx()) {
						return -1;
					} else {
						// 设备相同则按制动排序
						return zdSort(o1, o2);
					}
				}
			} else {
				// 其他情况
				if (o1.getSbsx() > o2.getSbsx()) {
					return 1;
				} else if (o1.getSbsx() < o2.getSbsx()) {
					return -1;
				} else {
					// 如果是制动则按制动排序
					return zdSort(o1, o2);
				}
			}
		}
	}

	/**
	 * 设备相同 制动排序
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	private int zdSort(VehFlow o1, VehFlow o2) {

		String jyxm1 = o1.getJyxm().substring(0, 1);
		String jyxm2 = o2.getJyxm().substring(0, 1);

		logger.info("jyxm1:" + jyxm1 + " " + o1.getMemo());
		logger.info("jyxm2:" + jyxm2 + " " + o2.getMemo());

		if (o1.getJyxm().substring(0, 1).equals("B") || o1.getJyxm().substring(0, 1).equals("L")) {
			Integer zw1 = Integer.parseInt(o1.getJyxm().substring(1, 2));
			Integer zw2 = Integer.parseInt(o2.getJyxm().substring(1, 2));

			int retzw = 0;
			if (zw1 == 0) {
				zw1 = Integer.parseInt(o1.getMemo());
			}
			if (zw2 == 0) {
				zw2 = Integer.parseInt(o2.getMemo());
				retzw = -1;
			}
			if (zw1 > zw2) {
				return 1;
			} else if (zw1 < zw2) {
				return -1;
			} else {
				char c1 = o1.getJyxm().charAt(0);
				char c2 = o2.getJyxm().charAt(0);
				if (c1 > c2) {
					return 1;
				} else if (c1 < c2) {
					return -1;
				} else {
					return retzw;
				}

			}
		}
		// 灯光
		if (jyxm1.equals("H") && jyxm2.equals("H")) {
			Integer dg1 = Integer.parseInt(o1.getJyxm().substring(1, 2));
			Integer dg2 = Integer.parseInt(o2.getJyxm().substring(1, 2));
			if (dg1 > dg2) {
				return 1;
			} else if (dg1 < dg2) {
				return -1;
			} else {
				return 0;
			}
		}
		return 0;
	}

}
