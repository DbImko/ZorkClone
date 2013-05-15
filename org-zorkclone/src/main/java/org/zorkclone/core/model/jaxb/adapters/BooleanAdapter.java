package org.zorkclone.core.model.jaxb.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public String marshal(Boolean arg0) throws Exception {
		return null;
	}

	@Override
	public Boolean unmarshal(String value) throws Exception {
		if ("no".equals(value)) {
			return false;
		}
		if ("yes".equals(value)) {
			return true;
		}
		return null;
	}
	
}