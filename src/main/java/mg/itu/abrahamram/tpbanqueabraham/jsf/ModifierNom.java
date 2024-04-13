/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.jsf.util.MessageUtil;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@Named(value = "modifierNom")
@ViewScoped
public class ModifierNom  implements Serializable{

	@Inject
	private GestionnaireCompte gc;

	private CompteBancaire compte;
	private String nouveauNom; 

	public CompteBancaire getCompte() {
		return compte;
	}

	public void setCompte(CompteBancaire compte) {
		this.compte = compte;
	}

	public String getNouveauNom() {
		if(nouveauNom == null) {
			nouveauNom = getCompte().getNom();
		}
		return nouveauNom;
	}

	public void setNouveauNom(String nouveauNom) {
		this.nouveauNom = nouveauNom;
	}

	/**
	 * Creates a new instance of RetraitDepot
	 */
	public ModifierNom() {
	}

	private String message;

	public String modifieNom() {
		String message = "Le compte `[" + compte.getId() + "] - " + compte.getNom() + "` a été renommé en `" + nouveauNom + "`";
		compte.setNom(nouveauNom);
		gc.update(compte);
		MessageUtil.addFlashInfoMessage(message);
		return "listeComptes?faces-redirect=true";
	}
}
