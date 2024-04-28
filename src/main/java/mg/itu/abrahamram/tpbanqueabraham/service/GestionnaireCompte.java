/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.List;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;

/**
 *
 * @author Aldramech
 */
@DataSourceDefinition(
    className = "com.mysql.cj.jdbc.MysqlDataSource",
    name = "java:app/jdbc/banque",
    serverName = "localhost",
    portNumber = 3306,
    user = "banque_user",
    password = "banque_user_123",
    databaseName = "banque",
    properties = {
	    "useSSL=false",
	    "allowPublicKeyRetrieval=true",
	    "driverClass=com.mysql.cj.jdbc.Driver"
    }
)
@Named(value = "gestionnaireCompte")
@ApplicationScoped
public class GestionnaireCompte {

	@PersistenceContext(unitName = "banquePU")
	private EntityManager em;

	/**
	 * Creates a new instance of GestionnaireCompte
	 */
	public GestionnaireCompte() {
	}

	@Transactional
	public void creerCompte(CompteBancaire compteBancaire) {
		em.persist(compteBancaire);
	}

	public List<CompteBancaire> getAllComptes() {
		TypedQuery<CompteBancaire> query = em.createNamedQuery("CompteBancaire.findAll", CompteBancaire.class);
		return query.getResultList();
	}

	public long nbComptes() {
		TypedQuery<Long> query = em.createNamedQuery("CompteBancaire.count", Long.class);
		return query.getResultList().get(0);
	}

	@Transactional
	public void transferer(CompteBancaire source, CompteBancaire destinataire, int montant) {
		source.retirer(montant);
		destinataire.deposer(montant);
		update(source);
		update(destinataire);
	}

	@Transactional
	public CompteBancaire update(CompteBancaire compteBancaire) {
		return em.merge(compteBancaire);
	}

	public CompteBancaire findById(Long idCompteBancaire) {
		return em.find(CompteBancaire.class, idCompteBancaire);
	}

	@Transactional
	public void modifierSolde(CompteBancaire compte, int montant, boolean depot) {
		if (depot) {
			compte.deposer(montant);
		} else {
			compte.retirer(montant);
		}
		em.merge(compte);
	}

	@Transactional
	public void supprimer(CompteBancaire compteBancaire) {
		compteBancaire = em.merge(compteBancaire);
		em.remove(compteBancaire);
	}

}
