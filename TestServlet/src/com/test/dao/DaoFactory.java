package com.test.dao;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;
import com.test.dao.implementations.UtilisateurDaoImpl;
import com.test.dao.implementations.VehiculeDaoImpl;
import com.test.dao.interfaces.UtilisateurDao;
import com.test.dao.interfaces.VehiculeDao;

public class DaoFactory {

	private static final String FICHIER_PROPERTIES       = "/com/test/config/config.properties";
	private static final String PROPERTY_URL             = "url";
	private static final String PROPERTY_DRIVER          = "driver";
	private static final String PROPERTY_NOM_UTILISATEUR = "nomutilisateur";
	private static final String PROPERTY_MOT_DE_PASSE    = "motdepasse";


	/* package */
	static BoneCP connexionPool = null;

	DaoFactory(BoneCP connectionPool) {
		this.connexionPool = connectionPool;
	}


	/*
	 * M�thode charg�e de r�cup�rer les informations de connexion � la base de
	 * donn�es, charger le driver JDBC et retourner une instance de la Factory
	 */
	public static DaoFactory getInstance() throws DaoConfigurationException {
		Properties properties = new Properties();
		String url;
		String driver;
		String nomUtilisateur;
		String motDePasse;

		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		InputStream fichierProperties = classLoader.getResourceAsStream( FICHIER_PROPERTIES );

		if ( fichierProperties == null ) {
			throw new DaoConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable." );
		}

		try {
			properties.load( fichierProperties );
			url = properties.getProperty( PROPERTY_URL );
			driver = properties.getProperty( PROPERTY_DRIVER );
			nomUtilisateur = properties.getProperty( PROPERTY_NOM_UTILISATEUR );
			motDePasse = properties.getProperty( PROPERTY_MOT_DE_PASSE );

		} catch ( FileNotFoundException e ) {
			throw new DaoConfigurationException( "Le fichier properties " + FICHIER_PROPERTIES + " est introuvable.", e );
		} catch ( IOException e ) {
			throw new DaoConfigurationException( "Impossible de charger le fichier properties " + FICHIER_PROPERTIES, e );
		}

		try {
			Class.forName( driver );
		} catch ( ClassNotFoundException e ) {
			throw new DaoConfigurationException( "Le driver est introuvable dans le classpath.", e );
		}

		try {
			/*
			 * Cr�ation d'une configuration de pool de connexions via l'objet
			 * BoneCPConfig et les diff�rents setters associ�s.
			 */
			BoneCPConfig config = new BoneCPConfig();
			/* Mise en place de l'URL, du nom et du mot de passe */
			config.setJdbcUrl( url );
			config.setUsername( nomUtilisateur );
			config.setPassword( motDePasse );
			/* Param�trage de la taille du pool */
			config.setMinConnectionsPerPartition( 5 );
			config.setMaxConnectionsPerPartition( 10 );
			config.setPartitionCount( 2 );
			/* Cr�ation du pool � partir de la configuration, via l'objet BoneCP */
			connexionPool = new BoneCP( config );
		} catch ( SQLException e ) {
			e.printStackTrace();
			throw new DaoConfigurationException( "Erreur de configuration du pool de connexions.", e );
		}
		/*
		 * Enregistrement du pool cr�� dans une variable d'instance via un appel
		 * au constructeur de DAOFactory
		 */
		DaoFactory instance = new DaoFactory( connexionPool );
		return instance;
	}

	public Connection getConnection() throws SQLException {
        return connexionPool.getConnection();
    }

	/*
	 * M�thodes de r�cup�ration de l'impl�mentation des diff�rents DAO (un seul
	 * pour le moment)
	 */
	public UtilisateurDao getUtilisateurDao() {
		return new UtilisateurDaoImpl( this );
	}

	public VehiculeDao getVehiculeDao() {
		return new VehiculeDaoImpl( this );
	}
}
