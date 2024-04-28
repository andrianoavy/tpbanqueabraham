/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import java.io.Serializable;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.jsf.util.MessageUtil;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@Named(value = "transfert")
@RequestScoped
public class Transfert implements Serializable {

	@Inject
	private GestionnaireCompte gc;

	private CompteBancaire source;
	private CompteBancaire destination;
	private int montant;

	public CompteBancaire getSource() {
		return source;
	}

	public void setSource(CompteBancaire source) {
		this.source = source;
	}

	public CompteBancaire getDestination() {
		return destination;
	}

	public void setDestination(CompteBancaire destination) {
		this.destination = destination;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	@Transactional
	public String transferer() {
		String currentUrl = "transfert?faces-redirect=true&s=" + source.getId() + "&d=" + destination.getId() + "&m=" + montant;
		boolean erreur = false;

		if (montant <= 0) {
			MessageUtil.addFlashErrorMessage("Le montant doit être supérieur à 0", "form:montant");
			erreur = true;
		}

		if (source.getId() == destination.getId()) {
			MessageUtil.addFlashErrorMessage("Les ids source et destination ne doivent pas être les mêmes");
			erreur = true;
		}

		try {
			gc.transferer(source, destination, montant);
		} catch (CompteBancaire.SoldeInsuffisantException ex) {
			MessageUtil.addFlashErrorMessage("Le compte source `ID = " + source.getId() + "` a un solde insuffisant(solde=" + source.getSolde() + ") pour le transfert", "form:source");
			MessageUtil.addFlashErrorMessage("Le montant à transférer est supérieur à la solde disponible(" + montant + ">" + source.getSolde() + ")", "form:montant");
			erreur = true;
		}

		if (erreur) {
			return currentUrl;
		}
		MessageUtil.addFlashInfoMessage("Transfert de la somme de [" + montant + "] depuis le compte `" + source.getNom() + "` vers `" + destination.getNom() + "` accompli avec succès");

		return "listeComptes?faces-redirect=true";
	}

	/**
	 * Creates a new instance of TransfertBean
	 */
	public Transfert() {
	}

}
