/**
 * 
 */
package org.esupportail.opi.services.mails;


import org.esupportail.opi.services.mails.expression.BoucleExpression;
import org.esupportail.opi.services.mails.expression.Expression;
import org.esupportail.opi.services.mails.expression.SimpleExpression;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.StringUtils;

import static java.lang.String.format;


/**
 * @author cleprous
 *
 */
public class DynamicMailContentImpl extends SimpleMailContentImpl {


	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -321813007559244805L;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());	

	/**
	 * The fields by class.
	 */
	private Map<Class< ? >, List<String>> fields;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public DynamicMailContentImpl() {
		super();
	}


	/** 
	 * @see org.esupportail.opi.services.mails.SimpleMailContentImpl#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();

	}

	/*
	 ******************* METHODS ********************** */



	/** 
	 * @see org.esupportail.opi.services.mails.SimpleMailContentImpl#makeBody()
	 */
	@Override
	public String makeBody() {
		if (getMailContent() == null) {
			throw new ConfigException("the getMailContent() should not be null");
		}
		String body = super.makeBody();
		//replace the Expression by this value
		List<Expression> expressions = getExpressions(body);
		if (log.isDebugEnabled()) {
			log.debug("-----EXPRESSIONS = = " + expressions);
		}
		if (expressions.isEmpty()) {
			return body;
		}

		return replaceExpression(body, expressions, 0);
	}

	/**
	 * @see org.esupportail.opi.services.mails.SimpleMailContentImpl#makeSubject()
	 */
	@Override
	public String makeSubject() {
		if (getMailContent() == null) {
			throw new ConfigException("the getMailContent() should not be null");
		}
		String subject = super.makeSubject();
		//replace the Expression by this value
		List<Expression> expressions = getExpressions(subject);
		if (log.isDebugEnabled()) {
			log.debug("-----EXPRESSIONS = = " + expressions);
		}
		if (expressions.isEmpty()) {
			return subject;
		}

		return replaceExpression(subject, expressions, 0);
	}


	/**
	 * make the list with all the expression in body.
	 * @param body
	 * @return Map with key expression and value contains expression for particular expression.
	 */
	private List<Expression> getExpressions(final String body) {
		List<Expression> expressions = new ArrayList<Expression>();

		Pattern p = Pattern.compile(MailContentUtils.REGEX_EXPRESSION, Pattern.MULTILINE );
		Matcher m = p.matcher(body);

		return createExpressions(expressions, m.find(), m, body, 0, null, 0);
	}

	/**
	 * Recursive method. create the expressions map.
	 * @param expressions
	 * @param findGroup
	 * @param m
	 * @param body
	 * @param beginContains
	 * @param parent
	 * @param compteur 
	 * @return Map key expression value contains to particular expression (example boucle)
	 */
	private List<Expression> createExpressions(
			final List<Expression> expressions, 
			final Boolean findGroup, 
			final Matcher m,
			final String body,
			final int beginContains,
			final Expression parent,
			final int compteur) {
		if (log.isDebugEnabled()) {
			log.debug("entering createExpressions");
			log.debug("expressions = " + expressions);
			log.debug("findGroup = " + findGroup);
			log.debug("beginContains = " + beginContains);
			log.debug("parent = " + parent);
			log.debug("compteur = " + compteur);
			log.debug("m = " + m);
			log.debug("body = " + body);

		}

		if (!findGroup) {
			if (beginContains != 0) {
				throw new ConfigException("la balise boucle n'a pas de fin");
			}
			if (log.isDebugEnabled()) {
				log.debug("leaving createExpressions");
				log.debug("expressions = " + expressions);
			}
			return expressions;
		}
		String ex = m.group();
		Expression expre = null;
		if (log.isDebugEnabled()) {
			log.debug("ex = " + ex);

		}
		
		int cpt = compteur + 1;
		if (ex.contains(MailContentUtils.KEY_WORD_FOR_BEGIN)) {
			expre = new BoucleExpression(null, ex, compteur, parent);
			expre.setObjet(getObject(getObjects(), expre));
			
			//nb character begin contains
			expressions.add(getContainsExpression((BoucleExpression) expre, body, m,  m.end()));

			//return createExpressions(expressions, m.find(), m, body, endGroup, expre, 0);	
		} else if (ex.contains(MailContentUtils.KEY_WORD_FOR_END)) {
			return expressions;

		} else {
			expre = new SimpleExpression(ex, compteur, parent);
			//TODO probleme recupere l'objet de la premiere list
			//doit executer le getter et recuperer le esultat du getter
			expre.setObjet(getObject(getObjects(), expre));
			expressions.add(expre);

		}
		return createExpressions(expressions, m.find(), m, body, beginContains, parent, cpt);

	}

	/**
	 * gestion du contenu d'une expression (BoucleExpression).
	 * @param exp
	 * @param body
	 * @param m
	 * @param startContains index character to start contains
	 * @return Expression
	 */
	public Expression getContainsExpression(
			final BoucleExpression exp, 
			final String body, 
			final Matcher m, 
			final int startContains) {
		if (log.isDebugEnabled()) {
			log.debug("entering getContainsExpression");
			log.debug("exp = " + exp);
			log.debug("body = " + body);
			log.debug("m = " + m);
			log.debug("startContains = " + startContains);

		}

		//debut du contenu de l'expression
		int cpt = 0;
		boolean findGroup = m.find();
		while (findGroup) {
			String ex = m.group();
			if (ex.contains(MailContentUtils.KEY_WORD_FOR_END)) {
				//on rÃÂ©cupÃÂ¨re le contenu HTML
				exp.setContains(body.substring(startContains, m.start()));
				//fin de la boucle
				return exp;
			} else {
				//le contenu contient de ELExpression
				int beginContains = 0;
				if (ex.contains(MailContentUtils.KEY_WORD_FOR_BEGIN)) {
					//sous boucle
					beginContains = m.end();
					
				} 
				
				exp.setExpressions(
						createExpressions(
								exp.getChildrens(), findGroup,
								m, body, beginContains, exp, cpt));
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("leaving getContainsExpression");
			log.debug("exp = " + exp);

		}

		return exp;
	}



	/**
	 * Replace the expression by the real value and control if expression in the fileds.
	 * @param body
	 * @param expressions
	 * @param compteur 
	 * @return String
	 */
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

		Object result = resultExpression(expression);
		String newBody = "";
		if (result != null) {
			newBody = result.toString();
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
	 * Execute the expression and return the real value.
	 */
	protected Object resultExpression(final Expression expression) {
		if (log.isDebugEnabled()) {
			log.debug("entering resultExpression = " + expression);
		}
		if (expression.getObjet() != null) {
			return executeExpression(expression.getProperties(),
				1, expression.getObjet(), expression);
		}
		return null;
	}





	/**
	 * Execute the expression and return the real value.
	 */
	private Object executeExpression(final String[] champs, 
			final int nbField,
			final Object o,
			final Expression expression) {
		if (log.isDebugEnabled()) {
			log.debug("entering executeExpression with object = " + o);
			log.debug("nbfield = " + nbField);
			for (int i = 0; i < champs.length; ++i) {
				log.debug(i + ". : champs = " + champs[i]); 
			}
		}
		if (champs.length == 1 && expression instanceof BoucleExpression) {
			//l'objet est une collection
			//pas de method a invoquÃÂ© il faut parcourir la collection
			return executeCollection(o, o.getClass(), (BoucleExpression) expression);
			
		}
		String field = champs[nbField];
		String methodName = MailContentUtils.START_ACCESSOR +
		    Character.toUpperCase(field.charAt(0)) + field.substring(1); //Utilitaires.upperCaseFirstChar(field, false);
		Object result = null;
		if (log.isDebugEnabled()) {
			log.debug("look for method = = " + methodName);
		}
		try {
			if (nbField == champs.length - 1) {
				//fin
				if (log.isDebugEnabled()) {
					log.debug("champs[nbField - 1] = " + champs[nbField - 1]);
				}
				

				//TODO on traite l'expression
				Method m = o.getClass().getMethod(methodName);
				result = m.invoke(o);
				
				
				if (expression instanceof BoucleExpression) {
					String retour = executeCollection(result, result.getClass(),
							(BoucleExpression) expression);
					if (StringUtils.hasText(retour)) {
						if (log.isDebugEnabled()) {
							log.debug("leaving executeExpression");
							log.debug("retour boucle = " + retour);
						}
						return retour;
					}
				}

				if (log.isDebugEnabled()) {
					log.debug("leaving executeExpression");
					log.debug("result.toString() = " + result);
				}
				return result;
			}

			//if o is a collection
			//impossible la collection doit etre la derniere propriete
			//			Collection<Object> c = useCollection(o, o.getClass());
			//
			//			if (c != null && !c.isEmpty()) {
			//				return managedCollection(c, methodName, expression);
			//			}
			//TODO on traite l'expression
			Method m = o.getClass().getMethod(methodName);
			result = m.invoke(o);
			
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			log.error(e);
		} catch (NoSuchMethodException e) {
			log.error(e);
			throw new ConfigException(format("this field %s is not present in class%s", field, o.getClass()));
		}

        int cpt = nbField + 1;
		
		//TODO voir si result est null quoi faire
//		String nameClass = expression.getNameClass(result);
//		List<String> prop = expression.getAllProperties(result.getClass());
//		Map<String, List<String>> m = new HashMap<String, List<String>>();
//		m.put(nameClass, prop);
		if (result != null) {
			return executeExpression(champs, cpt, result, expression);
		}
		return null;
	}



	/**
	 * execute la boucle si o est une collection et tradiut le contenu de la boucle.
	 * @param o
	 * @param clazz
	 * @param expression
	 * @return String
	 */
	private String executeCollection(final Object o , 
			final Class< ? > clazz, final BoucleExpression expression) {
		//if o is a collection
		Collection<Object> c = MailContentUtils.useCollection(o, clazz);

		if (c != null && !c.isEmpty()) {
			return managedCollection(c, expression);
		} 
		return "";
	}


	/**
	 * Parcours la collection et renvoie le contenu de la boucle.
	 * @param c
	 * @param expression 
	 * @return String
	 */
	private String managedCollection(
			final Collection<Object> c,  
			final BoucleExpression expression) {
		if (log.isDebugEnabled()) {
			log.debug("entering managedCollection");
			log.debug("collection c = " + c);
		}
		StringBuilder result = new StringBuilder();
		for (Object oCollec : c) {
			if (!expression.getChildrens().isEmpty()) {
				//on execute les expressions 
				//dans la boucle.
				for (Expression e : expression.getChildrens()) {
					e.setObjet(oCollec);
				}
				result.append(
						replaceExpression(
								expression.getContains(),
								expression.getChildrens(), 0));

			} else {
				result.append(expression.getContains());
			}


		}
		return result.toString();
	}



	/**
	 * update objet attribut.
	 * @param objects 
	 * @param expression
	 * return Object for this expressions.
	 */
	public Object getObject(final Object[] objects, final Expression expression) {
		//first element is a object
		final String className = expression.getProperties()[0];
		if (log.isDebugEnabled()) {
			log.debug("entering makeObject");
			log.debug("objects = " + objects);
			log.debug("first string s = " + className);
			log.debug("expression = " + expression);
		}
		if (objects == null || objects.length == 0) {
			throw new ConfigException("there is no objects in this mail"
					+ " then that the body contains any expressions.");
		}
		if (expression.getParent() != null 
				&& expression.getParent() instanceof BoucleExpression) {
			if (expression.getParent().getObjet() != null) {
				//on ne recuperer qu'un objet de la liste
				Object o = expression.getParent().getObjet();
				Collection<Object> c = MailContentUtils.useCollection(o, o.getClass());
				//on prend par defaut le premiere element pour décrire l'expression.
				//Il est mis à  jour dans le managedCollection
				if (c != null) {
					return c.iterator().next();
				} 
				return o;
			}
			return null;
		}
		for (Object o : objects) {
			String nameClazz = expression.getNameClass(o);
			if (log.isDebugEnabled()) {
				log.debug("nameClazz = " + nameClazz);
			}
			if (!StringUtils.hasText(nameClazz)) {
				//cas ou o est null
				return null;
			}
			if (expression instanceof BoucleExpression) {
				//TODO a tester car logiquement o devrait être une collection
				//permet de selectionner le bonne objet en fonction de l'expression
				if (nameClazz.equalsIgnoreCase(className)) {
					return MailContentUtils.useCollection(o, o.getClass());
				}
			} else {
				//permet de selectionner le bonne objet en fonction de l'expression
				if (nameClazz.equalsIgnoreCase(className)) {
					return o;
				} 
			}
		}
		throw new ConfigException("the class " + className + " is not present in the objects list.");
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return Map
	 */
	public Map<Class< ? >, List<String>> getFields() {
		return fields;
	}
	
	/**
	 * @param fields
	 */
	public void setFields(final Map<Class< ? >, List<String>> fields) {
		this.fields = fields;
	}
}
