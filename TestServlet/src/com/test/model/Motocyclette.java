package com.test.model;

public class Motocyclette extends Vehicule {
	
	private int puissance;
	
	public Motocyclette(String marque, String modele, int puissance) {
		super("Moto", marque, modele, 2);
		this.setPuissance(puissance);
	}

	public int getPuissance() {
		return puissance;
	}

	public void setPuissance(int puissance) {
		this.puissance = puissance;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Moto[")
			.append(super.toString())
			.append(", puissance=").append(getPuissance())
			.append("]");
		return builder.toString();
	}

}
