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
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<h:form id="managedArchiveForm">

				<t:div id="div_subSection"
					rendered="#{archiveTaskController.commission.id == null}">
					<e:paragraph value="Rechercher une commission" />
					<t:div id="blockFormInfo" styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
									action="#{archiveTaskController.lookForCmi}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{managedAccess.goDisplayFunction}" />
							</t:panelGroup>
						</t:div>

						<e:panelGrid styleClass="tableWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<e:outputLabel
								value="#{msgs['FIELD_LABEL.CODE']} #{msgs['COMMISSIONS']}"
								for="commissions" />
							<e:inputText id="codEtp" size="10" maxlength="6"
								value="#{archiveTaskController.codeCmi}" />

							<e:outputLabel value="#{msgs['COMMISSIONS']}" for="commissions" />
							<e:selectOneMenu id="lesCommissions"
								converter="javax.faces.Integer"
								value="#{archiveTaskController.idCmi}">
								<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.commissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
							</e:selectOneMenu>
						</e:panelGrid>

					</t:div>
				</t:div>
				<t:div id="div_cmi_selected"
					rendered="#{archiveTaskController.commission != null}">
					<t:div styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									immediate="true" action="#{archiveTaskController.reset}" />
							</t:panelGroup>
						</t:div>
						<t:div styleClass="blockTable" style="text-align:center;">
							<e:text value="#{msgs['COMMISSION.SELECTED']}">
								<f:param value="#{archiveTaskController.commission.libelle}" />
							</e:text>
						</t:div>
					</t:div>
				</t:div>


				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormUpdateTrtCmi" styleClass="blockForm"
					rendered="#{archiveTaskController.commission != null}">
					<t:div style="width:100%;">
						<e:panelGrid styleClass="tableWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<t:panelGroup>
								<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.CAMP_ARCH']}" for="choiceCampArch" />
								<t:outputText value="*" styleClass="etoileForChpObli" />
							</t:panelGroup>
							<t:panelGroup>
								<e:selectOneMenu id="choiceCampArch"
									converter="#{campagneConverter}"
									onchange="javascript:{simulateLinkClick('managedArchiveForm:selectCampToArch');}"
									value="#{archiveTaskController.campToArch}">
									<f:selectItems value="#{archiveTaskController.campagnesInUseItems}" />
								</e:selectOneMenu>
								<e:commandButton id="selectCampToArch"
									value="#{msgs[_.BUTTON.CHANGE]}"
									action="#{archiveTaskController.selectCampToArch}" />
							</t:panelGroup>
							<t:panelGroup>
								<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.CAMP_ACT']}" for="choiceCampActive" />
								<t:outputText value="*" styleClass="etoileForChpObli" />
							</t:panelGroup>
							<t:panelGroup>
								<e:selectOneMenu id="choiceCampActive"
									converter="#{campagneConverter}"
									value="#{archiveTaskController.campToActive}">
									<f:selectItems value="#{archiveTaskController.campagnesToActiveItems}" />
								</e:selectOneMenu>
							</t:panelGroup>
							<t:panelGroup/>
							<t:panelGroup/>
							<t:panelGroup>
								<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.AFF_ARCH']}" />
							</t:panelGroup>
							<t:panelGroup style="text-align: right;">
								<e:selectBooleanCheckbox id="affArchivedCamps"
									onchange="javascript:{simulateLinkClick('managedArchiveForm:refreshCampToAct');}"
									value="#{archiveTaskController.affArchivedCamps}" />
								<e:commandButton id="refreshCampToAct"
									value="#{msgs[_.BUTTON.CHANGE]}"
									action="#{archiveTaskController.refreshCampToActFC}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:htmlTag value="br" />
					<t:div rendered="#{not empty archiveTaskController.allTraitementCmi}">
						<t:div style="width:100%;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
								styleClass="tableJustWidthMax">
								<t:panelGroup>
									<e:text value="#{msgs['FIELD_LABEL.ETAPES'] }"
										styleClass="section-smallTitle" />
									<e:text value="#{archiveTaskController.campToArch.libelle}"/>
								</t:panelGroup>
								<t:panelGroup>
									<e:commandButton value="#{msgs['_.BUTTON.ARCHIVE']}"
										action="#{archiveTaskController.archiveTrtCmi}" />
								</t:panelGroup>
							</e:panelGrid>
						</t:div>
						<e:dataTable id="trtCmi" var="beanTrtCmi"
							value="#{archiveTaskController.allTraitementCmi}" rowIndexVar="rowVar"
							styleClass="displayInfo" alternateColors="false">
							<t:column width="5%">
								<t:popup styleClass="containerPopup" style="width:500px;"
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
							<t:column width="10%">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.CGE']}" />
								</f:facet>
								<e:text value="#{beanTrtCmi.traitementCmi.versionEtpOpi.codCge}" />
							</t:column>
							<t:column width="75%">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
								</f:facet>
								<e:text value="#{beanTrtCmi.etape.libWebVet}" />
							</t:column>
							<t:column width="10%">
								<f:facet name="header">
									<t:selectBooleanCheckbox valueChangeListener="#{archiveTaskController.checkAllOn}"
										value="#{archiveTaskController.allCheckedOn}" onclick="submit();">
									</t:selectBooleanCheckbox>
								</f:facet>
								<jdt:multipleRowsSelector
										selectionList="#{archiveTaskController.objectToArch}" />
							</t:column>
						</e:dataTable>
					</t:div>
					<t:htmlTag value="br" />
					<t:div rendered="#{not empty archiveTaskController.treatmentsCmiOff}">
					
						<t:div style="width:100%;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
								styleClass="tableJustWidthMax">
								<t:panelGroup>
									<e:text value="#{msgs['FIELD_LABEL.ETAPE_ARCHIVED'] }"
										styleClass="section-smallTitle" />
									<e:text value="#{archiveTaskController.campToArch.libelle}"/>
								</t:panelGroup>
								<t:panelGroup/>
							</e:panelGrid>
						</t:div>
						<e:dataTable id="trtCmiOff" var="beanTrtCmiOff"
							value="#{archiveTaskController.treatmentsCmiOff}" rowIndexVar="rowVar"
							styleClass="displayInfo" alternateColors="false">
							<t:column width="5%">
								<t:popup styleClass="containerPopup" style="width:500px;"
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
														value="#{beanTrtCmiOff.traitementCmi.linkTrtCmiCamp}"
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
							<t:column width="10%">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.CGE']}" />
								</f:facet>
								<e:text value="#{beanTrtCmiOff.traitementCmi.versionEtpOpi.codCge}" />
							</t:column>
							<t:column width="75%">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
								</f:facet>
								<e:text value="#{beanTrtCmiOff.etape.libWebVet}" />
							</t:column>
							<t:column width="10%">
								<f:facet name="header">
									<t:selectBooleanCheckbox valueChangeListener="#{archiveTaskController.checkAllOff}"
										value="#{archiveTaskController.allCheckedOff}" onclick="submit();">
									</t:selectBooleanCheckbox>
								</f:facet>
								<jdt:multipleRowsSelector
										selectionList="#{archiveTaskController.objectToArch}" />
							</t:column>
						</e:dataTable>
					</t:div>
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
	highlightInputAndSelect('managedArchiveForm');
	hideElement('managedArchiveForm:selectCampToArch');
	hideElement('managedArchiveForm:refreshCampToAct');
</script>
</e:page>
