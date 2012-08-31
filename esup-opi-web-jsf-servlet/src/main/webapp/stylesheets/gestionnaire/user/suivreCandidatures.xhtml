<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />

		<t:div id="choiceColumnExport" 
			rendered="#{printOpinionController.actionEnum.whatAction == printOpinionController.actionEnum.choiceColExport}">
				<%@include file="../opinion/_block/_choiceColumnExport.jsp"%>
		</t:div>
		
		
		<h:form id="monitorApplicationsForm" styleClass="opiR1_form">
			<t:div id="blockFormSearch" styleClass="blockForm">
				<t:div style="text-align:right;">
					<e:commandButton value="#{msgs['_.BUTTON.EXPORT']}"
						action="#{monitorCandidaturesController.exportList}" />
					<e:commandButton value="#{msgs['_.BUTTON.CHOICE_COL']}" immediate="true">
						<t:updateActionListener 
							value="#{printOpinionController.actionEnum.choiceColExport}"
							 property="#{printOpinionController.actionEnum.whatAction}"/>
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}" />
				</t:div>
				
				<t:div id="blockselectCmi" styleClass="blockForm">
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
						</t:panelGroup>
						<e:selectManyCheckbox id="regime" value="#{commissionController.listeRI}" converter="#{regimeInscriptionConverter}"
							disabled="#{!commissionController.canModifyRISearch}"
							onchange="javascript:{simulateLinkClick('monitorApplicationsForm:lookForIndividuComm');}"
							layout="lineDirection">
							<t:selectItems var="ri" 
									itemLabel="#{ri.shortLabel}" itemValue="#{ri}"
									value="#{nomenclatureController.allRegimeInscription}"/>
						</e:selectManyCheckbox>
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.STATE']}" for="state" />
						</t:panelGroup>
						<t:panelGroup>
							<opi:radioButton itemValue="STATE.DESIST" 
								itemLabel="#{msgs['STATE.DESIST']}" name="stateWish" overrideName="true"
								value="#{monitorCandidaturesController.stateSelected}"
								onClick="javascript:{simulateLinkClick('monitorApplicationsForm:lookForIndividuComm');}"/>
							<opi:radioButton itemValue="STATE.CONFIRM" 
								itemLabel="#{msgs['STATE.CONFIRM']}" name="stateWish" overrideName="true"
								value="#{monitorCandidaturesController.stateSelected}"
								onClick="javascript:{simulateLinkClick('monitorApplicationsForm:lookForIndividuComm');}"/>
						</t:panelGroup>
						<t:panelGroup/>
						<t:panelGroup/>
						<t:panelGroup>
							<e:outputLabel value="#{msgs['COMMISSIONS']}" for="cmi" />
						</t:panelGroup>
						<t:panelGroup>
							<e:selectOneMenu id="cmi" converter="javax.faces.Integer"
								valueChangeListener="#{monitorCandidaturesController.changeCommission}"
								onchange="javascript:{simulateLinkClick('monitorApplicationsForm:lookForCmi');}"
								value="#{commissionController.commission.id}">
								<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.commissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
							</e:selectOneMenu>
							<e:commandButton id="lookForCmi"
								value="#{msgs['_.BUTTON.SEARCH']}"
								action="#{monitorCandidaturesController.changeCommission}" />
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
				
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				
				<t:div id="blockSelectTrtCmi" styleClass="blockForm">
					<e:panelGrid styleClass="tableWidthMax" columns="3"
						columnClasses="colUnQuart,colUnQuart,col2UnDemi">
						<t:panelGroup/>
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.ETAPES']}" for="etapes" />
						</t:panelGroup>
						<t:panelGroup>
							<e:selectOneMenu id="etapes" converter="javax.faces.Integer"
								onchange="javascript:{simulateLinkClick('monitorApplicationsForm:lookForIndividuComm');}"
								valueChangeListener="#{monitorCandidaturesController.changeTrtCmi}"
								value="#{monitorCandidaturesController.idTrtCmi}">
								<f:selectItem itemLabel="" itemValue="0" />
								<t:selectItems var="beanTrtCmi"
									value="#{trtCmiController.allTraitementCmi}"
									itemLabel="#{beanTrtCmi.etape.libWebVet}"
									itemValue="#{beanTrtCmi.traitementCmi.id}" />
							</e:selectOneMenu>
							<e:commandButton id="lookForIndividuComm"
								value="#{msgs['_.BUTTON.SEARCH']}"
								action="#{monitorCandidaturesController.makeListStudent}" />
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div style="width:100%;" 
					rendered="#{not empty monitorCandidaturesController.individus}">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<t:panelGroup>
						<e:text value="#{msgs['INDIVIDU.FOUND.NB']}"
							styleClass="section-smallTitle" style="margin-right:4px;" 
							escape="false">
							<f:param value="#{monitorCandidaturesController.sizeIndividus}"/>
						</e:text>
					</t:panelGroup>
				</e:panelGrid>

				<e:dataTable var="individuPojo" id="individuData"
					value="#{monitorCandidaturesController.individus}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false" rowIndexVar="variable">
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.numDossierOpi}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NOM']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.nomPatronymique}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.prenom}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.DATE_NAI_COURT']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.dateNaissance}">
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
					</t:column>

					<t:column>
						<t:dataTable var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.indVoeuxPojo}">
							<t:column>
								<e:text value="#{indVoeuxPojo.vrsEtape.libWebVet}" />
							</t:column>
						</t:dataTable>
					</t:column>
				</e:dataTable>

				<e:paragraph value="#{msgs['INDIVIDU.NOT.FOUND']}"
					rendered="#{empty monitorCandidaturesController.individus}" />
			</t:div>
		</h:form>




		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		hideElement('monitorApplicationsForm:lookForIndividuComm');
		hideElement('monitorApplicationsForm:lookForCmi');
	</script>
</e:page>
