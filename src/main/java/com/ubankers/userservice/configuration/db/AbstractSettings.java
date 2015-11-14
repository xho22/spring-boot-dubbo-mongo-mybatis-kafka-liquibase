package com.ubankers.userservice.configuration.db;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractSettings {

	private static ObjectMapper mapper = new ObjectMapper();


	@Override
	public String toString() {
		try {
			return mapper.writeValueAsString(this);
		} catch ( Exception e ) {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}
}
