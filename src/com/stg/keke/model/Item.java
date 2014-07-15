package com.stg.keke.model;

public class Item {
	private int drawable;
	private int title;

	public Item(int drawable, int title) {
		this.drawable = drawable;
		this.title = title;
	}

	public int getDrawable() {
		return drawable;
	}

	public void setDrawable(int drawable) {
		this.drawable = drawable;
	}

	public int getTitle() {
		return title;
	}

	public void setTitle(int title) {
		this.title = title;
	}

}
