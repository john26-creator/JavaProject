package com.test.servlet;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

public class Utilitaire {

	private static final String ALGO_CHIFFREMENT = "SHA-256";

	public static String encryptPassword(String password) {
		/*
		 * Utilisation de la bibliothèque Jasypt pour chiffrer le mot de passe
		 * efficacement.
		 * 
		 * L'algorithme SHA-256 est ici utilisé, avec par défaut un salage
		 * aléatoire et un grand nombre d'itérations de la fonction de hashage.
		 * 
		 * La String retournée est de longueur 56 et contient le hash en Base64.
		 */
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
		passwordEncryptor.setPlainDigest( false );
		return passwordEncryptor.encryptPassword(password);
	}

	public static ConfigurablePasswordEncryptor getEncryptor () {
		ConfigurablePasswordEncryptor passwordEncryptor = new ConfigurablePasswordEncryptor();
		passwordEncryptor.setAlgorithm( ALGO_CHIFFREMENT );
		passwordEncryptor.setPlainDigest( false );
		return passwordEncryptor;
	}

}
