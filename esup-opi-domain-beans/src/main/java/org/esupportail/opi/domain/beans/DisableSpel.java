package org.esupportail.opi.domain.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

public class DisableSpel implements BeanFactoryPostProcessor {
	
	/**
	 * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor
	 * #postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
	 */
	public void postProcessBeanFactory(
			ConfigurableListableBeanFactory beanFactory) throws BeansException {
		beanFactory.setBeanExpressionResolver(null);
	}
}
