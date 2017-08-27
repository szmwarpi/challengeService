package com.warpaint.challengeservice;

import java.util.NavigableSet;
import java.util.TreeSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.warpaint.challengeservice.model.assetdata.AssetData;
import com.warpaint.challengeservice.model.assetdataprocessor.AssetDataProcessor;

@Component
public class ChallengeService {
	
	private static final Logger logger = LoggerFactory.getLogger(ChallengeService.class);
	
	private AssetDataProcessor assetDataProcessor;
	
	@Autowired
	public ChallengeService(AssetDataProcessor assetDataProcessor) {
		this.assetDataProcessor = assetDataProcessor;
	}
	
	public NavigableSet<AssetData>getHistoricalPriceData() {
		NavigableSet<AssetData> historicalPriceData = assetDataProcessor.fetchHistoricalData();
		logger.info("Historical price data {}", historicalPriceData.size());
		return historicalPriceData;
	}
	
	public NavigableSet<AssetData>getFuturePriceData() {
		logger.info("Generate future prices");
		// TODO Implement getFuturePrices()
		return new TreeSet<>();
	}
	
}
