/**
 * 
 */
package org.esupportail.opi.services.mails;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.services.mails.expression.BoucleExpression;
import org.esupportail.opi.services.mails.expression.Expression;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 * MailContentWithExceptionImpl : make the make with exception when invoke method.
 * After invoke method execute another method to obtain the result.
 */
public class MailContentWithExceptionImpl extends DynamicMailContentImpl {


	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5587437779407041375L;

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());	

	/**
	 * see {@link MailExceptionMethods}.
	 */
	private MailExceptionMethods mailExceptionMethods;



	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public MailContentWithExceptionImpl() {
		super();
	}

	/** 
	 * @see org.esupportail.opi.services.mails.SimpleMailContentImpl#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		Assert.notNull(this.mailExceptionMethods, 
				"property mailExceptionMethods of class " 
				+ this.getClass().getName() + " can not be null");
	}






	/*
	 ******************* METHODS ********************** */



	/**
	 * @see org.esupportail.opi.services.mails.DynamicMailContentImpl#replaceExpression(
	 * java.lang.String, java.util.List, int)
	 */
	@Override
	protected String replaceExpression(
			final String body, 
			final List<Expression> expressions, 
			final int compteur) {
		if (log.isDebugEnabled()) {
			log.debug("entering replaceExpression");
			log.debug("compteur = " + compteur);
			log.debug("expressions.size = " + expressions.size());
		}

		if (expressions.size() == compteur) {
			//suppression de balise end_boucle
			if (log.isDebugEnabled()) {
				log.debug("leaving replaceExpression");
			}
			return body.replaceAll(MailContentUtils.REGEX_EXPRESSION, "");
		}
		Expression expression = expressions.get(compteur);

		Object result = super.resultExpression(expression);
		String newBody = null;
		if (result != null) {
			String methodException = mailExceptionMethods.getExceptions().get(expression.getExpression());
			if (StringUtils.hasText(methodException)) {
				//ex
				newBody = executeException(expression.getExpression(), result);
			} 

			if (newBody == null) { newBody = result.toString(); }
		} else {
			newBody = "";
		}
		//replace the expression by result
		if (expression instanceof BoucleExpression) {
			BoucleExpression b = (BoucleExpression) expression;
			newBody = body.replace(b.getContains(), newBody);
		} else {
			newBody = body.replace(expression.getELexpression(), newBody);
		}

		int cpt = compteur + 1;
		return replaceExpression(newBody, expressions, cpt);
	}


	/**
	 * execute the exception method for this expression.
	 * @param expression
	 * @param resultExpression 
	 * @return String
	 */
	public String executeException(final String expression, final Object resultExpression) {
		if (log.isDebugEnabled()) {
			log.debug("entering executeException");
			log.debug("expression = " + expression);
			log.debug("resultExpression = " + resultExpression);
		}
		String nameMethod = mailExceptionMethods.getExceptions().get(expression);
		//TODO voir pq hibernate renvoie du java.sql.Date alros que l'on utilise que du java.util.Date
		Class< ? > []tab = new Class< ? >[1];
		tab[0] = (resultExpression.getClass().getName().matches(
		    ".*(_\\$\\$_javassist_).*")) ? resultExpression.getClass().getSuperclass() :
		        resultExpression.getClass(); // Utilitaires.getClass(resultExpression);
		if (log.isDebugEnabled()) {
			log.debug("nameMethod = " + nameMethod);
			log.debug("Class< ? > []tab -> tab[0] = " + tab[0]);
		}
		if (resultExpression.getClass().equals(java.sql.Date.class)) {
			tab = new Class< ? >[1];
			tab[0] = java.util.Date.class;
		} 
		Method method = null;
		Object result = null;
		try {
			method = mailExceptionMethods.getClass().getMethod(nameMethod, tab);
			result = method.invoke(mailExceptionMethods, resultExpression);
		} catch (SecurityException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
		} catch (IllegalArgumentException e) {
			log.error(e);
		} catch (IllegalAccessException e) {
			log.error(e);
		} catch (InvocationTargetException e) {
			log.error(e);
		}

		return (result != null) ? result.toString() : "";
	}

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @param mailExceptionMethods the mailExceptionMethods to set
	 */
	public void setMailExceptionMethods(final MailExceptionMethods mailExceptionMethods) {
		this.mailExceptionMethods = mailExceptionMethods;
	}





}
