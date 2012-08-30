<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionFirstForm" styleClass="div_subSection">
			<t:div id="div_subSectionTransfert" styleClass="div_subSection"
				rendered="#{not empty trtCmiController.listVETToTransfert}">
				<%@include file="_commission/_transfertTrtCmi.jsp"%>
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<h:form id="enterTrtCmiForm">

				<t:div id="div_subSection"
					rendered="#{trtCmiController.commission.id == null}">
					<e:paragraph value="Rechercher une commission" />
					<t:div id="blockFormInfo" styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
									action="#{trtCmiController.lookForCmi}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{trtCmiController.goBackFunction}" />
							</t:panelGroup>
						</t:div>

						<e:panelGrid styleClass="tableWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<e:outputLabel
								value="#{msgs['FIELD_LABEL.CODE']} #{msgs['COMMISSIONS']}"
								for="commissions" />
							<e:inputText id="codEtp" size="10" maxlength="10"
								value="#{trtCmiController.codeCmi}" />

							<e:outputLabel value="#{msgs['COMMISSIONS']}" for="commissions" />
							<e:selectOneMenu id="lesCommissions"
								converter="javax.faces.Integer"
								value="#{trtCmiController.idCmi}">
								<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.allCommissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
							</e:selectOneMenu>
						</e:panelGrid>

					</t:div>
				</t:div>
				<t:div id="info_valider" styleClass="messageForUser"
					rendered="#{trtCmiController.commission != null}">
					<e:text styleClass="msg-info" value="#{msgs['INFO.VALIDATE']}" />
				</t:div>
				<t:htmlTag value="br"
					rendered="#{trtCmiController.commission != null}" />
				<t:htmlTag value="br"
					rendered="#{trtCmiController.commission != null}" />
				<t:div id="div_cmi_selected"
					rendered="#{trtCmiController.commission != null}">
					<t:div styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{trtCmiController.update}" />
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									immediate="true" action="#{trtCmiController.reset}" />

							</t:panelGroup>
						</t:div>
						<t:div styleClass="blockTable" style="text-align:center;">
							<e:text value="#{msgs['COMMISSION.SELECTED']}">
								<f:param value="#{trtCmiController.commission.libelle}" />
							</e:text>
						</t:div>
					</t:div>
				</t:div>


				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormUpdateTrtCmi" styleClass="blockForm"
					rendered="#{trtCmiController.commission != null}">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FIELD_LABEL.ETAPES']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['CAMPAGNES.TITLE.MANAGED_ETAPES']}"
									action="#{trtCmiController.managedCamp}"/>
								<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
									action="#{etapeController.goSearchEtpForCmi}" />
								<e:commandButton id="submitSelectTypeTrt"
									value="#{msgs['_.BUTTON.CHANGE_TYP_TRT']}"
									action="#{trtCmiController.selectAllTypeTrt}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable id="trtCmi" var="beanTrtCmi"
						value="#{trtCmiController.allTraitementCmi}" rowIndexVar="rowVar"
						styleClass="displayInfo" alternateColors="false">
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CGE']}" />
							</f:facet>
							<e:text value="#{beanTrtCmi.traitementCmi.versionEtpOpi.codCge}" />
						</t:column>
						<t:column width="35%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
							</f:facet>
							<e:text value="#{beanTrtCmi.etape.libWebVet}" />
						</t:column>
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CAMPAGNES']}" />
							</f:facet>
							<t:popup styleClass="containerPopup" style="width:300px;"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="01"
								displayAtDistanceY="0">
								<t:graphicImage url="/media/images/informationSmall.png"
									style="cursor:pointer;" id="allcampagnes" />

								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<t:outputText styleClass="titrePopupAide"
												value="#{msgs['FIELD_LABEL.CAMPAGNES']}" />
											<t:panelGroup>
												<t:outputText styleClass="libellesAide"
													value="#{msgs['CAMPAGNES.INFO.VET']} : " />
											</t:panelGroup>
											<t:panelGroup>
												<t:dataList var="link"
													value="#{beanTrtCmi.traitementCmi.linkTrtCmiCamp}"
													layout="unorderedList">
													<t:column>
														<e:text value="#{link.campagne.code}"
															styleClass="span-text-small" />
														<e:text value=" : " styleClass="span-text-small" />
														<e:text value="#{link.campagne.libelle}"
															styleClass="span-text-small" />
													</t:column>
												</t:dataList>
											</t:panelGroup>
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>

						</t:column>
						<t:columns var="typeTrt"
							value="#{nomenclatureController.typeTrts}">
							<f:facet name="header">
								<opi:radioButton name="myRadioCol"
									onClick="javascript:{simulateLinkClick('enterTrtCmiForm:submitSelectTypeTrt');}"
									overrideName="true"
									value="#{trtCmiController.codeTypeTrtselected}"
									itemValue="#{typeTrt.code}" itemLabel="#{typeTrt.code}" />
							</f:facet>
							<opi:radioButton name="trtCmiTypTrt#{rowVar}" overrideName="true"
								value="#{beanTrtCmi.traitementCmi.codTypeTrait}"
								itemValue="#{typeTrt.code}" />
						</t:columns>
						<t:column width="3%">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								action="#{trtCmiController.removeTrtCmi}"
								title="#{msgs['_.BUTTON.DELETE']}">
								<t:updateActionListener value="#{beanTrtCmi}"
									property="#{trtCmiController.beanTrtCmi}" />
							</e:commandButton>
						</t:column>
					</e:dataTable>
					<%@include file="_commission/_enterTrtCmiOff.jsp"%>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />

				<t:div id="blockFormOrbeon" styleClass="blockForm"
					rendered="#{formulairesController.orbeonBuilderUrl != null 
					&& trtCmiController.commission != null}">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FORMULAIRE']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="1"
						columnClasses="colUnQuart">
						<e:text value="#{msgs['FORMULAIRE.INFO']}" />

						<t:dataList id="trtCmiForm" var="beanTrtCmi"
							value="#{trtCmiController.allTraitementCmi}">

							<e:panelGrid columns="2">
								<e:text value="#{beanTrtCmi.etape.libWebVet}" />

								<t:panelGroup rendered="#{formulairesController.modifiable}">
									<e:commandButton value="#{msgs['FORMULAIRE.NEW']}"
										action="#{formulairesController.createFormInBuilder}"
										rendered="#{formulairesController.formulairesCmi[beanTrtCmi.traitementCmi.versionEtpOpi] == null}">
										<t:updateActionListener value="#{beanTrtCmi.traitementCmi}"
											property="#{formulairesController.traitementCmiSelected}" />
									</e:commandButton>
									<h:outputLink target="_blank" styleClass="form-button"
										value="#{formulairesController.orbeonBuilderUrl}#{beanTrtCmi.traitementCmi.versionEtpOpi.codEtp}-#{beanTrtCmi.traitementCmi.versionEtpOpi.codVrsVet}
										-#{trtCmiController.currentRegime.shortLabel}"
										rendered="#{formulairesController.formulairesCmi[beanTrtCmi.traitementCmi.versionEtpOpi] != null}">
										<e:text value="#{msgs['FORMULAIRE.EDIT']}"/>
									</h:outputLink>
									<e:commandButton value="#{msgs['FORMULAIRE.DELETE']}"
										action="#{formulairesController.deleteFormInBuilder}"
										rendered="#{formulairesController.formulairesCmi[beanTrtCmi.traitementCmi.versionEtpOpi] != null}">
										<t:updateActionListener value="#{beanTrtCmi.traitementCmi}"
											property="#{formulairesController.traitementCmiSelected}" />
									</e:commandButton>
								</t:panelGroup>
								<t:panelGroup rendered="#{not formulairesController.modifiable}" />
							</e:panelGrid>
						</t:dataList>
						<e:text value="#{msgs['FORMULAIRE.WARN']}" />
					</e:panelGrid>
				</t:div>

			</h:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	highlightInputAndSelect('enterTrtCmiForm');
	hideElement('enterTrtCmiForm:submitSelectTypeTrt');
</script>
</e:page>
