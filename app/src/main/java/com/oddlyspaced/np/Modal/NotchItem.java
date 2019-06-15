package com.oddlyspaced.np.Modal;

public class NotchItem {

    private int height, width, size, topRadius, bottomRadius;
    private String title;

    public NotchItem(int height, int width, int size, int topRadius, int bottomRadius, String title) {
        this.height = height;
        this.width = width;
        this.size = size;
        this.topRadius = topRadius;
        this.bottomRadius = bottomRadius;
        this.title = title;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSize() {
        return size;
    }

    public int getTopRadius() {
        return topRadius;
    }

    public int getBottomRadius() {
        return bottomRadius;
    }

    public String getTitle() {
        return title;
    }
}
