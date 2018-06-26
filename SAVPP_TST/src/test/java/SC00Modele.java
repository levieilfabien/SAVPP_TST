package test.java;

import java.io.File;

import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;

import beans.CasEssaiBean;
import exceptions.SeleniumException;
import main.bean.CasEssaiModeleBean;
import main.constantes.Constantes;
import moteurs.FirefoxImpl;
import moteurs.GenericDriver;
import outils.ALMOutils;
import outils.SeleniumOutils;
import outils.XLSOutils;

public class SC00Modele extends CasEssaiBean {
	
	/**
	 * Id de serialisation.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Créer le repertoire de téléchargement pour le cas d'essai.
	 * @param profile le profil firefox utilisé
	 * @param casEssai le cas d'essai
	 * @return le chemin du repertoire.
	 */
	public String creerRepertoireTelechargement(CasEssaiBean casEssai, FirefoxProfile profile) {
		File repertoireTelechargement = new File(".\\" + casEssai.getNomCasEssai());
		repertoireTelechargement.mkdir();
		profile.setPreference(Constantes.PREF_FIREFOX_REPERTOIRE_TELECHARGEMENT, repertoireTelechargement.getAbsolutePath());
		return repertoireTelechargement.getAbsolutePath();
	}
	
	/**
	 * Configuration du profil pour test.
	 * @return Le profil utiliser sur le modèle du profil "Automate"
	 */
	public static FirefoxProfile configurerProfilNatixis() {
		
		// Initialisation du profil avec le profil automate requis
		ProfilesIni profileIni = new ProfilesIni();
		//FirefoxProfile profile = profileIni.getProfile("Automate");
		FirefoxProfile profile = new FirefoxProfile(new File(Constantes.EMPLACEMENT_PROFIL));
		
		profile.setPreference("app.update.enabled", Boolean.FALSE);
		profile.setPreference("network.negotiate-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		profile.setPreference("network.automatic-ntlm-auth.trusted-uris", "https://open-workplace.intranatixis.com/nfi/front-middle/wiki-izivente/Rfrentiel/Liens%20Izivente.aspx");
		
		profile.setPreference("browser.download.pluginOverrideTypes", "");
		profile.setPreference("plugin.disable_full_page_plugin_for_types", "application/pdf,application/vnd.fdf,application/vnd.adobe.xfdf,application/vnd.adobe.xdp+xml");
		
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		profile.setPreference("pdfjs.firstRun", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.alwaysAskBeforeHandling", Boolean.FALSE);
		profile.setPreference("pdfjs.previousHandler.preferredAction", 4);
		profile.setPreference("pdfjs.disabled", Boolean.TRUE);
		
		profile.setPreference("browser.download.useDownloadDir", Boolean.TRUE);
		profile.setPreference("browser.download.manager.focusWhenStarting", Boolean.FALSE);
		profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", new File(".\\").getAbsolutePath());
        
        //browser.download.panel.shown
        profile.setPreference("browser.helperApps.alwaysAsk.force", Boolean.FALSE);
        profile.setPreference("browser.download.manager.showWhenStarting", Boolean.FALSE);
        profile.setPreference("browser.download.manager.useWindow", Boolean.FALSE);
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf,text/pdf,application/octet-stream,application/x-pdf,text/plain,text/xml");

		return profile;
	}
	
	/**
	 * Permet de finaliser le cas d'essai en erreur.
	 * @param outil la boite à outil
	 * @param casEssai le cas d'essai
	 * @param ex l'exception générant l'erreur
	 * @throws SeleniumException en cas d'erreur.
	 */
	public final void finaliserTestEnErreur(final SeleniumOutils outil, final CasEssaiBean casEssai, final SeleniumException ex, final String idObjectif) throws SeleniumException {
		ex.printStackTrace();
		casEssai.setCommentaire(ex.toString());
		logger(ex.toString());
		finaliserTest(outil, casEssai, idObjectif, false);
	}

	/**
	 * Finalise l'execution d'un test en renseignant les log du cas d'essai et du test à  partir des
	 * logs du driver.
	 * 
	 * @param outils le driver.
	 * @param casEssai le cas d'essai concerné par le test.
	 * @throws SeleniumException en cas d'erreur lors de la génération du fichier excel de rapport.
	 */
	private void finaliserTest(SeleniumOutils outils, CasEssaiBean casEssai, final String idObjectif, boolean succes) throws SeleniumException {
		// On finalise aussi les sous cas.
		for(CasEssaiBean sousCas : casEssai.getTests()) {
			finaliserTest(outils, sousCas, casEssai.getNomCasEssai() + casEssai.getTime(), sousCas.getEtatFinal());
		}
		// Si le driver n'est pas nul on effectue des capture d'écran et on récupère les logs.
		if (outils != null) {
			casEssai.setRegistreExecution(outils.getDriver());
			logger(casEssai.getRegistreExecution() + "\n" + casEssai.toString());
			if (casEssai.getRepertoireTelechargement() != null) {
				outils.captureEcran("captureFinale" + casEssai.getNomCasEssai(), casEssai.getRepertoireTelechargement());
			} else {
				outils.captureEcran("captureFinale" + casEssai.getNomCasEssai(), casEssai.getNomCasEssai());
			}
		}
		// On valide l'objectif en fonction du succès du cas de test.
		casEssai.validerObjectif(outils.getDriver(), idObjectif, succes);
		//setCasEssai(casEssai);

		logger(casEssai.toString());

		//TODO A remettre
//		if (outils != null) {
//			outils.getDriver().quit();
//		}

		// On renseigne le rapport d'execution avec les données du cas de test.
		XLSOutils.renseignerExcel(casEssai);
		
		// On tente de mettre à jour ALM
		if (casEssai.getAlm()) {
			try {
				ALMOutils.miseAJourTestSet(casEssai, succes);
				System.out.println("Mise à jour effectuée dans ALM");
			} catch (SeleniumException ex) {
				ex.printStackTrace();
				System.out.println("Mise à jour impossible à effectuée dans ALM : " + ex.toString());
			}	
		}
	}
	
	/**
	 * Fonction de finalisation de test.
	 * @param outil la boite à outil.
	 * @param casEssai le cas d'essai.
	 * @param idObjectif l'id de l'objectif final.
	 * @throws SeleniumException en cas d'erreur.
	 */
	public final void finaliserTest(SeleniumOutils outil, CasEssaiBean casEssai, final String idObjectif) throws SeleniumException {
		finaliserTest(outil, casEssai, idObjectif, true);
	}
	
	/**
	 * Permet d'ajouter une ligne dans le registre d'execution pour apporter plus d'informations.
	 * @param append la chaine de caractère à  ajouter dans le registre d'execution.
	 */
	public final void logger(final String append) {
		if (getLogs() != null) {
			setLogs(getLogs() + "\n" + append);
		} else {
			setLogs(append);
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// FONCTION COMMUNES :
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	public String accesGoogle(SeleniumOutils outil) throws SeleniumException {
		
		String url = Constantes.URL_GOOGLE;
		String titre = Constantes.TITRE_PAGE;

		// Accès à google
		outil.chargerUrl(url);
		
		// Attente de l'affichage du titre de la page
		outil.attendreChargementPage(titre);

		return "OK";
	}
	
	/**
	 * Permet d'obtenir la boite a outil Selenium associe a un driver pour le scenario donne.
	 * @param scenario0 le scenario concerne.
	 * @return la boite a outil Selenium associee au scenario.
	 */
	public SeleniumOutils obtenirDriver(CasEssaiModeleBean scenario0) {
		//Configuration du driver
		FirefoxBinary ffBinary = new FirefoxBinary(new File(Constantes.EMPLACEMENT_FIREFOX));
		FirefoxProfile profile = configurerProfilNatixis();

		if (scenario0.getRepertoireTelechargement() == null) { 
			String repertoire = creerRepertoireTelechargement(scenario0, profile);
			scenario0.setRepertoireTelechargement(repertoire);
			this.setRepertoireTelechargement(repertoire);
		}
		// Initialisation du driver
		//FirefoxImpl driver = new FirefoxImpl(ffBinary, profile);
		FirefoxImpl driver = new FirefoxImpl(profile);
		
		driver.get(Constantes.URL_GOOGLE);

		
	    SeleniumOutils outil = new SeleniumOutils(driver, GenericDriver.FIREFOX_IMPL);
	    outil.setRepertoireRacine(scenario0.getRepertoireTelechargement());
		
		return outil;
	}
	
}
