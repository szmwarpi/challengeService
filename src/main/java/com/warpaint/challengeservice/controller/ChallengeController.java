package com.warpaint.challengeservice.controller;

import java.util.NavigableSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.warpaint.challengeservice.ChallengeService;
import com.warpaint.challengeservice.model.assetdata.AssetData;

@RestController
public class ChallengeController {
	
	private final ChallengeService challengeService;
	
	@Autowired
	public ChallengeController(ChallengeService challengeService) {
		this.challengeService = challengeService;
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value="/historicalPrice", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<NavigableSet<AssetData>> getHistoricalPriceData() {
		NavigableSet<AssetData> assetData = challengeService.getHistoricalPriceData();
		return new ResponseEntity<>(assetData, HttpStatus.OK);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value="/price", method=RequestMethod.GET, produces="application/json")
	@ResponseBody
	public ResponseEntity<NavigableSet<AssetData>> getFuturePriceData() {
		NavigableSet<AssetData> assetData = challengeService.getFuturePriceData();
		return new ResponseEntity<>(assetData, HttpStatus.OK);
	}
	
}
