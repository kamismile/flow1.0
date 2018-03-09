package com.shziyuan.flow.global.domain.flow;
import java.sql.Timestamp;
public class InfoAccountDistributorCertificate {
	private int id;
	private Timestamp insert_time;
	private int pending_id;
	private int distributor_id;
	private String filename;
	private String sourcename;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getInsert_time() {
		return insert_time;
	}
	public void setInsert_time(Timestamp insert_time) {
		this.insert_time = insert_time;
	}
	public int getPending_id() {
		return pending_id;
	}
	public void setPending_id(int pending_id) {
		this.pending_id = pending_id;
	}
	public int getDistributor_id() {
		return distributor_id;
	}
	public void setDistributor_id(int distributor_id) {
		this.distributor_id = distributor_id;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSourcename() {
		return sourcename;
	}
	public void setSourcename(String sourcename) {
		this.sourcename = sourcename;
	}
}