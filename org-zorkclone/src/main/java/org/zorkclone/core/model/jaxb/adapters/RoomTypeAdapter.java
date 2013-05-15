package org.zorkclone.core.model.jaxb.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.zorkclone.core.model.RoomType;

public final class RoomTypeAdapter extends XmlAdapter<String, RoomType> {

	@Override
	public String marshal(RoomType arg0) throws Exception {
		return null;
	}

	@Override
	public RoomType unmarshal(String value) throws Exception {
		return RoomType.getByName(value);
	}

}
