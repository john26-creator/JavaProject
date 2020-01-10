package com.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.password.ConfigurablePasswordEncryptor;

import com.test.dao.DaoFactory;
import com.test.dao.interfaces.UtilisateurDao;
import com.test.model.CreationUtilisateurForm;
import com.test.model.Utilisateur;


public class UtilisateurServlet extends HttpServlet {

	public static final String VUE_CREATION_USER_FORM = "/formulaireUtilisateur.jsp";
	public static final String VUE_CONNEXION = "/connexionForm.jsp";
	
	public static final String CONF_DAO_FACTORY = "daofactory";
	private UtilisateurDao utilisateurDao = null;

	public void init() throws ServletException {
		this.utilisateurDao = ((DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_CREATION_USER_FORM).forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		/* Préparation de l'objet formulaire */
		CreationUtilisateurForm form = new CreationUtilisateurForm(utilisateurDao);

		/* Traitement de la requête et récupération du bean en résultant */
		String nom = req.getParameter("name");
		String email = req.getParameter("emailUtilisateur");
		String password = req.getParameter("passwordUtilisateur");
		String passwordEncrypted = Utilitaire.encryptPassword(password);
		
		Utilisateur user = new Utilisateur(nom,email,passwordEncrypted);
		utilisateurDao.creer(user);

		this.getServletContext().getRequestDispatcher(VUE_CREATION_USER_FORM).forward(req, resp);
	}


}
