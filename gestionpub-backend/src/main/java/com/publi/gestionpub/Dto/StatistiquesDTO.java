package com.publi.gestionpub.Dto;

public class StatistiquesDTO {
	private long total;
    private long valides;
    private long enRevision;
    private long soumis;
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public long getValides() {
		return valides;
	}
	public void setValides(long valides) {
		this.valides = valides;
	}
	public long getEnRevision() {
		return enRevision;
	}
	public void setEnRevision(long enRevision) {
		this.enRevision = enRevision;
	}
	public long getSoumis() {
		return soumis;
	}
	public void setSoumis(long soumis) {
		this.soumis = soumis;
	}
	public StatistiquesDTO(long total, long valides, long enRevision, long soumis) {
		super();
		this.total = total;
		this.valides = valides;
		this.enRevision = enRevision;
		this.soumis = soumis;
	}
    
}
