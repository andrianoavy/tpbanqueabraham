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
@Named(value = "retraitDepot")
@ViewScoped
public class RetraitDepot implements Serializable {

	@Inject
	private GestionnaireCompte gc;

	private boolean depot = true;
	private CompteBancaire compte;

	private int montant;

	public boolean isDepot() {
		return depot;
	}

	public void setDepot(boolean isDepot) {
		this.depot = isDepot;
	}

	public CompteBancaire getCompte() {
		return compte;
	}

	public void setCompte(CompteBancaire compte) {
		this.compte = compte;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	/**
	 * Creates a new instance of RetraitDepot
	 */
	public RetraitDepot() {
	}

	private String message;

	public String effectueTransaction() {
		boolean erreur = false;
		if (montant <= 0) {
			message = "Le montant doit être supérieur à 0";
			MessageUtil.messageErreur(message, "form:montant");
			erreur = true;
		}

		if (!isDepot() && montant > compte.getSolde()) {
			message = "Le solde du compte est insuffisant(solde disponible = " + compte.getSolde() + ")";
			MessageUtil.messageErreur(message, "form:montant");
			erreur=true;
		}

		if(erreur) {
			return null;
		}

		// Si pas d'erreur modifier le solde
		gc.modifierSolde(compte, montant, isDepot());
		MessageUtil.addFlashInfoMessage("Le compte `[" + compte.getId() + "] - " + compte.getNom() + "` a été " + (depot ? "crédité" : "débité") + " de [" + montant + "] ("+(depot ? "dépôt" : "retrait")+")");

		return "listeComptes?faces-redirect=true";
	}
}
