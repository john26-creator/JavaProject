package com.test.model;

public class Utilisateur {
	
	private long id;
    private String nom;
    private String email;
	private String password;

    public Utilisateur(String nom, String email, String password) {
    	this.nom = nom;
		this.email = email;
		this.password = password;
	}

	public Utilisateur() {}

	public long getId() {
		return id;
	}

	public void setId(long l) {
		this.id = l;
	}

	public void setNom( String nom ) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setEmail( String email ) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}
	
}
