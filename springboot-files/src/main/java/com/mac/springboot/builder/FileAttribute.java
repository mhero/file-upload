package com.mac.springboot.builder;

import com.google.common.base.CaseFormat;

public enum FileAttribute {
	CREATION_DATE, LAST_ACCESS, LAST_MODIFY;

	@Override
	public String toString() {

		return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, name().toLowerCase());
	}
}
