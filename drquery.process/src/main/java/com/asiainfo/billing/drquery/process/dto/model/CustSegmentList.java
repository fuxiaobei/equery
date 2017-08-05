package com.asiainfo.billing.drquery.process.dto.model;

import java.util.ArrayList;

public class CustSegmentList extends ArrayList {

	public CustSegmentList() {
	}

	public CustSegmentList(CustSegmentList rhs) {
		copy(rhs);
	}

	// copy function
	public void copy(CustSegmentList rhs) {
		if (rhs == null)
			return;
		this.clear();
		for (int length = 0; length < rhs.size(); length++) {
			this.add(rhs.get(length));
		}
		return;
	}

	public boolean equals(final CustSegmentList rhs) {
		if (rhs == null)
			return false;

		if (rhs.size() != this.size())
			return false;

		for (int length = 0; length < rhs.size(); length++) {
			if (!(((CustSegment) this.get(length)).equals((CustSegment) (rhs
					.get(length)))))
				return false;
		}

		return true;
	}

}
