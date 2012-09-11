package org.esupportail.opi.utils;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

public class TestStringElExpression extends TestCase {
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	public TestStringElExpression() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TestStringElExpression(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	public void testString() {
		String b = "je fais uine test avec ${individu.adresse.codCom} et je ne sais pas si ${adressePojo.adresse.codBdi}";
		System.out.println("contains ${ " + b.contains("${"));
		System.out.println(b);
		List<String> expression = new ArrayList<String>();
		Boolean iselExpression = false;
		StringBuilder s = null;
		for (int i = 0; i < b.toCharArray().length; ++i) {
			Character car = b.toCharArray()[i];
			System.out.println("car = " + car);
			if (!iselExpression ) {
				System.out.println("car.equals($) = " + car.equals('$'));
				if (car.equals(Character.valueOf('$'))) {
					s = new StringBuilder("$");
					iselExpression = true;
				}
			} else {
				//fin el expression
				if (car.equals('}')) {
					iselExpression = false;
					s.append(car);
					expression.add(s.toString());
					s = null;
				} else {
					//dans elexpression
					s.append(car);
				}
			}
		}
		System.out.println("liste string =  " + expression);
		Assert.assertNotNull(b);
	}

	
	
}
