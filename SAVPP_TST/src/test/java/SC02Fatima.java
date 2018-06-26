package test.java;

import org.junit.Test;

import constantes.Actions;
import exceptions.SeleniumException;
import main.bean.CasEssaiModeleBean;
import main.constantes.Cibles;
import main.constantes.Constantes;
import outils.SeleniumOutils;

public class SC02Fatima extends SC00Modele {
	@Test
	public void accesTest() throws SeleniumException {
		
		//Description du scénario
		CasEssaiModeleBean scenario0 = new CasEssaiModeleBean();
		
		///////////////////////////////////////////////////Configuration////////////////////////////////////////////////
	    SeleniumOutils outil = obtenirDriver(scenario0);		
	    
		///////////////////////////////////////////////////EXECUTION////////////////////////////////////////////////
		String url = Constantes.URL_SAMY_FATIMA;
		String titre = Constantes.TITRE_PAGE_SAMY;

		// Accès à google
		outil.chargerUrl(url);
		
		// Attente de l'affichage du titre de la page
		outil.attendreChargementPage(titre);
		
		// Faire une saisie dans le formulaire de Mock
		outil.action(Actions.SELECTIONNER, Cibles.SELECTION_CIVILITE_SAMY, "HOMME"); 
		outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_NOM_SAMY, "LEMONSIEUR"); 
		outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_PRENOM_SAMY, "KEVIN"); 
		//Saisir la date naissance
		outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_dateNaissance_SAMY, "12/12/1970"); 
		//Saisir le mail
		outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_email_SAMY, "ichiroubh@gmail.com"); 
		//Saisir le numéro de portable 
		outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_telPortable_SAMY, "0610109436"); 
        // Remplir le formulaire et le valider
        outil.action(Actions.VIDER_ET_SAISIR, Cibles.SAISIE_MONTANT_EPARGNE, "5000");
        outil.action(Actions.VIDER_ET_SAISIR, Cibles.SAISIE_DATE_DISPO, "15/10/2018");
        outil.action(Actions.CLIQUER, Cibles.VALIDER_FORMULAIRE_DEMO);
	}
}



