/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package mg.itu.abrahamram.tpbanqueabraham.service;

import jakarta.annotation.sql.DataSourceDefinition;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

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
@RequestScoped
public class GestionnaireCompte {

	/**
	 * Creates a new instance of GestionnaireCompte
	 */
	public GestionnaireCompte() {
	}

}
