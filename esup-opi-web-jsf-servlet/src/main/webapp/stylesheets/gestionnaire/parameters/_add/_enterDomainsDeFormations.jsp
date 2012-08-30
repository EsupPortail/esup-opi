<%@include file="../../../_include/_include.jsp"%>
<h:form id="formAddDomain">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi" styleClass="tableJustWidthMax">
		<e:text value="#{msgs['DOMAIN.TITLE.ENTER']}" styleClass="section-smallTitle"/>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.addAction}"
				action="#{paramDomainFormationController.add}"/>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.updateAction}"
				action="#{paramDomainFormationController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{paramDomainFormationController.reset}"/>
		</t:panelGroup>
	</e:panelGrid>
	
	<t:panelGrid columns="3" styleClass="displayInfo">
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}"
			styleClass="form-field-label-inHeader-validator" for="code"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
			styleClass="form-field-label-inHeader-validator" for="inUse"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_LIB_LANGUE']}"
			styleClass="form-field-label-inHeader-validator" for="doms"/>

		<e:inputText id="SaisiCode" size="10"
			value="#{paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom}"/>
		<t:selectOneMenu id="choixEnServ"
			value="#{paramDomainFormationController.domain.ren1DomaineAnnuForm.temSveDom}">
			<f:selectItems value="#{paramDomainFormationController.temEnSveItems}"/>
		</t:selectOneMenu>
		<t:panelGroup rendered="#{paramDomainFormationController.actionLang.whatAction != paramDomainFormationController.actionLang.addAction}">
			<t:dataList var="langueDomSelect" value="#{paramDomainFormationController.domain.ren1Domaine2AnnuForm}">
				
				<t:panelGrid columns="3" 
						rendered="#{paramDomainFormationController.actionLang.whatAction == paramDomainFormationController.actionLang.emptyAction
								|| (paramDomainFormationController.actionLang.whatAction == paramDomainFormationController.actionLang.updateAction
								&& paramDomainFormationController.langueSelected != langueDomSelect.codLang)}">
					<e:commandButton image="/media/images/update.png" styleClass="espaceIcone"
						action="#{paramDomainFormationController.updateLangLib}">
						<t:updateActionListener
							value="#{langueDomSelect.codLang}"
							property="#{paramDomainFormationController.langueSelected}"/>
						<t:updateActionListener
							value="#{langueDomSelect.libDom}"
							property="#{paramDomainFormationController.libSaisi}"/>
					</e:commandButton>
					<e:commandButton image="/media/images/cancel.png" styleClass="espaceIcone"
						action="#{paramDomainFormationController.suppLangLib}">
						<t:updateActionListener
							value="#{langueDomSelect.codLang}"
							property="#{paramDomainFormationController.langueSelected}"/>
					</e:commandButton>
					<e:text styleClass="textLangLib" value="#{langueDomSelect.libDom} (#{langueDomSelect.codLang})"/>
				</t:panelGrid>
				
				<t:panelGrid columns="3" 
						rendered="#{paramDomainFormationController.actionLang.whatAction == paramDomainFormationController.actionLang.updateAction
								&& paramDomainFormationController.langueSelected == langueDomSelect.codLang}">
					<e:outputLabel value="#{msgs['FIELD_LABEL.LANGUE']}"
						styleClass="form-field-label-inHeader-validator" for="langue"/>
					<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
						styleClass="form-field-label-inHeader-validator" for="libele"/>
					<e:outputLabel value=""
						styleClass="form-field-label-inHeader-validator" for="bouton"/>
					
					<e:text styleClass="textLangLib" value="#{langueDomSelect.codLang}"/>
					<e:inputText id="ModifLib" size="100" value="#{paramDomainFormationController.libSaisi}"/>
					<t:div styleClass="twoGroupsButton">
						<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
							action="#{paramDomainFormationController.validModLangLib}"/>
						<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
							action="#{paramDomainFormationController.annulLangLib}"/>
					</t:div>
				</t:panelGrid>
				
			</t:dataList>
			<e:commandButton image="/media/images/add.png" styleClass="espaceIcone"
					rendered="#{not empty paramDomainFormationController.allLangue}"
					action="#{paramDomainFormationController.addLangLib}"/>
		</t:panelGroup>
		<t:panelGroup rendered="#{paramDomainFormationController.actionLang.whatAction == paramDomainFormationController.actionLang.addAction}">
			<t:panelGrid columns="3">
				<e:outputLabel value="#{msgs['FIELD_LABEL.LANGUE']}"
					styleClass="form-field-label-inHeader-validator" for="langue"/>
				<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
					styleClass="form-field-label-inHeader-validator" for="libele"/>
				<e:outputLabel value=""
					styleClass="form-field-label-inHeader-validator" for="bouton"/>
				
				<t:selectOneMenu id="choixLang"
					value="#{paramDomainFormationController.langueSelected}">
					<f:selectItems value="#{paramDomainFormationController.allLangue}"/>
				</t:selectOneMenu>
				<e:inputText id="choixlib" size="100"
					value="#{paramDomainFormationController.libSaisi}"/>
				<t:div styleClass="twoGroupsButton">
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						action="#{paramDomainFormationController.validAddLangLib}"/>
					<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
						action="#{paramDomainFormationController.annulLangLib}"/>
				</t:div>
			</t:panelGrid>
		</t:panelGroup>
	</t:panelGrid>
	<e:message for="formAddDomain"/>
</h:form>
