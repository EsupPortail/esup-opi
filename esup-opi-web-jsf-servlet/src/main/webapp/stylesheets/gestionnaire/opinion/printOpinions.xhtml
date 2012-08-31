<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>

		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		
		<t:div id="div_subSectionPdf" styleClass="div_subSection"
			rendered="#{printOpinionController.actionEnum.whatAction 
				== printOpinionController.actionEnum.confirmAction}">
				<%@include file="_block/_confirmPdfNotification.jsp"%>
		</t:div>
		<t:div id="choiceColumnExport" 
			rendered="#{printOpinionController.actionEnum.whatAction == printOpinionController.actionEnum.choiceColExport}">
				<%@include file="_block/_choiceColumnExport.jsp"%>
		</t:div>
		
		<h:form id="lookForOpinionForm" styleClass="opiR1_form"
			rendered="#{printOpinionController.actionEnum.whatAction != printOpinionController.actionEnum.choiceColExport}">		
			<t:div id="blockFormSearch" styleClass="blockForm">
				<t:div style="text-align:right;">
					<e:commandButton value="#{msgs['_.BUTTON.DISPLAY']}"
						action="#{printOpinionController.seeCandidats}" 
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
					<e:commandButton value="#{msgs['_.BUTTON.CHOICE_COL']}" immediate="true">
						<t:updateActionListener 
							value="#{printOpinionController.actionEnum.choiceColExport}"
							 property="#{printOpinionController.actionEnum.whatAction}"/>
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.EXPORT']}" 
						action="#{printOpinionController.makeCsvValidation}" 
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
					<e:commandButton value="#{msgs['OPINION.EDIT.VALIDATION']}" 
						action="#{printOpinionController.printPDFValidation}"
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
					<e:commandButton value="#{msgs['OPINION.EDIT.NOTIFICATION']}" 
						action="#{printOpinionController.printPDFAllNotifications}"
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}" 
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
				</t:div>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['AVIS.LABEL_DECISION']}" for="opinion" />
					</t:panelGroup>
					<t:panelGroup>
						<e:dataTable var="typeDec" id="typeOpinionData" 
							value="#{nomenclatureController.typeDecisionsSorted}"
							alternateColors="false" align="center"
							renderedIfEmpty="false" rowIndexVar="variable">
							<t:column >
								<t:div	style="padding:0px;margin:0px;">
									<jdt:multipleRowsSelector 
										selectionList="#{printOpinionController.resultSelected}" />
									<e:text value="#{typeDec.libelle }" />
								</t:div>
							</t:column>
						</e:dataTable>
					</t:panelGroup>
					<t:panelGroup>
						<e:outputLabel value="#{msgs['OPINION.SELECT_CANDIDAT.LABEL']}" for="choixValid" />
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
						<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
						<e:outputLabel value="#{msgs['COMMISSIONS']}" for="lacommision" />
					</t:panelGroup>
					<t:panelGroup>
						<e:selectOneRadio id="choixValid"
							value="#{printOpinionController.selectValid}">
  								<f:selectItem itemLabel="#{msgs['OPINION.SELECT_CANDIDAT.NOT_VALID']}" itemValue="#{false}" />
  								<f:selectItem itemLabel="#{msgs['OPINION.SELECT_CANDIDAT.VALID']}" itemValue="#{true}" />
						</e:selectOneRadio>
						<e:selectManyCheckbox value="#{commissionController.listeRI}" converter="#{regimeInscriptionConverter}"
							disabled="#{!paginatorIndividu.indRechPojo.canModifyRISearch}"
							layout="lineDirection">
							<t:selectItems var="ri" 
									itemLabel="#{ri.shortLabel}" itemValue="#{ri}"
									value="#{nomenclatureController.allRegimeInscription}"/>
						</e:selectManyCheckbox>
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
						<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
							value="#{printOpinionController.idCommissionSelected}">
							<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.commissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
						</e:selectOneMenu>
					</t:panelGroup>
				</e:panelGrid>
		
			</t:div>
		
			<t:div style="width:100%;">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<t:panelGroup>
						<e:text value="#{msgs['INDIVIDU.FOUND']}"
							styleClass="section-smallTitle" style="margin-right:4px;"/>
					</t:panelGroup>
				</e:panelGrid>
			
				<e:dataTable var="individuPojo" id="individuData"
					value="#{printOpinionController.lesIndividus}"
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
								<t:popup styleClass="containerPopup" style="width:350px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="-50"
									displayAtDistanceY="0">
									<e:text value="#{indVoeuxPojo.shortLibVet}"
										style="cursor:pointer;" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}" />
												<t:outputText styleClass="libellesAide"
													value="#{indVoeuxPojo.vrsEtape.libWebVet}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</t:column>
							<t:column>
								<e:text value="#{indVoeuxPojo.avisEnService.result.libelle}" />
							</t:column>
							<t:column>
								<e:commandButton 
									image="/media/images/email.png"
									styleClass="form-button-image" title="#{msgs['INFO.MAIL.TEST']}" immediate="true"
									action="#{validOpinionController.sendOneMail}">
									<t:updateActionListener value="#{indVoeuxPojo}"
											 property="#{validOpinionController.indVoeuPojo}"/>
									<t:updateActionListener value="#{individuPojo}"
										property="#{printOpinionController.individuPojoSelected}" />		 
								</e:commandButton>
							</t:column>
							<t:column>
								<e:commandButton 
									image="/media/images/emailCandidat.png"
									styleClass="form-button-image" title="#{msgs['INFO.MAIL.RESEND']}" immediate="true"
									action="#{validOpinionController.sendOneMailCandidat}">
									<t:updateActionListener value="#{indVoeuxPojo}"
											 property="#{validOpinionController.indVoeuPojo}"/>
									<t:updateActionListener value="#{individuPojo}"
										property="#{printOpinionController.individuPojoSelected}" />		 
								</e:commandButton>
							</t:column>
						</t:dataTable>
					</t:column>
					<t:column>
						<e:commandButton image="/media/images/printer.jpg"
								styleClass="form-button-image"
								action="#{printOpinionController.printOneNotification}"
								title="#{msgs['INFO.CANDIDAT.NOTIFICATION']}"
								rendered="#{printOpinionController.actionEnum.whatAction 
									!= printOpinionController.actionEnum.confirmAction}" >
								<t:updateActionListener value="#{individuPojo}"
									property="#{printOpinionController.individuPojoSelected}" />
						</e:commandButton>
					</t:column>
				</e:dataTable>
				
				<e:paragraph value="#{msgs['INDIVIDU.NOT.FOUND']}"
					rendered="#{empty printOpinionController.lesIndividus}" />
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
		hideElement('lookForOpinionForm:submitSelectTypeOpinion');
	</script>
</e:page>
