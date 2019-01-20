package com.xs.jt.veh.manager;

import java.util.List;
import java.util.Map;

public interface IReportManager {

	public Map<String, Object> getReport1(String jylsh, int jycs);

	public String getReport4(String jylsh);

	public Map<String, List> getReport2(String jylsh);

}
