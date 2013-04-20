package com.xylyx.virtualtourtabbed.Objects;

public class SiteObject {
	private long id;
	private String siteName;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	
	// will be used by ArrayAdapter
	@Override
	public String toString(){
		return siteName;
	}	
}
