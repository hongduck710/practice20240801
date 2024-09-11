package org.zerock.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mapper.Sample1Mapper;
import org.zerock.mapper.Sample2Mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class SampleTxServiceImpl implements SampleTxService{
	
	@Setter(onMethod_ = { @Autowired })
	private Sample1Mapper mapper1;
	
	@Setter(onMethod_ = { @Autowired })
	private Sample2Mapper mapper2;
	
	@Transactional /* 예외가 발생할 경우  롤백처리 할 수 있도록 하는 어노테이션 */
	@Override
	public void addData(String value) {
		log.info("🌷🌷🌷🌷mapper1🌷🌷🌷🌷");
		mapper1.insertCol1(value);
		
		log.info("🌷🌷🌷🌷mapper2🌷🌷🌷🌷");
		mapper2.insertCol2(value);
		
		log.info("🌷🌷🌷🌷end🌷🌷🌷🌷");
	}
}
