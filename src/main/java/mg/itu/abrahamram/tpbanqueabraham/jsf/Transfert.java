/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.inject.Inject;
import java.io.Serializable;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
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

	public String transferer() {
		gc.transferer(source, destination, montant);
		return "/listeComptes?faces-redirect=true";	
	}
	
	/**
	 * Creates a new instance of TransfertBean
	 */
	public Transfert() {
	}
	
}
