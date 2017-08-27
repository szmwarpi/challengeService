package com.warpaint.challengeservice;

import com.warpaint.challengeservice.model.assetdata.AssetData;
import com.warpaint.challengeservice.model.assetdataprocessor.AssetDataProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

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
	
	public NavigableSet<AssetData>getFuturePriceData(int monthCount, int iterationCount) {
		logger.info("Generate future prices");

		NavigableSet<AssetData> monthEndData = assetDataProcessor.fetchMonthEndData();

		MonteCarlo monteCarlo = new MonteCarlo(getPriceMovements(monthEndData));
		LocalDate[] futureMonthEnds = generateMonthEndDates(monthEndData.last().getPriceAsOf(), monthCount);

		double[] simulationResult = monteCarlo.simulateAndReturnMaxEndPrice(
                monthEndData.last().getClosePrice().doubleValue(),
                monthCount,
                iterationCount,
				4
        );

		return mergeDatesAndPrices(futureMonthEnds, simulationResult);
	}

	private double[] getPriceMovements(NavigableSet<AssetData> monthEndData) {
		double[] result = new double[monthEndData.size()-1];
		Iterator<AssetData> monthEndDataIterator = monthEndData.iterator();
		double previousPrice = monthEndDataIterator.next().getClosePrice().doubleValue();
		for (int i = 0; i < result.length; i++) {
			double price = monthEndDataIterator.next().getClosePrice().doubleValue();
			result[i] = price / previousPrice;
			previousPrice = price;
		}
		return result;
	}

	private LocalDate[] generateMonthEndDates(LocalDate start, int count) {
		LocalDate[] result = new LocalDate[count];
		LocalDate nextMonthStart = LocalDate.of(start.getYear(), start.getMonth().getValue(), 1).plusMonths(1);
		if (nextMonthStart.minusDays(1).equals(start)) {
			nextMonthStart = nextMonthStart.plusMonths(1);
		}
		for (int i = 0; i < count; i++ ) {
			result[i] = nextMonthStart.minusDays(1);
			nextMonthStart = nextMonthStart.plusMonths(1);
		}
		return result;
	}

	private double[] generatePrices(double start, double[] priceMovements) {
		double[] result = new double[priceMovements.length + 1];
		double price = start;
		result[0] = price;
		for (int i = 0; i < priceMovements.length; i++ ) {
			price *= priceMovements[i];
			result[i+1] = price;
		}
		return result;
	}

	private NavigableSet<AssetData> mergeDatesAndPrices(LocalDate[] dates, double[] prices) {
		if (dates.length != prices.length) {
			throw new IllegalArgumentException("List should be of the same length");
		}
		NavigableSet<AssetData> result = new TreeSet<>();
		for (int i = 0; i < dates.length; i++) {
			result.add(new AssetData(BigDecimal.valueOf(prices[i]).setScale(6, BigDecimal.ROUND_HALF_EVEN), dates[i]));
		}
		return result;
	}
}
