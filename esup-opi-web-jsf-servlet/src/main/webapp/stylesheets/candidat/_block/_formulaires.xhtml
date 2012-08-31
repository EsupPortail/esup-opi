<%@include file="../../_include/_include.jsp"%>
<t:div id="blockInfoForm" styleClass="blockForm"
	rendered="#{not empty formulairesController.indSelectedForms
				&& not sessionController.currentInd.hideMessageInfoForm}">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FORMULAIRE']}" styleClass="section-smallTitle" />
		</e:panelGrid>
	</t:div>
	<e:dataTable var="indVoeuPojo"
		value="#{sessionController.currentInd.indVoeuxPojo}"
		alternateColors="false" renderedIfEmpty="false"
		styleClass="displayInfo">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['INFO.SAISIE']}" />
			</f:facet>
			<e:commandButton styleClass="form-button-link" immediate="true"
				action="#{formationController.goSummaryWishes}"
				value="#{indVoeuPojo.vrsEtape.libWebVet}"
				rendered="#{formulairesController.formulairesCmi[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null
							&& indVoeuPojo.etat.displayForms}">
				<t:updateActionListener value="#{indVoeuPojo}"
					property="#{formationController.indVoeuPojo}" />
			</e:commandButton>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['INDIVIDU.ETAT']}" />
			</f:facet>
			<t:panelGroup
				rendered="#{formulairesController.formulairesCmi[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null
							&& indVoeuPojo.etat.displayForms}">
				<e:commandButton value="#{msgs['FORMULAIRE.ACCESS']}"
					style="font-size:14px;"
					action="#{formulairesController.createResponseInRunner}"
					rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] == null && formulairesController.indSelectedForms[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
					<t:updateActionListener value="#{indVoeuPojo.indVoeu}"
						property="#{formulairesController.indVoeuSelected}" />
				</e:commandButton>
				<h:outputLink target="_blank" styleClass="form-button"
					style="font-size:14px;"
					value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
							-#{sessionController.currentInd.regimeInscription.shortLabel}/edit/#{indVoeuPojo.indVoeu.individu.numDossierOpi}"
					rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null
					&& sessionController.currentInd.asRightsToUpdate}">
					<e:text value="#{msgs['FORMULAIRE.EDIT2']}"/>
				</h:outputLink>
				<h:outputLink target="_blank" styleClass="form-button-image"
					value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
							-#{sessionController.currentInd.regimeInscription.shortLabel}/view/#{indVoeuPojo.indVoeu.individu.numDossierOpi}"
					rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
					<t:graphicImage value="/media/images/magnifier.png"
						title="#{msgs['_.BUTTON.DISPLAY']}" />
				</h:outputLink>
			</t:panelGroup>
		</t:column>
	</e:dataTable>
</t:div>