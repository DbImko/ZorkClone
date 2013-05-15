package org.zorkclone.core.model.jaxb.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.zorkclone.core.model.DirectionType;

public final class DirectionTypeAdapter extends XmlAdapter<String, DirectionType> {

	@Override
	public String marshal(DirectionType arg0) throws Exception {
		return null;
	}

	@Override
	public DirectionType unmarshal(String value) throws Exception {
		return DirectionType.getByName(value);
	}

}