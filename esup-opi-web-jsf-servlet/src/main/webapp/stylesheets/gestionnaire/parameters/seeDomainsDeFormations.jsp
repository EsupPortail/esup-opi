<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['DOMAIN.MANAGED']}"/>
	<e:messages styleClass="messageForUser"/>
	<t:panelGroup id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.deleteAction}">
		<%@include file="_delete/_deleteDomainsDeFormations.jsp"%>
	</t:panelGroup>
	<h:form id="seeDomainsForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{paramDomainFormationController.goAddDom}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}"/>
		</t:div>
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.updateAction}">
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				action="#{paramDomainFormationController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
				action="#{paramDomainFormationController.reset}"/>
		</t:div>
		<e:dataTable var="domainSelect"
			value="#{paramDomainFormationController.listDomain}"
			styleClass="paginatorData" alternateColors="true">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.CODE']}"/>
				</f:facet>
				<e:text value="#{domainSelect.ren1DomaineAnnuForm.codDom}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}"/>
				</f:facet>
				<t:selectOneMenu value="#{paramDomainFormationController.domain.ren1DomaineAnnuForm.temSveDom}"
					rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.updateAction
						&& domainSelect.ren1DomaineAnnuForm.codDom == paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom}">
						<f:selectItems value="#{paramDomainFormationController.temEnSveItems}"/>maitre fion
				</t:selectOneMenu>
				<t:graphicImage url="/media/images/check2.gif"
					rendered="#{domainSelect.ren1DomaineAnnuForm.temSveDom == 'O'
					&& (paramDomainFormationController.actionEnum.whatAction != paramDomainFormationController.actionEnum.updateAction
						|| domainSelect.ren1DomaineAnnuForm.codDom != paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom)}"/>
				<t:graphicImage url="/media/images/check0.gif"
					rendered="#{domainSelect.ren1DomaineAnnuForm.temSveDom == 'N'
					&& (paramDomainFormationController.actionEnum.whatAction != paramDomainFormationController.actionEnum.updateAction
						|| domainSelect.ren1DomaineAnnuForm.codDom != paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom)}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIST_LIB_LANGUE']}"/>
				</f:facet>
				<t:dataList var="langueDomSelect"
					value="#{domainSelect.ren1Domaine2AnnuForm}"
					rendered="#{paramDomainFormationController.actionEnum.whatAction != paramDomainFormationController.actionEnum.updateAction
						|| domainSelect.ren1DomaineAnnuForm.codDom != paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom}">
					<e:text value="#{langueDomSelect.libDom} (#{langueDomSelect.codLang})"/>
				</t:dataList>
				<t:panelGroup
						rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.updateAction
						&& domainSelect.ren1DomaineAnnuForm.codDom == paramDomainFormationController.domain.ren1DomaineAnnuForm.codDom}">
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
				</t:panelGroup>
			</t:column>
			<t:column styleClass="buttonTD"
				rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{paramDomainFormationController.actionEnum.updateAction}"
						property="#{paramDomainFormationController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{domainSelect}"
						property="#{paramDomainFormationController.domain}"/>
				</e:commandButton>
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{paramDomainFormationController.actionEnum.deleteAction}"
						property="#{paramDomainFormationController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{domainSelect}"
						property="#{paramDomainFormationController.domain}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		</h:form>
		<t:panelGroup id="addDomain"
			rendered="#{paramDomainFormationController.actionEnum.whatAction == paramDomainFormationController.actionEnum.addAction}">
			<%@include file="_add/_enterDomainsDeFormations.jsp"%>
		</t:panelGroup>
	
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		<%--
			highlightInputAndSelect('seeDomainsForm');
		--%>
	</script>
</e:page>
