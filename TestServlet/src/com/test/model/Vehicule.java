package com.test.model;

public class Vehicule {
	
	private long id;

	private String immatriculation;
	
	private String marque;
	
	private String modele;
	
	private int nbreRoues;
	
	private String typeVehicule;
	
	public Vehicule() {
		
	}
	
	public Vehicule(String typeVehicule, String marque, String modele, int nbreRoues) {
		this.typeVehicule = typeVehicule;
		this.setMarque(marque);
		this.setModele(modele);
		this.setNbreRoues(nbreRoues);
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMarque() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque = marque;
	}

	public String getModele() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele = modele;
	}

	public int getNbreRoues() {
		return nbreRoues;
	}

	public void setNbreRoues(int nbreRoues) {
		this.nbreRoues = nbreRoues;
	}
	
	
	public String getImmatriculation() {
		return immatriculation;
	}

	public void setImmatriculation(String immatriculation) {
		this.immatriculation = immatriculation;
	}
	
	

	public String getTypeVehicule() {
		return typeVehicule;
	}

	public void setTypeVehicule(String typeVehicule) {
		this.typeVehicule = typeVehicule;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("marque=").append(getMarque())
			.append(", modele=").append(getModele())
			.append(", roues=").append(getNbreRoues());
		return builder.toString();
	}

}
