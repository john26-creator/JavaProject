package com.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.dao.DaoFactory;
import com.test.dao.interfaces.UtilisateurDao;
import com.test.model.CreationUtilisateurForm;
import com.test.model.Utilisateur;

public class FormServlet extends HttpServlet{
	public static final String ATT_CLIENT = "user";
	public static final String ATT_FORM   = "form";

	public static final String CONF_DAO_FACTORY = "daofactory";
	public static final String VUE_SUCCES = "/vehicules.jsp";
	public static final String VUE_FORM   = "/connexionForm.jsp";

	private UtilisateurDao utilisateurDao = null;
	
	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		/* À la réception d'une requête GET, simple affichage du formulaire */
		this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
	}

	public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {
		
		/* Préparation de l'objet formulaire */
		CreationUtilisateurForm form = new CreationUtilisateurForm(utilisateurDao);

		/* Traitement de la requête et récupération du bean en résultant */
		Utilisateur user = form.creerUtilisateur( request );

		/* Ajout du bean et de l'objet métier à l'objet requête */
		request.setAttribute( ATT_CLIENT, user );
		request.setAttribute( ATT_FORM, form );

		if ( form.getErreurs().isEmpty() ) {
			Utilisateur u = utilisateurDao.trouver(user.getEmail());
				//Si il y a un résultat c'est qu'il est présent
				if (u != null && Utilitaire.getEncryptor().checkPassword(user.getPassword(), u.getPassword())) {
					//Je créé donc une session qui donnera accès à l'espace personnel de l'utilisateur
					setUserSession(request, u);
					//Je créé des coockies pour que les champs du formulaire de connexion soient préremplis lors d'un prochaine connexion
					setUserCookie(response, user);
					//Je redirige vers l'espace personnel de l'utilisateur
					response.sendRedirect("/TestServlet/toto");
				}
				else {
					/* Sinon, ré-affichage du formulaire avec message d'erreur identifiants incorrects*/
					form.setErreur(CreationUtilisateurForm.CHAMP_PASSWORD, "Identifiants Incorrects");
					this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
				}

		} else {
			/* Sinon, ré-affichage du formulaire de création avec les erreurs */
			this.getServletContext().getRequestDispatcher( VUE_FORM ).forward( request, response );
		}
	}

	private void setUserSession (HttpServletRequest request, Utilisateur user) {
		HttpSession session = request.getSession();
		session.setAttribute( "user", user.getId());
	}

	private void setUserCookie (HttpServletResponse response, Utilisateur user) {
		Cookie nom = new Cookie("nom", user.getNom());
		Cookie adresseMail = new Cookie("mail", user.getEmail());
		response.addCookie(nom);
		response.addCookie(adresseMail);
	}

}

