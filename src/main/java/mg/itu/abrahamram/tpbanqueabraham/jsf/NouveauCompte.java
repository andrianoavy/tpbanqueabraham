/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.validation.constraints.PositiveOrZero;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.jsf.util.MessageUtil;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@Named(value = "nouveauCompte")
@RequestScoped
public class NouveauCompte {

	@Inject
	private GestionnaireCompte gc;
	private String nom;
	@PositiveOrZero(message = "Le montant doit être supérieur à 0")
	private int solde;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int getSolde() {
		return solde;
	}

	public void setSolde(int solde) {
		this.solde = solde;
	}


	/**
	     * Creates a new instance of NouveauCompte
	     */
	public NouveauCompte() {
	}

	public String creerCompte() {
		CompteBancaire nouveau = new CompteBancaire(nom, solde);
		gc.creerCompte(nouveau);
		MessageUtil.addFlashInfoMessage("Nouveau compte créé au nom de `"+nouveau.getNom()+"` avec le solde initial de ["+nouveau.getSolde()+"] associé à l'ID ["+nouveau.getId()+"]");
		return "listeComptes?faces-redirect=true";
	}

}
