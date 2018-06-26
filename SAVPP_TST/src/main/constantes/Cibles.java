package main.constantes;

import beans.CibleBean;
import constantes.Clefs;

public class Cibles {

	////// EXEMPLE SUR GOOGLE //////
	/**
	 * Champ de saisie pour la recherche google.
	 */
	public static final CibleBean SAISIE_RECHERCHE = new CibleBean(Clefs.XPATH, "//input[@type!='hidden' and @type != 'submit'][1]");
	
	/**
	 * Bouton de recherche google.
	 */
	public static final CibleBean VALIDER_RECHERCHE = new CibleBean(Clefs.VALEUR, "Recherche Google");
	
	//////EXEMPLE SUR SAMY //////
	/**
	 * Selecteur saisie de la civilité.
	 */
	public static final CibleBean SELECTION_CIVILITE_SAMY = new CibleBean(Clefs.ID, "field_situationFamiliale");
	/**
	 * Selecteur saisie du prenom.
	 */
	public static final CibleBean SELECTION_PRENOM_SAMY = new CibleBean(Clefs.ID, "field_prenom");
	/**
	 * Selecteur saisie du nom.
	 */
	public static final CibleBean SELECTION_NOM_SAMY = new CibleBean(Clefs.ID, "field_nom");
	/**
	 * Selecteur saisie du montant epargne.
	 */
	public static final CibleBean SAISIE_MONTANT_EPARGNE = new CibleBean(Clefs.ID, "montantEpargneSalarial");
	/**
	 * Selecteur saisie de la date de disponibilite epargne.
	 */
	public static final CibleBean SAISIE_DATE_DISPO = new CibleBean(Clefs.ID, "dateDisponibiliteEpargneSalarial");
	/**
	 * Bouton acces samy credit.
	 */
	public static final CibleBean VALIDER_FORMULAIRE_DEMO = new CibleBean(Clefs.VALEUR, "submit");
	/**
	 * Date de naissance
	 */
	public static final CibleBean SELECTION_dateNaissance_SAMY = new CibleBean(Clefs.ID, "field_dateNaissance");
	/**
	 * @mail
	 */
	public static final CibleBean SELECTION_email_SAMY = new CibleBean(Clefs.ID, "field_email");
	/**
	 * portable 
	 */
	public static final CibleBean SELECTION_telPortable_SAMY = new CibleBean(Clefs.ID, "telPortable");
}