package org.zorkclone.core.model;

import javax.xml.bind.annotation.XmlElement;

public class BorderModel {

	private Object borders;

	public Object getBorders() {
		return borders;
	}

	@XmlElement
	public void setBorders(Object borders) {
		this.borders = borders;
	}
}
