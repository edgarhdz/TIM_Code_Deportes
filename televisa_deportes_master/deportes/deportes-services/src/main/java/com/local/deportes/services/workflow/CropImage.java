package com.local.deportes.services.workflow;

/**
 * Note Management Data Access
 *
 * Business Object that contain the basic attributes of the crop image
 *
 * Changes History:
 *
 *         2013-01-25 Initial Development
 *
 * @author lsztul@xumak.com
 * @version 1.0
 */
public class CropImage {

	public Integer width;
	public Integer height;
	public String typeImage;
	
	public CropImage() {
	}
	
	public CropImage(Integer width, Integer height) {
		super();
		this.width = width;
		this.height = height;
	}

	public CropImage(Integer width, Integer height, String typeImage) {
		super();
		this.width = width;
		this.height = height;
		this.typeImage = typeImage;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getTypeImage() {
		return typeImage;
	}
	
	public void setTypeImage(String typeImage) {
		this.typeImage = typeImage;
	}
}
