<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CONFIRMATION.TITLE']}" />
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="choiceEtapeForm" styleClass="opiR1_form">
				<t:div id="div_subSection" styleClass="div_subSection">
					<t:div id="blockCandidaturesConfirm" styleClass="blockForm">

						<t:div styleClass="messageForUser">
							<e:messages />
						</t:div>
						<t:htmlTag value="br" />
						<t:div style="width:100%;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
								styleClass="tableJustWidthMax">
								<t:panelGroup>
									<e:text value="#{msgs['_.BUTTON.CONFIRMER_DESISTER']}"
										styleClass="section-smallTitle" style="margin-right:4px;" />
								</t:panelGroup>

								<t:panelGroup>
									<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
										rendered="#{sessionController.currentInd.asRightsToUpdate}"
										action="#{confirmationController.finishCandidature}" />
									<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
										immediate="true"
										action="#{accueilController.goWelcomeCandidat}" />
								</t:panelGroup>
							</e:panelGrid>
						</t:div>
						<e:dataTable var="indVoeuPojo"
							value="#{confirmationController.indVoeuxPojoFav}"
							alternateColors="false" renderedIfEmpty="false"
							styleClass="displayInfo" rowIndexVar="rowVar">
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ETAPES']}" />
								</f:facet>
								<e:text value="#{indVoeuPojo.vrsEtape.libWebVet}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<e:text value="#{msgs['STATE.CONFIRM.TITLE']}" />
								</f:facet>
								<opi:radioButton id="myRadioId1" name="myRadioCol#{rowVar}"
									overrideName="true" value="#{indVoeuPojo.stateConf}"
									itemValue="STATE.CONFIRM" disabled="#{indVoeuPojo.disableConfirm}"/>
							</t:column>
							<t:column>
								<f:facet name="header">
									<e:text value="#{msgs['STATE.DESIST.TITLE']}" />
								</f:facet>
								<opi:radioButton id="myRadioId2" name="myRadioCol#{rowVar}"
									overrideName="true" value="#{indVoeuPojo.stateConf}"
									itemValue="STATE.DESIST" disabled="#{indVoeuPojo.disableConfirm}"/>
							</t:column>
							<t:column>
								<f:facet name="header">
									<e:text value="#{msgs['CALENDAR_RDV.TEXT.RDV.TEXT']}" />
								</f:facet>
								<e:commandButton image="/media/images/calendar.png"
									styleClass="form-button-image" immediate="true"
									title="#{msgs['CALENDAR_RDV.ADD.TITLE']}"
									rendered="#{indVoeuPojo.isEtatConfirme 
												&& (!indVoeuPojo.hasIAForVoeu || !indVoeuPojo.hasNNE)
												&& indVoeuPojo.individuDate == null
												&& indVoeuPojo.calendrierRdv != null}"
									action="#{saisieRdvEtuController.goAddRdvEtu}">
									<t:updateActionListener
										value="#{indVoeuPojo}"
										property="#{saisieRdvEtuController.voeu}"/>
								</e:commandButton>
								<e:text value="#{indVoeuPojo.individuDate.dateRdv}"
									rendered="#{indVoeuPojo.individuDate != null}">
									<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
										timeZone="#{sessionController.timezone}" />
								</e:text>
								
							</t:column>
						</e:dataTable>
					</t:div>
				</t:div>
			</h:form>
			<t:div styleClass="messageForUser"
					rendered="#{confirmationController.hasIAForVoeux }">
				<e:text value="#{confirmationController.htmlBlockIAWeb}" escape="false"/>
			</t:div>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>

</e:page>
