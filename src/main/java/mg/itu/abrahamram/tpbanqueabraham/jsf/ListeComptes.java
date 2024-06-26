/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.inject.Named;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import java.io.Serializable;
import java.util.List;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.jsf.util.MessageUtil;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@Named(value = "listeComptes")
@ViewScoped
public class ListeComptes implements Serializable {

	@Inject
	private GestionnaireCompte gc;

	private List<CompteBancaire> allComptes;

	/**
	 * Creates a new instance of ListeComptes
	 */
	public ListeComptes() {
	}

	public List<CompteBancaire> getAllComptes() {
		if (allComptes == null) {
			allComptes = gc.getAllComptes();
		}
		return allComptes;
	}

	public String supprimerCompte(CompteBancaire compteBancaire) {
		String message = "Le compte ["+compteBancaire.getId()+"] au nom de `"+compteBancaire.getNom()+"` a été supprimé avec succès";
		gc.supprimer(compteBancaire);
		MessageUtil.addFlashInfoMessage(message);
		return "listeComptes?faces-redirect=true";
	}
}
