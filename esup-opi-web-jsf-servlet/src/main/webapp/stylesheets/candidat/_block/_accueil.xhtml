<%@include file="../../_include/_include.jsp"%>
<h:form id="navHeaderCandidat" styleClass="opiR1_form">
	<%@include file="/stylesheets/candidat/_navigation/_navGlobal.jsp"%>
</h:form>
<t:div id="maPageCandidat" styleClass="div_body">
	<t:div id="div_subSectionCandidat" styleClass="div_subSection">
		
		<h:form id="accueilCandidatForm" styleClass="opiR1_form">
			<t:panelGroup>
				<e:text value="#{msgs['INDIVIDU.TITLE.RECAP_DOS']}" 
					styleClass="section-header"/>
				<e:text value="(#{sessionController.currentInd.campagneEnServ.libelle})"
					styleClass="span-text-small-italic" />
			</t:panelGroup>
			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />


			<t:div id="blockInfoCoord" styleClass="blockForm">
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['INDIVIDU.COORD']}"
							styleClass="section-smallTitle" />
						<e:commandButton value="#{msgs['_.BUTTON.DISPLAY_UPDATE']}"
							action="#{individuController.goSeeAccount}" />
					</e:panelGrid>
				</t:div>
				<t:panelGrid columns="5" styleClass="displayInfo"
					rowClasses="headerRow, ">
					<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
					<t:outputText value="#{msgs['INDIVIDU.NOM']}" />
					<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
					<t:outputText value="#{msgs['INDIVIDU.NAISSANCE']}" />
					<t:outputText value="#{msgs['INDIVIDU.ETAT']}" />

					<e:text
						value="#{sessionController.currentInd.individu.numDossierOpi}" />
					<e:text
						value="#{sessionController.currentInd.individu.nomPatronymique}" />
					<e:text value="#{sessionController.currentInd.individu.prenom}" />
					<e:text
						value="#{sessionController.currentInd.individu.dateNaissance}">
						<f:convertDateTime pattern="dd/MM/yyyy"
							timeZone="#{sessionController.timezone}" />
					</e:text>
					<e:text value="#{sessionController.currentInd.etat.label}" />
				</t:panelGrid>
			</t:div>
			
			<t:div id="blockRegInsc" styleClass="blockForm"
				rendered="#{sessionController.currentInd.managerAsRightsToUpdate && sessionController.canUpdateRI}">
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
							styleClass="section-smallTitle" />
						<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
							action="#{individuController.modifyRegimeInscription}" />
					</e:panelGrid>
				</t:div>
				<t:panelGrid columns="2" styleClass="tableWidthMax"
					columnClasses="col1UnDemi,col1UnDemi">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION.SELECT']}"
							for="regimeInscription" />
					</t:panelGroup>
					<t:panelGroup>
						<e:selectOneMenu id="regimeInscription"
							converter="#{regimeInscriptionConverter}"
							value="#{sessionController.currentInd.regimeInscription}">
							<f:selectItems value="#{nomenclatureController.regimeInscriptionsItemsConv}" />
						</e:selectOneMenu>
					</t:panelGroup>
				</t:panelGrid>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
			</t:div>

			<t:div styleClass="blockImportant" 
				rendered="#{not empty formulairesController.indSelectedForms
							&& not sessionController.currentInd.hideMessageInfoForm}">
				<e:panelGrid columns="2" columnClasses="image,text">
					<t:graphicImage url="/media/images/important.png" />
					<t:outputText value="#{msgs['INFO.CANDIDAT.FULL_FORMS']}" 
						escape="false" style="font-weight:bold;color:#a71817"
						/>
				</e:panelGrid>
			</t:div>
			<t:htmlTag value="br" rendered="#{not empty formulairesController.indSelectedForms
							&& not sessionController.currentInd.hideMessageInfoForm}"/>

			<t:div styleClass="blockImportant" 
				rendered="#{confirmationController.canConfirmVoeux}">
				<e:panelGrid columns="2" columnClasses="image,text">
					<t:graphicImage url="/media/images/important.png" />
					<t:outputText value="#{msgs['CONFIRMATION.INFO_MESSAGE']}" 
						rendered="#{!sessionController.currentInd.regimeInscription.displayInfoFC}"
						escape="false" style="font-weight:bold;color:#a71817"/>
					<t:outputText value="#{msgs['CONFIRMATION.INFO_MESSAGE_FC']}" 
						rendered="#{sessionController.currentInd.regimeInscription.displayInfoFC}"
						escape="false" style="font-weight:bold;color:#a71817"/>
				</e:panelGrid>
			</t:div>
			<t:htmlTag value="br" rendered="#{confirmationController.canConfirmVoeux}"/>
			

			<%@include file="_candidatures.jsp"%>
			
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			
			<e:text value="#{msgs['CALENDAR_RDV.ACCUEIL.TITRE_RDV']}" styleClass="section-smallTitle"
				rendered="#{not empty saisieRdvEtuController.allRdvEtu}"/>
			<t:panelGroup rendered="#{not empty saisieRdvEtuController.allRdvEtu}">
				<e:dataTable var="dateRdv" 
					value="#{saisieRdvEtuController.keyMapAllRdvEtu}"
					alternateColors="false" renderedIfEmpty="false"
					styleClass="displayInfo">
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.DATE']}"/>
						</f:facet>
						<e:text value="#{dateRdv}">
							<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
							timeZone="#{sessionController.timezone}" />
						</e:text>
					</t:column>
					
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.MESSAGE']}"/>
						</f:facet>
						<e:text escape="false" value="#{saisieRdvEtuController.allRdvEtu[dateRdv][0].messageRdv}"/>
					</t:column>
					
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.TITRE_VOEU']}"/>
						</f:facet>
						<t:dataList var="individuDatePojo" value="#{saisieRdvEtuController.allRdvEtu[dateRdv]}">
							<e:text value="#{individuDatePojo.voeuRdv.vrsEtape.libWebVet}"/>
						</t:dataList>
					</t:column>
				</e:dataTable>
			</t:panelGroup>
			
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="blockInfoDossier" styleClass="blockForm">
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['INDIVIDU.DOS_ELEC']}"
							styleClass="section-smallTitle" />
					</e:panelGrid>
				</t:div>
				<e:panelGrid columns="3" styleClass="displayInfo"
					columnClasses=",,buttonTD" rowClasses="headerRow,,,,,">
					<t:outputText value="#{msgs['INFO.SAISIE']}" />
					<t:outputText value="#{msgs['INDIVIDU.ETAT']}" />
					<t:outputText value="#{msgs['INDIVIDU.DISPLAY_UPDATE']}" />

					<e:commandButton value="#{msgs['INDIVIDU.BAC']}"
						styleClass="form-button-link" immediate="true"
						action="#{indBacController.goSeeIndBac}"
						rendered="#{sessionController.currentInd.canUpdateIndCursusPro}" />
					<e:text value="#{msgs['INDIVIDU.BAC']}"
						rendered="#{!sessionController.currentInd.canUpdateIndCursusPro}" />
					<e:text value="#{sessionController.currentInd.etatIndBac}"/>
					<t:panelGroup>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							action="#{indBacController.goSeeIndBac}"
							title="#{msgs['_.BUTTON.DISPLAY']}"
							rendered="#{sessionController.currentInd.canUpdateBac}" />
					</t:panelGroup>

					<e:commandButton value="#{msgs['INDIVIDU.SITUATION']}"
						styleClass="form-button-link" immediate="true"
						action="#{situationController.goSeeSituation}"
						rendered="#{sessionController.currentInd.canUpdateIndCursusPro
								&& sessionController.currentInd.regimeInscription.displayInfoSituation}" />
					<e:text value="#{msgs['INDIVIDU.SITUATION']}"
						rendered="#{!sessionController.currentInd.canUpdateIndCursusPro
								&& sessionController.currentInd.regimeInscription.displayInfoSituation}" />
					<e:text value="#{situationController.etatSituation}"
						rendered="#{sessionController.currentInd.regimeInscription.displayInfoSituation}" />
					<t:panelGroup
							rendered="#{sessionController.currentInd.canUpdateIndCursusPro
								&& sessionController.currentInd.regimeInscription.displayInfoSituation}" >
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							action="#{situationController.goSeeSituation}"
							title="#{msgs['_.BUTTON.DISPLAY']}"/>
					</t:panelGroup>

					<e:text value="#{msgs['INDIVIDU.CURSUS_UNI']}"
						rendered="#{!sessionController.currentInd.canUpdateIndCursusPro}" />
					<e:commandButton value="#{msgs['INDIVIDU.CURSUS_UNI']}"
						styleClass="form-button-link" immediate="true"
						action="#{cursusController.goSeeCursusScol}"
						rendered="#{sessionController.currentInd.canUpdateIndCursusPro}" />
					<e:text value="#{sessionController.currentInd.etatIndCursusScol}" />
					<t:panelGroup>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							action="#{cursusController.goSeeCursusScol}"
							title="#{msgs['_.BUTTON.DISPLAY']}"
							rendered="#{sessionController.currentInd.canUpdateIndCursusScol}" />
					</t:panelGroup>

					<t:panelGroup>
						<e:text value="#{msgs['INDIVIDU.CURSUS_EXT']}"
							rendered="#{!sessionController.currentInd.canUpdateIndCursusPro}" />
						<e:commandButton value="#{msgs['INDIVIDU.CURSUS_EXT']}"
							action="#{cursusController.goSeeCursusPro}"
							styleClass="form-button-link" immediate="true"
							rendered="#{sessionController.currentInd.canUpdateIndCursusPro}" />
						<t:popup styleClass="containerPopup" style="width:350px;"
							closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
							displayAtDistanceX="-350" displayAtDistanceY="0">
							<t:graphicImage url="/media/images/informationSmall.png"
								style="cursor:pointer;" />

							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<t:outputText styleClass="titrePopupAide"
											value="#{msgs['INDIVIDU.CURSUS_EXT']}" />
										<t:panelGroup>
											<t:outputText styleClass="libellesAide"
												value="#{msgs['INDIVIDU.CURSUS_EXT.DESC_WELC']}" />
										</t:panelGroup>

									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>
					</t:panelGroup>
					<e:text value="#{sessionController.currentInd.etatIndCursusPro}" />
					<t:panelGroup>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							action="#{cursusController.goSeeCursusPro}"
							title="#{msgs['_.BUTTON.DISPLAY']}"
							rendered="#{sessionController.currentInd.canUpdateIndCursusPro}" />
					</t:panelGroup>


					<t:panelGroup>
						<e:text value="#{msgs['INDIVIDU.QUALIF.NO_DIP']}"
							rendered="#{!sessionController.currentInd.canUpdateIndCursusPro}" />
						<e:commandButton value="#{msgs['INDIVIDU.QUALIF.NO_DIP']}"
							styleClass="form-button-link" immediate="true"
							action="#{cursusController.goSeeQualif}"
							rendered="#{sessionController.currentInd.canUpdateIndCursusPro}" />
						<t:popup styleClass="containerPopup" style="width:350px;"
							closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
							displayAtDistanceX="-350" displayAtDistanceY="0">
							<t:graphicImage url="/media/images/informationSmall.png"
								style="cursor:pointer;" id="entatVoeux" />

							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<t:outputText styleClass="titrePopupAide"
											value="#{msgs['INDIVIDU.QUALIF.NO_DIP']}" />
										<t:panelGroup>
											<t:outputText styleClass="libellesAide"
												value="#{msgs['INDIVIDU.QUALIF.NO_DIP.DESC_WELC']}" />
										</t:panelGroup>

									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>
					</t:panelGroup>
					<e:text
						value="#{sessionController.currentInd.etatQualifNonDiplomante}" />
					<t:panelGroup>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							action="#{cursusController.goSeeQualif}"
							title="#{msgs['_.BUTTON.DISPLAY']}"
							rendered="#{sessionController.currentInd.canUpdateQualifNonDiplomante}" />
					</t:panelGroup>
				</e:panelGrid>
			</t:div>

			<t:htmlTag value="br" />
			<%@include file="_formulaires.jsp"%>

		</h:form>
	</t:div>
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
</t:div>
<t:div id="div_navFooterCandidat">
	<h:form id="navFooterCandidat" styleClass="opiR1_form">
		<%@include file="/stylesheets/candidat/_navigation/_navGlobal.jsp"%>
	</h:form>
</t:div>
<script type="text/javascript">
	highlightChildrenLiTags('navFooterCandidat:navigationLogin');
	highlightChildrenLiTags('navHeaderCandidat:navigationLogin');
	highlightInputAndSelect('accueilCandidatForm');
</script>
