package com.codepath.gridimagesearch.models;

import java.io.Serializable;

public class ImageFilter implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4685235053211725572L;
	private String imgSize;
	private String imgColor;
	private String imgType;
	private String site;

	public String getImgSize() {
		return imgSize;
	}

	public void setImgSize(String imgSize) {
		this.imgSize = imgSize;
	}

	public String getImgColor() {
		return imgColor;
	}

	public void setImgColor(String imgColor) {
		this.imgColor = imgColor;
	}

	public String getImgType() {
		return imgType;
	}

	public void setImgType(String imgType) {
		this.imgType = imgType;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}
}
