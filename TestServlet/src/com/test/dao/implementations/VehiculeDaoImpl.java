package com.test.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.test.dao.DaoException;
import com.test.dao.DaoFactory;
import com.test.dao.DaoUtilitaire;
import com.test.dao.interfaces.VehiculeDao;
import com.test.model.Motocyclette;
import com.test.model.Vehicule;
import com.test.model.Voiture;

public class VehiculeDaoImpl implements VehiculeDao {

	private DaoFactory daoFactory;

	private static final String SQL_SELECT_ALL = "SELECT * FROM public.\"Vehicule\";";
	private static final String SQL_INSERT_VOITURE = "INSERT INTO public.\"Vehicule\" " 
			+ "(immatriculation, marque, model, nb_roues, couleur, annee, type_vehicule) " 
			+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
	private static final String SQL_INSERT_MOTO = "INSERT INTO public.\"Vehicule\" "
			+ "(immatriculation, marque, model, nb_roues, puissance, type_vehicule)"
			+ "VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SQL_DELETE = "DELETE FROM public.\"Vehicule\" WHERE immatriculation = ?;";

	public VehiculeDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public Map<String, Vehicule> trouverVehicules() throws  DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;

		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement (SQL_SELECT_ALL);
			results = preparedStatement.executeQuery();
			Map <String,Vehicule> vehicules = new HashMap <String,Vehicule> ();
			while (results.next()) {
				Vehicule v;
				if ("voiture".equals(results.getString("type_vehicule"))){
					v = new Voiture (results.getString("marque"), results.getString("model"), results.getInt("annee"), results.getString("couleur"));
				} else {
					v = new Motocyclette(results.getString("marque"), results.getString("model"), results.getInt("puissance"));
				}
				v.setImmatriculation(results.getString("immatriculation"));
				vehicules.put(v.getImmatriculation(),v);
			}
			return vehicules;
		} catch (SQLException e) {
			throw new DaoException( e );
		} finally {
			DaoUtilitaire.fermeturesSilencieuses(results, preparedStatement, connexion );
		}
	}

	@Override
	public void ajouterVehicule(Vehicule v) throws DaoException{
		Connection connexion = null;
		PreparedStatement p = null;
		ResultSet valeursAutoGenerees = null;
		
		try {
			connexion = daoFactory.getConnection();
			if (v instanceof Voiture) {
				p = connexion.prepareStatement( SQL_INSERT_VOITURE ,Statement.RETURN_GENERATED_KEYS);
				p.setString(1, v.getImmatriculation());
				p.setString(2, v.getMarque());
				p.setString(3, v.getModele());
				p.setInt(4, v.getNbreRoues());
				p.setString(5, ((Voiture) v).getCouleur());
				p.setInt(6, ((Voiture) v).getAnnee());
				p.setString(7, v.getTypeVehicule());
			}else {
				p = connexion.prepareStatement( SQL_INSERT_MOTO, Statement.RETURN_GENERATED_KEYS);
				p.setString(1, v.getImmatriculation());
				p.setString(2, v.getMarque());
				p.setString(3, v.getModele());
				p.setInt(4, v.getNbreRoues());
				p.setInt(5, ((Motocyclette) v).getPuissance());
				p.setString(6, v.getTypeVehicule());
			}
			int statut = p.executeUpdate();
			
			/* Analyse du statut retourné par la requête d'insertion */
			if ( statut == 0 ) {
				throw new DaoException( "Échec de la création du véhicule, aucune ligne ajoutée dans la table." );
			} else {
				valeursAutoGenerees = p.getGeneratedKeys();
				if ( valeursAutoGenerees.next() ) {
					/* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
					v.setId( valeursAutoGenerees.getLong( "id_vehicule" ) );
				} else {
					throw new DaoException( "Échec de la création du véhicule en base, aucun ID auto-généré retourné." );
				}
			}
		} catch (SQLException e) {
			throw new DaoException( e );
		} finally {
			DaoUtilitaire.fermeturesSilencieuses(valeursAutoGenerees, p, connexion );
		}
	}

	@Override
	public void supprimerVehicule(String immat) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		try {
			connexion = daoFactory.getConnection();
			preparedStatement = connexion.prepareStatement( SQL_DELETE );
			preparedStatement.setString(1, immat);
			int statut = preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException( e );
		} finally {
			DaoUtilitaire.fermeturesSilencieuses(preparedStatement, connexion );
		}
	}

}
