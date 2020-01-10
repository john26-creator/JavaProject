package com.test.dao.interfaces;

import java.util.Map;

import com.test.dao.DaoException;
import com.test.model.Vehicule;

public interface VehiculeDao {
	Map <String,Vehicule> trouverVehicules() throws DaoException;
	void ajouterVehicule (Vehicule v) throws DaoException;
	void supprimerVehicule (String immat) throws DaoException;
}
