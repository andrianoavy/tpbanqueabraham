/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.jsf;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
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
		if (value == null || value.isBlank()) {
			return null;
		}
		CompteBancaire compte = gc.findById(Long.parseLong(value));
		if(compte == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Pas de compte associé","Pas de compte associé à l'ID `"+value+"`");
			throw new ConverterException(message);
		}
		return compte;
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
