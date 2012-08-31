<%@include file="../../_include/_include.jsp"%>
<t:div id="blockCandidatureWithVoeu" styleClass="blockForm"
	rendered="#{not empty sessionController.currentInd.indVoeuxPojo}">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['APPLICATION.BIG_TITLE']}"
				styleClass="section-smallTitle" style="margin-right:4px;"/>
				<e:text value="#{msgs['INFO.CANDIDATURE.WELCOME']}"
					styleClass="span-text-small-italic" />
			</t:panelGroup>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.CONFIRMER_DESISTER']}"
					immediate="true" action="#{confirmationController.goConfirmCandidatures}"/>
				<e:commandButton value="#{msgs['_.BUTTON.DOWNLOAD.DOSSIER']}"
					immediate="true" action="#{accueilController.printDossier}" />
				<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
					immediate="true" action="#{formationController.goSeeVoeux}" />
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:dataTable var="indVoeuPojo"
		value="#{sessionController.currentInd.indVoeuxPojo}"
		alternateColors="false" renderedIfEmpty="false"
		styleClass="displayInfo">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.FORMATION_ASK']}" />
			</f:facet>
			<e:commandButton styleClass="form-button-link" immediate="true"
				action="#{formationController.goSummaryWishes}"
				value="#{indVoeuPojo.vrsEtape.libWebVet}">
				<t:updateActionListener value="#{indVoeuPojo}"
					property="#{formationController.indVoeuPojo}" />
			</e:commandButton>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:panelGroup>
					<t:outputText value="#{msgs['INDIVIDU.ETAT']}" />
					<t:popup styleClass="containerPopup" style="width:350px;"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="-350"
								displayAtDistanceY="0">
								<t:graphicImage url="/media/images/informationSmall.png"
									style="cursor:pointer;" id="entatVoeux" />

								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<t:outputText styleClass="titrePopupAide"
												value="#{msgs['INDIVIDU.ETAT']}" />
											<t:panelGroup>
												<t:outputText styleClass="libellesAide"
													value="#{msgs['VOEU.INFO.STATE']} : " />
											</t:panelGroup>
											<t:panelGroup>
												<e:ul>
													<e:li>
														<e:text value="#{msgs['STATE.NON_ARRIVE']}"
															styleClass="span-text-small" />
														<e:text value=" : " styleClass="span-text-small" />
														<e:text value="#{msgs['STATE.NON_ARRIVE.COMMENT']}"
															styleClass="span-text-small" />
													</e:li>
													<e:li>
														<e:text value="#{msgs['STATE.ARRIVE_COMPLET']}"
															styleClass="span-text-small" />
														<e:text value=" : " styleClass="span-text-small" />
														<e:text value="#{msgs['STATE.ARRIVE_COMPLET.COMMENT']}"
															styleClass="span-text-small" />
													</e:li>
													<e:li>
														<e:text value="#{msgs['STATE.ARRIVE_INCOMPLET']}"
															styleClass="span-text-small" />
														<e:text value=" : " styleClass="span-text-small" />
														<e:text value="#{msgs['STATE.ARRIVE_INCOMPLET.COMMENT']}"
															styleClass="span-text-small" />
													</e:li>
												</e:ul>
											</t:panelGroup>
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>

				</t:panelGroup>

			</f:facet>
			<e:text value="#{indVoeuPojo.etat.label}"/>
			<e:text value="#{msgs['CALENDAR_RDV.CANDIDATURE_VALIDER']}"
				rendered="#{indVoeuPojo.etat.validerEtu}"/>
			<e:text value="#{msgs['CALENDAR_RDV.CANDIDATURE_REFUSER']}"
				rendered="#{indVoeuPojo.etat.refuserEtu}"/>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.RESULT']}" />
			</f:facet>
			<t:outputText value="#{msgs['VOEU.RESULT.NON_DISPO']}"
				rendered="#{empty indVoeuPojo.indVoeu.avis}" />
			<t:dataList id="datalist" style="myStyle"
				var="avis"
  				value="#{indVoeuPojo.indVoeu.avis}"> 	
  				<t:outputText value="#{avis.result.libelle}" 
  					rendered="#{avis.temoinEnService && avis.validation}"/>				
			</t:dataList>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
			</f:facet>
			<t:outputText value="#{msgs['VOEU.RESULT.NON_DISPO']}"
				rendered="#{empty indVoeuPojo.indVoeu.avis}" />
			<t:dataList id="datalist" style="myStyle"
				var="avis2"
  				value="#{indVoeuPojo.indVoeu.avis}"> 	
  				<t:outputText value="#{avis2.commentaire}" 
  					rendered="#{avis2.temoinEnService && avis2.validation}"/>				
			</t:dataList>
		</t:column>
	</e:dataTable>
</t:div>
<t:div id="blockCandidatureWithoutVoeu"
	rendered="#{empty sessionController.currentInd.indVoeuxPojo}">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['APPLICATION.BIG_TITLE']}"
				styleClass="section-smallTitle" />
			<e:commandButton value="#{msgs['_.BUTTON.MAKE.CHOICE']}"
				immediate="true"
				rendered="#{sessionController.currentInd.isManager || 
								(sessionController.currentInd.etat.canDoChoiceEtape
											&& sessionController.currentInd.regimeInscription.calInsIsOpen)}"
				action="#{formationController.goSearchFormation}" />
		</e:panelGrid>
	</t:div>

	<t:div styleClass="blockTable" style="text-align:center;">
		<e:text value="#{msgs['INDIVIDU.WITHOUT_CANDI']}" style="font-weight:bold;font-size:16px;"
			rendered="#{sessionController.currentInd.etat.canDoChoiceEtape && sessionController.currentInd.regimeInscription.calInsIsOpen}" />
		<e:text value="#{msgs['INDIVIDU.INFO.STATE_INCOMPLET']}"
			rendered="#{!sessionController.currentInd.etat.canDoChoiceEtape}" />
		<e:text value="#{msgs['INDIVIDU.INFO.CAL_CLOSE']}"
			rendered="#{!sessionController.currentInd.regimeInscription.calInsIsOpen}" />
	</t:div>
</t:div>