package com.asiainfo.billing.drquery.process.dto.model;

import java.util.ArrayList;

public class SumTypeList extends ArrayList {

	public SumTypeList() {
	}

	public SumTypeList(SumTypeList rhs) {
		copy(rhs);
	}

	public void copy(SumTypeList rhs) {
		if (rhs == null)
			return;
		this.clear();
		for (int length = 0; length < rhs.size(); length++) {
			this.add(rhs.get(length));
		}
		return;
	}

	public boolean equals(final SumTypeList rhs) {
		if (rhs == null)
			return false;

		if (rhs.size() != this.size())
			return false;

		for (int length = 0; length < rhs.size(); length++) {
			if (!(((SumType) this.get(length)).equals((SumType) (rhs
					.get(length)))))
				return false;
		}
		return true;
	}
}
