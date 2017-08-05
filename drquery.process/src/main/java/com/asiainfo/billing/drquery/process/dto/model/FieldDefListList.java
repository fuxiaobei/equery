package com.asiainfo.billing.drquery.process.dto.model;

import java.util.ArrayList;

public class FieldDefListList extends ArrayList {

	public FieldDefListList() {
	}

	public FieldDefListList(FieldDefListList rhs) {
		copy(rhs);
	}

	// copy function
	public void copy(FieldDefListList rhs) {
		if (rhs == null)
			return;
		this.clear();
		for (int length = 0; length < rhs.size(); length++) {
			this.add(rhs.get(length));
		}
		return;
	}

	// equals function
	public boolean equals(final FieldDefListList rhs) {
		if (rhs == null)
			return false;

		if (rhs.size() != this.size())
			return false;

		for (int length = 0; length < rhs.size(); length++) {
			if (!(((FieldDefList) this.get(length)).equals((FieldDefList) (rhs
					.get(length)))))
				return false;
		}
		return true;
	}
}
