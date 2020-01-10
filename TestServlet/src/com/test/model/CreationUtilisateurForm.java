package com.test.model;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.test.dao.interfaces.UtilisateurDao;

public final class CreationUtilisateurForm {
    private static final String CHAMP_NOM       = "nomUtilisateur";
    private static final String CHAMP_EMAIL     = "emailUtilisateur";
    public static final String CHAMP_PASSWORD  = "passwordUtilisateur";
    
    private String              resultat;
    private Map<String, String> erreurs         = new HashMap<String, String>();
    private UtilisateurDao utilisateurDao;

    public CreationUtilisateurForm(UtilisateurDao utilisateurDao) {
		this.utilisateurDao = utilisateurDao;
	}

	public Map<String, String> getErreurs() {
        return erreurs;
    }

    public String getResultat() {
        return resultat;
    }

    public Utilisateur creerUtilisateur( HttpServletRequest request ) {
        String nom = getValeurChamp( request, CHAMP_NOM );
        String email = getValeurChamp( request, CHAMP_EMAIL );
        String password = getValeurChamp (request, CHAMP_PASSWORD);

        Utilisateur user = new Utilisateur();

        user.setNom( nom );
        try {
            validationEmail( email );
        } catch ( Exception e ) {
            setErreur( CHAMP_EMAIL, e.getMessage() );
        }
        user.setEmail( email );
        try {
            validationPassword( password );
        } catch ( Exception e ) {
            setErreur( CHAMP_PASSWORD, e.getMessage() );
        }
        user.setPassword(password);
        
        if ( erreurs.isEmpty() ) {
            resultat = "Succès de la création du client.";
        } else {
            resultat = "Échec de la création du client.";
        }

        return user;
    }

	private void validationPassword(String password) throws Exception {
    	if ( password != null ) {
            if ( password.length() < 2 ) {
                throw new Exception( "Le mot de passe doit contenir au moins 2 caractères." );
            }
        } else {
            throw new Exception( "Merci d'entrer un mot de passe." );
        }
    }

    private void validationEmail( String email ) throws Exception {
        if ( email != null && !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ) ) {
            throw new Exception( "Merci de saisir une adresse mail valide." );
        }
    }

    /*
     * Ajoute un message correspondant au champ spécifié à la map des erreurs.
     */
    public void setErreur( String champ, String message ) {
        erreurs.put( champ, message );
    }

    /*
     * Méthode utilitaire qui retourne null si un champ est vide, et son contenu
     * sinon.
     */
    private static String getValeurChamp( HttpServletRequest request, String nomChamp ) {
        String valeur = request.getParameter( nomChamp );
        if ( valeur == null || valeur.trim().length() == 0 ) {
            return null;
        } else {
            return valeur;
        }
    }
}