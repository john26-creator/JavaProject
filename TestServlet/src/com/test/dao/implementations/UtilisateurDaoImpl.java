package com.test.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.test.dao.DaoException;
import com.test.dao.DaoFactory;
import com.test.dao.DaoUtilitaire;
import com.test.dao.interfaces.UtilisateurDao;
import com.test.model.Utilisateur;

public class UtilisateurDaoImpl implements UtilisateurDao {

	private DaoFactory daoFactory;

	private static final String SQL_SELECT_PAR_EMAIL = "SELECT * FROM public.\"USER\" WHERE email = ?";
	private static final String SQL_SELECT_PAR_ID = "SELECT * FROM public.\"USER\" WHERE id_user= ?";
	private static final String SQL_INSERT = "INSERT INTO public.\"USER\" (nom, email, pass) VALUES (?, ?, ?)";

	public UtilisateurDaoImpl(DaoFactory daoFactory) {
		this.daoFactory = daoFactory;
	}

	@Override
	public void creer(Utilisateur utilisateur) throws DaoException {
		Connection connexion = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet valeursAutoGenerees = null;

	    try {
	        /* R�cup�ration d'une connexion depuis la Factory */
	        connexion = daoFactory.getConnection();
	        preparedStatement = DaoUtilitaire.initialisationRequetePreparee( connexion, SQL_INSERT, true, utilisateur.getNom(), utilisateur.getEmail(), utilisateur.getPassword());
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourn� par la requ�te d'insertion */
	        if ( statut == 0 ) {
	            throw new DaoException( "�chec de la cr�ation de l'utilisateur, aucune ligne ajout�e dans la table." );
	        }
	        /* R�cup�ration de l'id auto-g�n�r� par la requ�te d'insertion */
	        valeursAutoGenerees = preparedStatement.getGeneratedKeys();
	        if ( valeursAutoGenerees.next() ) {
	            /* Puis initialisation de la propri�t� id du bean Utilisateur avec sa valeur */
	            utilisateur.setId( valeursAutoGenerees.getLong( 1 ) );
	        } else {
	            throw new DaoException( "�chec de la cr�ation de l'utilisateur en base, aucun ID auto-g�n�r� retourn�." );
	        }
	    } catch ( SQLException e ) {
	        throw new DaoException( e );
	    } finally {
	    	DaoUtilitaire.fermeturesSilencieuses( valeursAutoGenerees, preparedStatement, connexion );
	    }
	}

	@Override
	public Utilisateur trouver(String email) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur user = null;

		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = DaoUtilitaire.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_EMAIL, false, email );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			if ( resultSet.next() ) {
				user = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DaoException( e );
		} finally {
			DaoUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}

		return user;
	}
	
	/*
	 * Simple m�thode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Utilisateur map( ResultSet resultSet ) throws SQLException {
	    Utilisateur utilisateur = new Utilisateur();
	    utilisateur.setId( Long.parseLong(resultSet.getString( "id_user" )) );
	    utilisateur.setEmail( resultSet.getString( "email" ) );
	    utilisateur.setPassword( resultSet.getString( "pass" ) );
	    utilisateur.setNom( resultSet.getString( "nom" ) );
	    return utilisateur;
	}

	@Override
	public Utilisateur trouver(long id) throws DaoException {
		Connection connexion = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Utilisateur user = null;

		try {
			/* R�cup�ration d'une connexion depuis la Factory */
			connexion = daoFactory.getConnection();
			preparedStatement = DaoUtilitaire.initialisationRequetePreparee( connexion, SQL_SELECT_PAR_ID, false, id );
			resultSet = preparedStatement.executeQuery();
			/* Parcours de la ligne de donn�es de l'�ventuel ResulSet retourn� */
			if ( resultSet.next() ) {
				user = map( resultSet );
			}
		} catch ( SQLException e ) {
			throw new DaoException( e );
		} finally {
			DaoUtilitaire.fermeturesSilencieuses( resultSet, preparedStatement, connexion );
		}

		return user;
	}
}
