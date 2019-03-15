package com.xs.jt.cmsvideo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xs.jt.cmsvideo.entity.VideoPara;

@Repository
public interface VideoParaRepository extends JpaRepository<VideoPara, Integer>{

}
