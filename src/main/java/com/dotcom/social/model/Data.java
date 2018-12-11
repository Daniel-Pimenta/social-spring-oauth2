package com.dotcom.social.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "height", "is_silhouette", "url", "width" })
public class Data {

	@JsonProperty("height")
	private Integer height;
	@JsonProperty("is_silhouette")
	private Boolean isSilhouette;
	@JsonProperty("url")
	private String url;
	@JsonProperty("width")
	private Integer width;

	@JsonProperty("height")
	public Integer getHeight() {
		return height;
	}

	@JsonProperty("height")
	public void setHeight(Integer height) {
		this.height = height;
	}

	@JsonProperty("is_silhouette")
	public Boolean getIsSilhouette() {
		return isSilhouette;
	}

	@JsonProperty("is_silhouette")
	public void setIsSilhouette(Boolean isSilhouette) {
		this.isSilhouette = isSilhouette;
	}

	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	@JsonProperty("width")
	public Integer getWidth() {
		return width;
	}

	@JsonProperty("width")
	public void setWidth(Integer width) {
		this.width = width;
	}

}