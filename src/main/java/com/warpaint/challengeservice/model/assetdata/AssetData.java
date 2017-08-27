package com.warpaint.challengeservice.model.assetdata;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@JsonInclude( Include.NON_EMPTY )
@Data
@NoArgsConstructor
@AllArgsConstructor
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
