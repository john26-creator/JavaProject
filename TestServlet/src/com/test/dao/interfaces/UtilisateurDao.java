package com.test.dao.interfaces;

import com.test.dao.DaoException;
import com.test.model.Utilisateur;

public interface UtilisateurDao {

	void creer( Utilisateur utilisateur ) throws DaoException;

    Utilisateur trouver( String email ) throws DaoException;
    
    Utilisateur trouver( long id ) throws DaoException;

}
