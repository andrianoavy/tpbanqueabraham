/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
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

	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public String effectueTransaction() {
		gc.detach(compte);
		String currentUrl = "retraitDepot?faces-redirect=true&id=" + compte.getId() + "&m=" + montant + "&d="+depot;
		boolean erreur = false;
		if (montant <= 0) {
			message = "Le montant doit être supérieur à 0";
			MessageUtil.addFlashErrorMessage(message, "form:montant");
			erreur = true;
		}
		try {
			gc.modifierSolde(compte, montant, depot);
		} catch (CompteBancaire.SoldeInsuffisantException ex) {
			message = "Le solde du compte est insuffisant(solde disponible = " + compte.getSolde() + ")";
			MessageUtil.addFlashErrorMessage(message, "form:montant");
			erreur=true;
		} catch (OptimisticLockException ex) {
			message = "Le compte de " + compte.getNom() + " a été modifié ou supprimé par un autre utilisateur!";
			MessageUtil.addFlashErrorMessage(message);
			erreur=true;
		}

		if(erreur) {
			return currentUrl;
		}
		MessageUtil.addFlashInfoMessage("Le compte `[" + compte.getId() + "] - " + compte.getNom() + "` a été " + (depot ? "crédité" : "débité") + " de [" + montant + "] ("+(depot ? "dépôt" : "retrait")+")");
		return "listeComptes?faces-redirect=true";
	}
}
