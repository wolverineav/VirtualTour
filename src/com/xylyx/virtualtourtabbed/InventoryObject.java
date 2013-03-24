package com.xylyx.virtualtourtabbed;

public class InventoryObject {
	private long id;
	private String objName;
	private String mediaType;
	private String media;
	
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
	public String getMediaType() {
		return mediaType;
	}
	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	
	// will be used by ArrayAdapter
	@Override
	public String toString(){
		return objName;
	}

}
