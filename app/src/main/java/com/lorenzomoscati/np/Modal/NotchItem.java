package com.lorenzomoscati.np.Modal;

public class NotchItem {

	private final int height;
	private final int width;
	private final int size;
	private final int topRadius;
	private final int bottomRadius;
	private final String title;

	// Method to contain a notch preset
	public NotchItem(int height, int width, int size, int topRadius, int bottomRadius, String title) {

		this.height = height;
		this.width = width;
		this.size = size;
		this.topRadius = topRadius;
		this.bottomRadius = bottomRadius;
		this.title = title;

	}



	// Returns [int] the height
	public int getHeight() {

		return height;

	}

	// Returns [int] the width
	public int getWidth() {

		return width;

	}

	// Returns [int] the size
	public int getSize() {

		return size;

	}

	// Returns [int] the topRadius
	public int getTopRadius() {

		return topRadius;

	}

	// Returns [int] the bottomRadius
	public int getBottomRadius() {

		return bottomRadius;

	}

	// Returns [int] the title of the preset
	public String getTitle() {

		return title;

	}

}
