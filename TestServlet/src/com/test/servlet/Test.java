package com.test.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.test.dao.DaoFactory;
import com.test.dao.interfaces.UtilisateurDao;
import com.test.dao.interfaces.VehiculeDao;
import com.test.model.Motocyclette;
import com.test.model.Vehicule;
import com.test.model.Voiture;

public class Test extends HttpServlet {

	public static final String CONF_DAO_FACTORY = "daofactory";
	private VehiculeDao vehiculeDao = null;
	private UtilisateurDao utilisateurDao = null;

	public void init() throws ServletException {
		this.vehiculeDao = ((DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getVehiculeDao();
		this.utilisateurDao = ((DaoFactory) getServletContext().getAttribute( CONF_DAO_FACTORY ) ).getUtilisateurDao();
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException{

		HttpSession session = request.getSession();
		if ( session.getAttribute( "user" ) == null ) {
			/* Redirection vers la page publique */
			response.sendRedirect("/TestServlet/connexionForm.jsp");
			return;
		} else {
			Map <String,Vehicule> vehicules = vehiculeDao.trouverVehicules();
			String nomUtilisateur = utilisateurDao.trouver((long) session.getAttribute( "user" )).getNom();
			String servletPath = request.getRequestURL().toString();
			if(servletPath.contains("remove")) {
				String immatToRemove = request.getParameter("immat");
				vehicules.remove(immatToRemove);
				vehiculeDao.supprimerVehicule(immatToRemove);
				response.sendRedirect("/TestServlet/toto");
			}
			else {

				String message = "Bonjour";
				if (vehicules.isEmpty()) {
					vehicules = vehiculeDao.trouverVehicules();
				}

				request.setAttribute("message", message + " " + nomUtilisateur);
				request.setAttribute("vehicules", vehicules);
				this.getServletContext().getRequestDispatcher("/restreint/vehicules.jsp").forward(request, response);
			}
		}
	}

@Override
public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	String immatriculation = req.getParameter("immatriculation");
	String marque = req.getParameter("marque");
	String modele = req.getParameter("modele");
	String couleur = req.getParameter("couleur");
	String puissance = req.getParameter("puissance");
	String annee = req.getParameter("annee");
	String vehicule = req.getParameter("typeVehicule");

	Map<String, Vehicule> vehicules = null;
	vehicules = vehiculeDao.trouverVehicules();

	if("Voiture".equals(vehicule)) {//c'est une voiture
		Voiture v = new Voiture(marque, modele, Integer.parseInt(annee), couleur);
		v.setImmatriculation(immatriculation);
		vehicules.put(v.getImmatriculation(),v);
		vehiculeDao.ajouterVehicule(v);
	}
	else {
		Motocyclette m = new Motocyclette(marque, modele, Integer.parseInt(puissance));
		m.setImmatriculation(immatriculation);
		vehicules.put(m.getImmatriculation(),m);
		vehiculeDao.ajouterVehicule(m);
	}
	req.setAttribute("vehicules", vehicules);
	this.getServletContext().getRequestDispatcher("/restreint/vehicules.jsp").forward(req, resp);
}

}
