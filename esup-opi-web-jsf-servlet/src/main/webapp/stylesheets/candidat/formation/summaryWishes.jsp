<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['FORMATION.TITLE.INFO']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="summaryWishesForm" styleClass="opiR1_form">
				<t:div id="blockFormEtape" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<t:panelGroup />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.PRINT.HERE']}"
									immediate="true" onclick="window.print();" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
									immediate="true"
									action="#{accueilController.goWelcomeCandidat}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
				</t:div>
			</h:form>
			<t:dataList var="summaryWishes"
				value="#{formationController.summaryWishes}">
				<t:div styleClass="block-red" style="margin-top:10px;">
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div rendered="#{summaryWishes.canDonwload}">
						<t:div>
							<e:panelGrid columns="2" columnClasses="col90,col10TextRight"
								styleClass="tableJustWidthMax">
								<t:panelGroup
									rendered="#{!sessionController.currentInd.regimeInscription.displayInfoFC}">
									<e:text value="#{msgs['FORMATION.SUMMARY.DAT_DOS']} "
										styleClass="section-black-smallTitle" />
									<e:text
										value="#{summaryWishes.commission.commission.calendarCmi.datEndBackDossier}"
										styleClass="section-black-smallTitle">
										<f:convertDateTime pattern="dd/MM/yyyy"
											timeZone="#{sessionController.timeZone}" />
									</e:text>
								</t:panelGroup>
								<t:panelGroup
									rendered="#{sessionController.currentInd.regimeInscription.displayInfoFC}">
									<e:text value="#{msgs['FORMATION.SUMMARY.QUINZAINE_FC']} "
										styleClass="section-black-smallTitle" />
								</t:panelGroup>
								<h:form id="printDossier" styleClass="opiR1_form">
									<e:commandButton
										value="#{msgs['_.BUTTON.DOWNLOAD.MY_DOSSIER']}"
										immediate="true" action="#{accueilController.validatePrintOneDossier}">
										<t:updateActionListener value="#{summaryWishes.commission}"
											property="#{accueilController.object}" />
									</e:commandButton>
								</h:form>
							</e:panelGrid>

						</t:div>
						<t:div styleClass="blockTable">
							<h:form id="dossiersSelect" styleClass="opiR1_form">
								<t:dataList var="indVoeuPojo" value="#{summaryWishes.vows}"
									layout="unorderedList">
									<e:panelGrid styleClass="tableJustWidthMax"
										columnClasses="portlet-table-text-left">
										<t:panelGroup>
											<e:text value="#{indVoeuPojo.vrsEtape.libWebVet} " />
											<t:panelGroup
												rendered="#{formulairesController.formulairesCmi[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null
															&& indVoeuPojo.etat.displayForms}">
												<e:commandButton value="#{msgs['FORMULAIRE.ACCESS']}"
													style="margin-right:5px;"
													action="#{formulairesController.createResponseInRunner}"
													rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] == null && formulairesController.indSelectedForms[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
													<t:updateActionListener value="#{indVoeuPojo.indVoeu}"
														property="#{formulairesController.indVoeuSelected}" />
												</e:commandButton>
												<h:outputLink target="_blank" styleClass="form-button"
													style="margin-right:5px;"
													value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
													-#{sessionController.currentInd.regimeInscription.shortLabel}/edit/#{sessionController.currentInd.individu.numDossierOpi}"
													rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
													<e:text value="#{msgs['FORMULAIRE.EDIT2']}" />
												</h:outputLink>
												<h:outputLink target="_blank" styleClass="form-button-image"
													value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
													-#{sessionController.currentInd.regimeInscription.shortLabel}/view/#{sessionController.currentInd.individu.numDossierOpi}"
													rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
													<t:graphicImage value="/media/images/magnifier.png"
														title="#{msgs['_.BUTTON.DISPLAY']}" />
												</h:outputLink>
											</t:panelGroup>
										</t:panelGroup>
									</e:panelGrid>
								</t:dataList>
							</h:form>
						</t:div>
					</t:div>

					<t:div rendered="#{!summaryWishes.canDonwload}">
						<t:div>
							<e:paragraph value="#{msgs['FORMATION.SUMMARY.LIST_FORM']} " />
						</t:div>
						<t:div styleClass="blockTable">
							<h:form id="dossiers" styleClass="opiR1_form">
								<t:dataList var="indVoeuPojo" value="#{summaryWishes.vows}"
									layout="unorderedList">
									<e:panelGrid styleClass="tableJustWidthMax">
										<t:panelGroup>
											<e:text value="#{indVoeuPojo.vrsEtape.libWebVet}" />
											<e:commandButton value="#{msgs['FORMULAIRE.ACCESS']}"
												style="margin-right:5px;"
												action="#{formulairesController.createResponseInRunner}"
												rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] == null && formulairesController.indSelectedForms[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
												<t:updateActionListener value="#{indVoeuPojo.indVoeu}"
													property="#{formulairesController.indVoeuSelected}" />
											</e:commandButton>
											<h:outputLink target="_blank" styleClass="form-button"
												style="margin-right:5px;"
												value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
												-#{sessionController.currentInd.regimeInscription.shortLabel}/edit/#{sessionController.currentInd.individu.numDossierOpi}"
												rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
												<e:text value="#{msgs['FORMULAIRE.EDIT2']}" />
											</h:outputLink>
											<h:outputLink target="_blank" styleClass="form-button-image"
												value="#{formulairesController.orbeonOpiUrl}#{indVoeuPojo.vrsEtape.codEtp}-#{indVoeuPojo.vrsEtape.codVrsVet}
												-#{sessionController.currentInd.regimeInscription.shortLabel}/view/#{sessionController.currentInd.individu.numDossierOpi}"
												rendered="#{formulairesController.indFormulaires[indVoeuPojo.indVoeu.linkTrtCmiCamp.traitementCmi.versionEtpOpi] != null}">
												<t:graphicImage value="/media/images/magnifier.png"
													title="#{msgs['_.BUTTON.DISPLAY']}" />
											</h:outputLink>
										</t:panelGroup>
									</e:panelGrid>
								</t:dataList>
							</h:form>
						</t:div>
						<t:div>
							<e:paragraph
								value="#{msgs['FORMATION.SUMMARY.DOS_PRE_ANALYSE']} "
								escape="false" style="font-weight:bold;" />
							<e:ul>
								<e:li>
									<e:text value="#{msgs['FORMATION.SUMMARY.DOS_PRE_ANALYSE.1']} " />
								</e:li>
								<e:li>
									<e:text value="#{msgs['FORMATION.SUMMARY.DOS_PRE_ANALYSE.2']} " />
									<e:text
										value="#{summaryWishes.commission.commission.calendarCmi.datEndBackDossier}">
										<f:convertDateTime pattern="dd/MM/yyyy"
											timeZone="#{sessionController.timeZone}" />
									</e:text>
								</e:li>
							</e:ul>
						</t:div>
					</t:div>
					<t:htmlTag value="br" />
					<t:div>
						<e:paragraph value="#{msgs['FIELD_LABEL.SEND_TO.THIS_ADR']}" />
						<t:div>
							<t:div>
								<e:text
									value="#{summaryWishes.commission.contactCommission.corresponding}" />
							</t:div>
							<t:div>
								<e:text
									value="#{summaryWishes.commission.adressePojo.adresse.adr1}" />
							</t:div>
							<t:div>
								<e:text
									value="#{summaryWishes.commission.adressePojo.adresse.adr2}"
									rendered="#{summaryWishes.commission.adressePojo.adresse.adr2 != null 
										&& summaryWishes.commission.adressePojo.adresse.adr2 != ''}" />
							</t:div>
							<t:div>
								<e:text
									value="#{summaryWishes.commission.adressePojo.adresse.adr3}"
									rendered="#{summaryWishes.commission.adressePojo.adresse.adr3 != null 
										&& summaryWishes.commission.adressePojo.adresse.adr3 != ''}" />
							</t:div>
							<t:div>
								<e:text
									value="#{summaryWishes.commission.adressePojo.adresse.codBdi} " />
								<e:text
									value="#{summaryWishes.commission.adressePojo.commune.libCommune} " />
								<e:text value="#{msgs['ADRESS.CEDEX']} "
									rendered="#{summaryWishes.commission.adressePojo.isCedex}" />
								<e:text
									value="#{summaryWishes.commission.adressePojo.adresse.cedex}"
									rendered="#{summaryWishes.commission.adressePojo.isCedex}" />
							</t:div>
							<t:div>
								<e:text
									value="#{msgs['FIELD_LABEL.COURRIEL']} : #{summaryWishes.commission.adressePojo.adresse.mail}" />
							</t:div>
							<t:div>
								<e:text
									value="#{msgs['FIELD_LABEL.SHORT_TEL']}. : #{summaryWishes.commission.adressePojo.adresse.phoneNumber}" />
							</t:div>
							<t:div>
								<e:text
									value="#{msgs['ADRESS.TEL_FAX']}. : #{summaryWishes.commission.adressePojo.adresse.faxNumber}" />
							</t:div>
						</t:div>
					</t:div>
					<t:htmlTag value="br" />
					<t:div rendered="#{not empty summaryWishes.commission.contactCommission.managerName}" >
						<e:paragraph value="#{msgs['FORMATION.SUMMARY.MANAGER']}" />
						<t:div>
							<e:text
								value="#{summaryWishes.commission.contactCommission.managerName}" />
						</t:div>
						<t:div>
							<e:text
								value="#{msgs['FIELD_LABEL.COURRIEL']} : #{summaryWishes.commission.contactCommission.managerMail}" />
						</t:div>
						<t:div>
							<e:text
								value="#{msgs['FIELD_LABEL.SHORT_TEL']}. : #{summaryWishes.commission.contactCommission.managerPhone}" />
						</t:div>
					</t:div>




					<t:htmlTag value="br" />
					<h:form id="affichPieces" styleClass="opiR1_form">
					<t:div>
						<e:paragraph value="#{msgs['FORMATION.SUMMARY.LIST_PJ']}" />
						<t:div styleClass="blockTable">
							<t:dataList var="pieceJ" value="#{summaryWishes.pieces}"
								layout="orderedList">
								<e:text value="#{pieceJ.libelle}" />
								
								<e:commandButton style="margin-right:5px;"
												action="#{formulairesController.download}"
												value="#{pieceJ.nomDocument}"
												rendered="#{pieceJ.nomDocument!=null}">
												<t:updateActionListener value="#{pieceJ.nomDocument}"
									property="#{formulairesController.nomDocument}" />
								</e:commandButton>
							</t:dataList>
						</t:div>
					</t:div>
					</h:form>

					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
				</t:div>
			</t:dataList>
		</t:div>
		<t:div styleClass="blockImportant" style="margin-top:15px;">
			<e:panelGrid columns="2" columnClasses="image,text">
				<t:graphicImage url="/media/images/important.png" />
				<t:outputText value="#{msgs['INDIVIDU.INFO.CONSULT_MAIL']}"
					escape="false" />
			</e:panelGrid>
		</t:div>
	</t:div>
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigationLogin');
	highlightChildrenLiTags('navigationFooter:navigationLogin');
</script>
</e:page>
