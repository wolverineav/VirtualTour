package com.xylyx.virtualtourtabbed.Objects;

public class InventoryObject {
	private long id;
	private String objName;
	private int mediaType;
	private String media;
	private long siteId;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getObjName() {
		return objName;
	}
	public void setObjName(String objName) {
		this.objName = objName;
	}
	public int getMediaType() {
		return mediaType;
	}
	public void setMediaType(int mediaType) {
		this.mediaType = mediaType;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	
	public long getSiteId() {
		return siteId;
	}
	public void setSiteId(long siteId) {
		this.siteId = siteId;
	}
	// will be used by ArrayAdapter
	@Override
	public String toString(){
		return objName;
	}

}
