package com.zai360.portal.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zai360.portal.test.dao.WriteDao;
import com.zai360.portal.test.dao.impl.WriteDaoImpl;
import com.zai360.portal.test.service.WriteService;

@Service
public class WriteServiceImpl extends WriteDaoImpl implements WriteService {
	@Autowired
	private WriteDao writeDao;

}
