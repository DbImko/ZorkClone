package org.zorkclone.core.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.zorkclone.core.model.jaxb.adapters.BooleanAdapter;

public class ConditionModel {

	private boolean has;

	private String object;

	private String owner;

	private String status;

	public Boolean isHas() {
		return has;
	}

	@XmlElement
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	public void setHas(Boolean has) {
		this.has = has;
	}
	
	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(has).append(object).append(owner)
				.append(status).toHashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ConditionModel) {
			final ConditionModel other = (ConditionModel) obj;
			return new EqualsBuilder().append(has, other.has)
					.append(object, other.object).append(owner, other.owner)
					.append(status, other.status).isEquals();
		} else {
			return false;
		}
	}
}
