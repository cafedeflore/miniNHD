package com.cafedeflore.libNHD.data;

import java.io.Serializable;

public class TorrentEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type = 0;
	private String title = "";
	private String secondTitle = "";
	private boolean isNew = false;
	private int commandsNumber = 0;
	private String time = "";
	private String size = "";
	private int torrentNumber = 0;
	private int downloadNumber = 0;
	private int completeNumber = 0;
	private String uploader = "";
	
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the secondTitle
	 */
	public String getSecondTitle() {
		return secondTitle;
	}
	/**
	 * @param secondTitle the secondTitle to set
	 */
	public void setSecondTitle(String secondTitle) {
		this.secondTitle = secondTitle;
	}
	/**
	 * @return the isNew
	 */
	public boolean isNew() {
		return isNew;
	}
	/**
	 * @param isNew the isNew to set
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	/**
	 * @return the commandsNumber
	 */
	public int getCommandsNumber() {
		return commandsNumber;
	}
	/**
	 * @param commandsNumber the commandsNumber to set
	 */
	public void setCommandsNumber(int commandsNumber) {
		this.commandsNumber = commandsNumber;
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}
	/**
	 * @return the torrentNumber
	 */
	public int getTorrentNumber() {
		return torrentNumber;
	}
	/**
	 * @param torrentNumber the torrentNumber to set
	 */
	public void setTorrentNumber(int torrentNumber) {
		this.torrentNumber = torrentNumber;
	}
	/**
	 * @return the downloadNumber
	 */
	public int getDownloadNumber() {
		return downloadNumber;
	}
	/**
	 * @param downloadNumber the downloadNumber to set
	 */
	public void setDownloadNumber(int downloadNumber) {
		this.downloadNumber = downloadNumber;
	}
	/**
	 * @return the completeNumber
	 */
	public int getCompleteNumber() {
		return completeNumber;
	}
	/**
	 * @param completeNumber the completeNumber to set
	 */
	public void setCompleteNumber(int completeNumber) {
		this.completeNumber = completeNumber;
	}
	/**
	 * @return the uploader
	 */
	public String getUploader() {
		return uploader;
	}
	/**
	 * @param uploader the uploader to set
	 */
	public void setUploader(String uploader) {
		this.uploader = uploader;
	}
}
