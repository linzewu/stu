package com.xs.jt.cmsvideo.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xs.jt.cmsvideo.dao.VideoParaRepository;
import com.xs.jt.cmsvideo.entity.VideoPara;
import com.xs.jt.cmsvideo.manager.IVideoParaManager;
@Service
public class VideoParaManagerImpl implements IVideoParaManager {
	@Autowired
	private VideoParaRepository videoParaRepository;

	@Override
	public void addVideoPara(VideoPara videoPara) {
		videoParaRepository.save(videoPara);
	}

}
