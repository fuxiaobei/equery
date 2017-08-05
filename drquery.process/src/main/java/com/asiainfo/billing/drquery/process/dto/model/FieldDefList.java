package com.asiainfo.billing.drquery.process.dto.model;

import java.util.ArrayList;

public class FieldDefList extends ArrayList {

	public FieldDefList() {
	}

	public FieldDefList(FieldDefList rhs) {
		copy(rhs);
	}

	public void copy(FieldDefList rhs) {
		if (rhs == null)
			return;
		this.clear();
		for (int length = 0; length < rhs.size(); length++) {
			this.add(rhs.get(length));
		}
		return;
	}

	public boolean equals(final FieldDefList rhs) {
		if (rhs == null)
			return false;

		if (rhs.size() != this.size())
			return false;

		for (int length = 0; length < rhs.size(); length++) {
			if (!(((FieldDef) this.get(length)).equals((FieldDef) (rhs
					.get(length)))))
				return false;
		}

		return true;
	}

}
