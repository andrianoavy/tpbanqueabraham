/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.config;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;
import jakarta.transaction.Transactional;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@Named(value = "init")
@ApplicationScoped
public class Init {

	@Inject
	private GestionnaireCompte gc;

	/**
	 * Creates a new instance of Init
	 */
	public Init() {
	}

	@Transactional
	@PostConstruct
	public void init(
	    @Observes
	    @Initialized(ApplicationScoped.class) ServletContext context) {
		Long count = gc.nbComptes();
		if (count == 0) {
			gc.creerCompte(new CompteBancaire("John Lennon", 150000));
			gc.creerCompte(new CompteBancaire("Paul McCartney", 950000));
			gc.creerCompte(new CompteBancaire("Ringo Starr", 20000));
			gc.creerCompte(new CompteBancaire("Georges Harrisson", 100000));
		}
	}

}
