package ua.nure.pekariev.model;

import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {

	private static final long serialVersionUID = 6969015484458342842L;

	private Long id;
	private Account account;
	private Car car;
	private Date created;
	private Date confirmed;
	private Date starts;
	private Date expires;
	private Long daysAmount;
	private Long totalRentValue;
	private Short status;
	private String statusComment;
	private Boolean withDriver;
	private Boolean paid;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Car getCar() {
		return car;
	}

	public void setCar(Car car) {
		this.car = car;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getConfirmed() {
		return confirmed;
	}

	public void setConfirmed(Date confirmed) {
		this.confirmed = confirmed;
	}

	public Date getStarts() {
		return starts;
	}

	public void setStarts(Date starts) {
		this.starts = starts;
	}

	public Date getExpires() {
		return expires;
	}

	public void setExpires(Date expires) {
		this.expires = expires;
	}

	public Long getDaysAmount() {
		return daysAmount;
	}

	public void setDaysAmount(Long daysAmount) {
		this.daysAmount = daysAmount;
	}

	public Long getTotalRentValue() {
		return totalRentValue;
	}

	public void setTotalRentValue(Long totalRentValue) {
		this.totalRentValue = totalRentValue;
	}

	public Short getStatus() {
		return status;
	}

	public void setStatus(Short status) {
		this.status = status;
	}

	public String getStatusComment() {
		return statusComment;
	}

	public void setStatusComment(String statusComment) {
		this.statusComment = statusComment;
	}

	public Boolean getWithDriver() {
		return withDriver;
	}

	public void setWithDriver(Boolean withDriver) {
		this.withDriver = withDriver;
	}

	public Boolean getPaid() {
		return paid;
	}

	public void setPaid(Boolean paid) {
		this.paid = paid;
	}

}
