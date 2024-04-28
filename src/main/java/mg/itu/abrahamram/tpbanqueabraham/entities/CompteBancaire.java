/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Aldramech
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "CompteBancaire.findAll", query = "SELECT distinct c FROM CompteBancaire c join fetch c.operations"),
    @NamedQuery(name = "CompteBancaire.count", query = "SELECT count(c) FROM CompteBancaire c")
})
@Table(name = "comptebancaire")
public class CompteBancaire implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nom;
	private int solde;
	@Version
	private int version;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<OperationBancaire> operations = new ArrayList<>();

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

	public Long getId() {
		return id;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	

	public CompteBancaire() {
	}

	public List<OperationBancaire> getOperations() {
		return operations;
	}
	
	public CompteBancaire(String nom, int solde) {
		this.nom = nom;
		this.solde = solde;
		this.operations.add(new OperationBancaire("Création du compte",solde));
	}

	public void deposer(int montant) {
		solde += montant;
		operations.add(new OperationBancaire("Crédit", montant));
	}

	public void retirer(int montant) {
		solde -= montant;
		operations.add(new OperationBancaire("Débit", montant * -1));
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are not set
		if (!(object instanceof CompteBancaire)) {
			return false;
		}
		CompteBancaire other = (CompteBancaire) object;
		if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "mg.itu.abrahamram.tpbanqueabraham.entities.CompteBanquaire[ id=" + id + " ]";
	}

}
