package com.warpaint.challengeservice.controller;

import java.util.NavigableSet;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
	public ResponseEntity<NavigableSet<AssetData>> getFuturePriceData(
			@RequestParam("numberOfMonths") Optional<Integer> monthCount,
			@RequestParam("numberOfIterations") Optional<Integer> iterationCount) {
		NavigableSet<AssetData> assetData = challengeService.getFuturePriceData(
				monthCount.orElse(240), iterationCount.orElse(1000)
		);
		return new ResponseEntity<>(assetData, HttpStatus.OK);
	}
	
}
