package com.warpaint.challengeservice.model.assetdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import lombok.experimental.Accessors;

@JsonInclude( Include.NON_EMPTY )
@Data
@Accessors(chain = true)
public class AssetData implements Comparable<AssetData> {

	private BigDecimal closePrice;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate priceAsOf;
	
	@Override
	public int compareTo(AssetData that) {
		return Comparator.comparing(AssetData::getPriceAsOf, 
			   Comparator.nullsFirst(Comparator.naturalOrder())).compare(this, that);
	}
	
}
