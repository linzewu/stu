package com.xs.jt.veh.manager;

import com.xs.jt.base.module.common.Message;

public interface ICheckQueueManager {

	public Integer getPdxh(String jcxdh, Integer gwsx);

	public Message upLine(String jylsh, Integer jycs);

}
