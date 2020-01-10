package com.test.model;

public class Voiture extends Vehicule {

	private int annee;
	
	private String couleur;
	
	public Voiture(String marque, String modele, int annee, String couleur) {
		super("Voiture", marque, modele, 4);
		this.setAnnee(annee);
		this.setCouleur(couleur);
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public String getCouleur() {
		return couleur;
	}

	public void setCouleur(String couleur) {
		this.couleur = couleur;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Voiture[")
			.append(super.toString())
			.append(", annee=").append(getAnnee())
			.append(", couleur=").append(getCouleur())
			.append("]");
		return builder.toString();
	}
}
