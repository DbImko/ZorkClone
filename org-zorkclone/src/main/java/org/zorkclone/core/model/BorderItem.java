package org.zorkclone.core.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.zorkclone.core.model.jaxb.adapters.DirectionTypeAdapter;

public class BorderItem {

	private String name;

	private DirectionType direction;

	public BorderItem() {
	}

	public BorderItem(String name, DirectionType direction) {
		this.name = name;
		this.direction = direction;
	}

	public String getName() {
		return name;
	}

	@XmlElement(name = "name")
	public void setName(String name) {
		this.name = name;
	}

	public DirectionType getDirection() {
		return direction;
	}

	@XmlElement(name = "direction")
	@XmlJavaTypeAdapter(DirectionTypeAdapter.class)
	public void setDirection(DirectionType direction) {
		this.direction = direction;
	}

}
