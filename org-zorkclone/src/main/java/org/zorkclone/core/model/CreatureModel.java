package org.zorkclone.core.model;

public class CreatureModel {

	private String name;
	
	private String vulnerability;
	
	private TriggerModel trigger;
	
	private AttackModel attack;
	
	private boolean exists = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVulnerability() {
		return vulnerability;
	}

	public void setVulnerability(String vulnerability) {
		this.vulnerability = vulnerability;
	}

	public TriggerModel getTrigger() {
		return trigger;
	}

	public void setTrigger(TriggerModel trigger) {
		this.trigger = trigger;
	}

	public AttackModel getAttack() {
		return attack;
	}

	public void setAttack(AttackModel attack) {
		this.attack = attack;
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}
	
	@Override
	public String toString() {
		return super.toString() + " " + name;
	}
	
}
