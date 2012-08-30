<%@include file="../../../_include/_include.jsp"%>
<t:div id="blockFormProp" styleClass="menuWelcome"
	rendered="#{opinionController.actionEnum.whatAction == 
				opinionController.actionEnum.propositionAction}">
	<h:form id="enterPropositionForm" styleClass="opiR1_form">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.PROPOSITION']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="Sélectionner une nouvelle commission"
					action="#{commissionController.reset}" 
					rendered="#{not empty trtCmiController.allTraitementCmi}"
					immediate="true" />
			</t:panelGroup>
		</e:panelGrid>
		<t:div>
			<e:text value="#{msgs['FIELD_LABEL.AVIS.STUDENT']}" 
				styleClass="section-subheader"/>
			<e:text value="#{opinionController.indVoeuxPojo.indVoeu.individu.nomPatronymique}"/>
			<e:text value=" "/>
			<e:text value="#{opinionController.indVoeuxPojo.indVoeu.individu.prenom}"/>
			<t:htmlTag value="br" />
			<e:text value="#{msgs['FIELD_LABEL.AVIS.ETAPE']}" 
				styleClass="section-subheader"/>
			<e:text value="#{opinionController.indVoeuxPojo.vrsEtape.libWebVet}"/>
			<t:htmlTag value="br" />
		</t:div>
		<t:div rendered="#{not empty trtCmiController.allTraitementCmi}"
				style="width:100%;text-align:right;">
			<e:commandButton id="savePropositions" value="#{msgs['_.BUTTON.SAVE']}"
				action="#{opinionController.savePropositions}"/>
			<e:commandButton id="cancelPropositions" value="#{msgs['_.BUTTON.CANCEL']}">
				<t:updateActionListener
					value="#{opinionController.actionEnum.emptyAction}"
					property="#{opinionController.actionEnum.whatAction}" />
			</e:commandButton>
		</t:div>
		<e:dataTable var="beanTrtCmi" id="versionEtapeData"
			value="#{trtCmiController.allTraitementCmi}"
			styleClass="paginatorData" alternateColors="true"
			renderedIfEmpty="false">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.INTITULE_FORMA']}" />
				</f:facet>
				<e:text value="#{beanTrtCmi.etape.libWebVet}" />
			</t:column>
			<t:column>
				<jdt:multipleRowsSelector
					selectionList="#{commissionController.objectToAdd}" />
			</t:column>
		</e:dataTable>


		<t:div rendered="#{empty trtCmiController.allTraitementCmi}">
			<e:commandButton id="changeCommission" 
				value="#{msgs['_.BUTTON.CHANGE']}"
				action="#{opinionController.selectCmiForProposition}"/>
			<e:panelGrid styleClass="tableWidthMax" columns="2"
				columnClasses="col1UnDemi,col2UnDemi">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['COMMISSION.CHOICE']}"
						for="lesCommissions" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
						value="#{commissionController.commission.id}"
						onchange="javascript:{simulateLinkClick('enterPropositionForm:changeCommission');}" >
						<f:selectItem itemLabel="" itemValue="" />
						<t:selectItems var="commission" value="#{commissionController.commissionsItemsByRight}"
							itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
					</e:selectOneMenu>
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
	</h:form>
</t:div>
<script type="text/javascript">
	hideElement('enterPropositionForm:changeCommission');
</script>
