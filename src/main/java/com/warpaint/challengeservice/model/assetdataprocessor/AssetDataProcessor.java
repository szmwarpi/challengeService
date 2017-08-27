package com.warpaint.challengeservice.model.assetdataprocessor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.warpaint.challengeservice.model.assetdata.AssetData;


@Component
public class AssetDataProcessor {
	
	private static final Logger logger = LoggerFactory.getLogger(AssetDataProcessor.class);
	
	private static final String[] PRICE_HEADER = {"Date","Open","High","Low","Close","Adj Close","Volume"};
	private static final int PRICE_INDEX_DATE = 0;
    private static final int PRICE_INDEX_CLOSE = 4;
	
	@Value("classpath:^FVX.csv")
    private Resource csv;
	private DateTimeFormatter dateFormatter;
	
	public AssetDataProcessor() {
		dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	}
	
	private AssetData parseHistoryLine(String line) {
        String[] tokens = line.split(",");
        if (tokens.length == PRICE_HEADER.length) {
            try {
            	LocalDate date = LocalDate.parse(tokens[PRICE_INDEX_DATE], dateFormatter);
            	BigDecimal close = new BigDecimal(tokens[PRICE_INDEX_CLOSE]);
            	return new AssetData().setPriceAsOf(date).setClosePrice(close);
            } catch (DateTimeParseException e) {
                logger.error("Failed to parse column: date: {}", line);
            } catch (NumberFormatException n) {
            	logger.error("Failed to parse column: close: {}", line);
            }
        }
        else {
        	logger.error("Invalid number of columns: {}", line);
        }
        return null;
    }
	
	public NavigableSet<AssetData> fetchHistoricalData() {
		NavigableSet<AssetData> historicalData = new TreeSet<>();
		try (InputStream stream = csv.getInputStream();
			 InputStreamReader reader = new InputStreamReader(stream);
			 BufferedReader br = new BufferedReader(reader)) 
		{
			historicalData = br.lines().skip(1)
						.map(this::parseHistoryLine)
						.filter(Objects::nonNull)
						.collect(Collectors.toCollection(TreeSet::new));
				logger.info("Fetched {} price data", historicalData.size());
		} 
		catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		return historicalData;
	}
	
}
