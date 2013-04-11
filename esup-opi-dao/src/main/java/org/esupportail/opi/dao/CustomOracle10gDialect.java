package org.esupportail.opi.dao;

import java.sql.Types;

import org.hibernate.dialect.Oracle10gDialect;

/**
 * <p>
 * Classe utilisée dans le but de faire passer la validation hibernate éxecutée
 * au lancement de l'application. En effet, la base a été créée avec des champs
 * CLOB mappés sur un type hibernate "text" qui est traduit en "long" par le
 * dialect Oracle8i.
 * </p>
 * <p>
 * Pour notre besoin, nous venons donc ici le surcharger à notre tour pour lui
 * donner la valeur "clob".
 * </p>
 * 
 * @author llevague
 * 
 */
public class CustomOracle10gDialect extends Oracle10gDialect {

	public CustomOracle10gDialect() {
		super();
		registerColumnType(Types.LONGVARCHAR, "clob");
	}
}
