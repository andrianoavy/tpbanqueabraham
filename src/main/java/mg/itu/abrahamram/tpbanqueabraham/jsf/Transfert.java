/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.validator.ValidatorException;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import jakarta.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
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
	@PositiveOrZero
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

	public String transferer() {
		boolean erreur = false;
		if (source.getId() == destination.getId()) {
			MessageUtil.messageErreur("Les ids source et destination ne doivent pas être les mêmes");
			return null;
		}

		try {
			gc.transferer(source, destination, montant);
		} catch (CompteBancaire.SoldeInsuffisantException ex) {
MessageUtil.messageErreur("Le compte source `ID = "+source.getId()+"` a un solde insuffisant(solde="+source.getSolde()+") pour le transfert", "Solde insuffisant", "form:source");
MessageUtil.messageErreur("Le montant à transférer est supérieur à la solde disponible("+montant+">"+source.getSolde()+")", "Solde insuffisant", "form:montant");
			return null;
		}

		MessageUtil.addFlashInfoMessage("Transfert de la somme de [" + montant + "] depuis le compte `" + source.getNom()+ "` vers `" + destination.getNom() + "` accompli avec succès");

		return "listeComptes?faces-redirect=true";
	}

	/**
	 * Creates a new instance of TransfertBean
	 */
	public Transfert() {
	}

}
