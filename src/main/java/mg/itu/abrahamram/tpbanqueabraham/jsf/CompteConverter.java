/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import mg.itu.abrahamram.tpbanqueabraham.entities.CompteBancaire;
import mg.itu.abrahamram.tpbanqueabraham.service.GestionnaireCompte;

/**
 *
 * @author Aldramech
 */
@FacesConverter(value = "compteConverter", managed = true)
public class CompteConverter implements Converter<CompteBancaire> {

	@Inject
	private GestionnaireCompte gc;

	/*
	 * Convertit une String en CompteBancaire.
	 *
	 * @param value valeur à convertir
	 * @return 
	 */
	@Override
	public CompteBancaire getAsObject(FacesContext context, UIComponent component, String value) {
		System.out.println("VALUE "+value);
		System.out.println("LONG "+Long.parseUnsignedLong(value));
		if (value == null) {
			return null;
		}
		return gc.findById(Long.parseLong(value));
	}

	/**
	 * Convertit un CompteBancaire en String.
	 *
	 * @param value valeur à convertir
	 */
	@Override
	public String getAsString(FacesContext context, UIComponent component, CompteBancaire compte) {
		if (compte == null) {
			return "";
		}
		return compte.getId().toString();
	}

}
