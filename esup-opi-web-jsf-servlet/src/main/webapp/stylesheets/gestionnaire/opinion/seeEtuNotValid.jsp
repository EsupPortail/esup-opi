<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<script type="text/javascript">
						var userSelected = false;
						function selectUser(linkId) {
							if (!userSelected) {
							  	userSelected = true;
								simulateLinkClick(linkId);
						  	}
						}
					</script>
		<e:section value="#{msgs['INDIVIDU.TITLE.LISTE_NOT_VALID']}" />
		<e:subSection value="#{msgs['COMMISSION.SELECTED']} " >
				<f:param value="#{commissionController.commission.libelle}"/>
		</e:subSection>
		
		<e:subSection value="#{msgs['OPINION.NO_AVIS_SELECTED']}"  
			 rendered="#{empty printOpinionController.resultSelected}">
		</e:subSection>
		<e:subSection value="#{msgs['OPINION.SELECT_NON_DEFINITIF']} " 
			 rendered="#{!printOpinionController.isFinal
			 && not empty printOpinionController.resultSelected}">
			 <f:param value="#{printOpinionController.labelResultSelected}"/>
		</e:subSection>
		<e:subSection value="#{msgs['OPINION.SELECT_DEFINITIF']}" 
			 rendered="#{printOpinionController.isFinal
			 && not empty printOpinionController.resultSelected}">
			 <f:param value="#{printOpinionController.labelResultSelected}"/>
		</e:subSection>

		<h:form id="seeIndividusForm">

			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>

			<t:div style="width:100%;">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<t:panelGroup></t:panelGroup>
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.EXPORT']}" immediate="true"
							action="#{printOpinionController.makeCsvInSeeEtuVal}" />
						<e:commandButton value="#{msgs['OPINION.EDIT.VALIDATION']}"
									rendered="#{managedAccess.updateAuthorized}" immediate="true"
									action="#{printOpinionController.makePDFValidation}"/>
						<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
							action="go_valid_opinions" />
					</t:panelGroup>
				</e:panelGrid>
			</t:div>


			<t:div style="width:100%;">
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
						</t:dataTable>
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
	hideElement('seeIndividusForm:individuData:submitPageSize');
	hideElement('seeIndividusForm:individuData:submitSelectTypeTrt');
</script>
</e:page>
