<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />

		<t:div id="blockFormSearch" styleClass="blockForm"
			rendered="#{not empty managedAccess.domainAccueilGest}">
			<h:form id="lookForIndForm" styleClass="opiR1_form">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.IND']}"
						styleClass="section-smallTitle" />
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
							action="#{typeTraitController.filterPaginator}" />
					</t:panelGroup>
				</e:panelGrid>

				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['INDIVIDU.NUM_DOSSIER']}"
							for="numDossierOpi" />
					</t:panelGroup>
					<e:inputText id="numDossierOpi"
						value="#{paginatorIndividuPojo.indRechPojo.numDossierOpiRecherche}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['TYP_TRT.EXCLURE_TRAITE']}"
							for="pasIncTraites" />
					</t:panelGroup>
					<t:selectBooleanCheckbox id="pasIncTraites"
						value="#{paginatorIndividuPojo.indRechPojo.excludeWishProcessed}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['INDIVIDU.NOM']}" for="nom" />
					</t:panelGroup>
					<e:inputText id="nom"
						value="#{paginatorIndividuPojo.indRechPojo.nomRecherche}" />
					<t:panelGroup>
						<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom" />
					</t:panelGroup>
					<e:inputText id="prenom"
						value="#{paginatorIndividuPojo.indRechPojo.prenomRecherche}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
					</t:panelGroup>
					<e:selectManyCheckbox value="#{paginatorIndividuPojo.listeRI}" converter="javax.faces.Integer"
						disabled="#{!paginatorIndividuPojo.indRechPojo.canModifyRISearch}"
						layout="lineDirection">
						<t:selectItems var="ri" 
								itemLabel="#{ri.shortLabel}" itemValue="#{ri.code}"
								value="#{nomenclatureController.allRegimeInscription}"/>
					</e:selectManyCheckbox>
					<t:panelGroup>
						<e:outputLabel value="#{msgs['COMMISSION.CHOICE']}"
							for="lesCommissions" />
					</t:panelGroup>
					<t:panelGroup>
						<e:selectOneMenu id="lesCommissions"
							converter="javax.faces.Integer"
							value="#{paginatorIndividuPojo.indRechPojo.idCmi}">
							<f:selectItem itemLabel="" itemValue="" />
							<t:selectItems var="commission"
								value="#{commissionController.commissionsItemsByRight}"
								itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
						</e:selectOneMenu>
					</t:panelGroup>

					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.DATE_CREATION']}"
							for="dateCreationVoeu" />
					</t:panelGroup>
					<t:panelGroup>
						<e:inputText id="dateCreationVoeu" size="10" maxlength="8"
							value="#{paginatorIndividuPojo.indRechPojo.dateCreationVoeuRecherchee}">
							<f:convertDateTime pattern="ddMMyyyy"
								timeZone="#{sessionController.timezone}" />
							<f:validateLength minimum="8"/>
						</e:inputText>
					</t:panelGroup>

				</e:panelGrid>
			</h:form>
		</t:div>

		<t:htmlTag value="br" />
		<t:htmlTag value="br" />


		<h:form id="seeTypeTrtForm">

			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>

			<t:div style="width:100%;text-align:right;">
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						action="#{typeTraitController.update}"
						rendered="#{managedAccess.updateAuthorized
								|| managedAccess.addAuthorized}" />
					<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
						immediate="true" action="#{typeTraitController.goSeeAllTypeTraitments}" />
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}" />
					<e:commandButton id="submitSelectTypeTrt"
										value="#{msgs['_.BUTTON.CHANGE_TYP_TRT']}"
										action="#{typeTraitController.selectAllTypeAction}" />
				</t:panelGroup>
			</t:div>


			<t:div style="width:100%;">
				<e:dataTable var="individuPojo" id="individuData"
					value="#{paginatorIndividuPojo.individuPojos}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false" rowIndexVar="variable">
					<f:facet name="header">
						<h:panelGroup>
							<e:paginator id="paginatorIndividuPojo"
								paginator="#{paginatorIndividuPojo}"
								itemsName="#{msgs['INDIVIDUS']}"
								onchange="javascript:{simulateLinkClick('seeTypeTrtForm:individuData:submitPageSize');}" />
							<e:commandButton id="submitPageSize"
								action="#{paginatorIndividuPojo.forceReload}" />
						</h:panelGroup>
					</f:facet>
					<t:column width="2%">
						<t:graphicImage url="/media/images/flag_green.png"
							title="#{msgs['VOEU.FLAG_TRAITE']}"
							rendered="#{individuPojo.hasAllVoeuxTraited}" />
						<t:graphicImage url="/media/images/flag_red.png"
							title="#{msgs['VOEU.FLAG_NON_TRAITE']}"
							rendered="#{!individuPojo.hasAllVoeuxTraited}" />
					</t:column>
					<t:column width="10%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.numDossierOpi}" />
					</t:column>
					<t:column width="10%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.DATE_CREATION']}" />
						</f:facet>
						<e:text value="#{individuPojo.dateCreationDossier}">
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
					</t:column>

					<t:column width="15%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NOM_PRENOM']}" />
						</f:facet>
						<e:text
							value="#{individuPojo.individu.nomPatronymique} 
							#{individuPojo.individu.prenom}" />
					</t:column>
					<t:column width="21%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.CURSUS.DERNIER']}" />
						</f:facet>
						<e:text
							value="#{individuPojo.derniereAnneeEtudeCursus.cursus.annee}
							#{individuPojo.derniereAnneeEtudeCursus.libCur} 
							(#{individuPojo.derniereAnneeEtudeCursus.libEtb})"
							rendered="#{individuPojo.derniereAnneeEtudeCursus != null}" />
					</t:column>

					<t:column width="2%">
						<t:popup styleClass="containerPopup" style="width:500px;"
							closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
							rendered="#{individuPojo.derniereAnneeEtudeCursus != null}"
							displayAtDistanceX="-501" displayAtDistanceY="0">
							<t:graphicImage url="/media/images/informationSmall.png"
								style="cursor:pointer;" id="infoAnneeUni" />
							<f:facet name="popup">
								<h:panelGroup>
									<h:panelGrid columns="1">
										<t:outputText styleClass="titrePopupAide"
											value="#{msgs['INDIVIDU.CURSUS_UNI']}" />
										<t:panelGroup>
											<t:dataList var="indCursusScolPojo"
												value="#{individuPojo.indCursusScolPojo}"
												layout="unorderedList">
												<t:column>
													<e:text value="#{indCursusScolPojo.cursus.annee}"
														styleClass="span-text-small" />
													<e:text value=" " styleClass="span-text-small" />
													<e:text value="#{indCursusScolPojo.libCur}"
														styleClass="span-text-small" />
													<e:text value=" (" styleClass="span-text-small" />
													<e:text value="#{indCursusScolPojo.etablissement.libEtb}"
														styleClass="span-text-small" />
													<e:text value=")" styleClass="span-text-small" />
												</t:column>
											</t:dataList>
										</t:panelGroup>
									</h:panelGrid>
								</h:panelGroup>
							</f:facet>
						</t:popup>
					</t:column>


					<t:column width="30%" style="text-align:center;">
						<t:dataList var="indVoeuxPojo" id="indVoeux"
								value="#{individuPojo.indVoeuxPojo}">
								<e:ul 
									rendered="#{!indVoeuxPojo.indVoeu.haveBeTraited 
										|| !paginatorIndividuPojo.indRechPojo.excludeWishProcessed}" 
										style="margin:0px;padding:0px;">
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
								</e:ul>
						</t:dataList>
					</t:column>
					<t:columns var="typeTrt" 
					value="#{typeTraitController.typeTraitements}"  
					style="text-align:center;">
						<f:facet name="header">
							<opi:radioButton name="myRadioCol#{variable}"
								onClick="javascript:{simulateLinkClick('seeTypeTrtForm:submitSelectTypeTrt');}"
								overrideName="true"
								value="#{typeTraitController.codeTypeTrtselected}"
								itemValue="#{typeTrt.code}" itemLabel="#{typeTrt.code}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" value="#{individuPojo.indVoeuxPojo}"
							rowIndexVar="countIndVoeux">
							<e:ul style="margin:0px;padding:0px;" 
							rendered="#{!indVoeuxPojo.indVoeu.haveBeTraited 
										|| !paginatorIndividuPojo.indRechPojo.excludeWishProcessed}">
								<opi:radioButton name="trtCmiTypTrt:#{variable}:#{countIndVoeux}" overrideName="true"
									value="#{indVoeuxPojo.indVoeu.codTypeTrait}"
									itemValue="#{typeTrt.code}" />
							</e:ul>
						</t:dataList>
					</t:columns>
				</e:dataTable>
			</t:div>
		</h:form>

		<e:text value="#{msgs['INDIVIDU.NOT.FOUND']}"
			rendered="#{empty paginatorIndividuPojo.visibleItems}" />

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
	hideElement('seeTypeTrtForm:individuData:submitPageSize');
	hideElement('seeTypeTrtForm:submitSelectTypeTrt');
</script>
</e:page>
