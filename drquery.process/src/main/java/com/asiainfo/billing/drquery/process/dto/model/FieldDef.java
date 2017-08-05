package com.asiainfo.billing.drquery.process.dto.model;


public class FieldDef {

	// data members
	private int pos;
	private String name = new String();
	private String value = new String();

	public FieldDef() {
	}

	public FieldDef(final FieldDef rhs) {
		copy(rhs);
	}

	public void copy(final FieldDef rhs) {
		if (rhs == null)
			return;
		pos = rhs.pos;
		if (rhs.name != null)
			name = rhs.name;
		else
			name = rhs.name;
		if (rhs.value != null)
			value = rhs.value;
		else
			value = rhs.value;
		return;
	}

	public boolean equals(final FieldDef rhs) {
		if (rhs == null)
			return false;

		if (!(pos == rhs.pos))
			return false;

		if (!(name.equals(rhs)))
			return false;

		if (!(value.equals(rhs)))
			return false;

		return true;
	}


	// get functions
	public int getPos() {
		return pos;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	// set functions
	public void setPos(int value) {
		pos = value;
	}

	public void setName(String value) {
		name = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
