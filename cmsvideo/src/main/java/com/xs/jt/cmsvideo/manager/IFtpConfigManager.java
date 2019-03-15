package com.xs.jt.cmsvideo.manager;

import java.util.Map;

import com.xs.jt.cmsvideo.entity.FtpConfig;

public interface IFtpConfigManager {

	public Map<String, Object> getFtpConfigList(Integer page, Integer rows, FtpConfig ftpConfig);

	public void saveFtpConfig(FtpConfig ftpConfig);

	public void deleteFtpConfig(FtpConfig ftpConfig);

}
