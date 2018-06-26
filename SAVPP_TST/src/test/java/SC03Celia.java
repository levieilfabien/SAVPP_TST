package test.java;

import java.io.File;

import org.junit.Test;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;

import constantes.Actions;
import exceptions.SeleniumException;
import main.bean.CasEssaiModeleBean;
import main.constantes.Cibles;
import main.constantes.Constantes;
import moteurs.FirefoxImpl;
import moteurs.GenericDriver;
import outils.SeleniumOutils;

public class SC03Celia extends SC00Modele {

       /**
       * Id de serialisation.
       */
       private static final long serialVersionUID = 1L;

       @Test
       public void accesTest() throws SeleniumException {
              
              //Description du scénario
              CasEssaiModeleBean scenario0 = new CasEssaiModeleBean();
              
              ///////////////////////////////////////////////////Configuration////////////////////////////////////////////////
           SeleniumOutils outil = obtenirDriver(scenario0);          
           
              ///////////////////////////////////////////////////EXECUTION////////////////////////////////////////////////
              String url = Constantes.CELIA_SAMY;
              String titre = Constantes.FABIEN_TITRE_SAMY;

              // Accès à google
              outil.chargerUrl(url);
              
              // Attente de l'affichage du titre de la page
              outil.attendreChargementPage(titre);
              
              	// Remplir l identite 
      			outil.action(Actions.SELECTIONNER, Cibles.SELECTION_CIVILITE_SAMY, "HOMME"); 
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_NOM_SAMY, "LEMONSIEUR"); 
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_PRENOM_SAMY, "KEVIN"); 
      			//Saisir la date naissance
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_dateNaissance_SAMY, "12/12/1970"); 
      			//Saisir le mail
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_email_SAMY, "ichiroubh@gmail.com"); 
      			//Saisir le numéro de portable 
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SELECTION_telPortable_SAMY, "0610109436");
      			//Saisir le montant 
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SAISIE_MONTANT_EPARGNE, "5000");
      			//Saisir la date de disponibilite
      			outil.action(Actions.VIDER_ET_SAISIR, Cibles.SAISIE_DATE_DISPO, "15/10/2018");
      			//Valider le formulaire
      			outil.action(Actions.CLIQUER, Cibles.VALIDER_FORMULAIRE_DEMO);

       }
    
}
