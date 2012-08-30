<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PROFIL.TITLE.ENTER']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div styleClass="maxDivTextRight">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CHAMP']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="enterProfilForm">
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<t:panelGroup />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									rendered="#{profilController.actionEnum.whatAction == profilController.actionEnum.addAction
											&& managedAccess.addAuthorized}"
									action="#{profilController.add}" />
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									rendered="#{profilController.actionEnum.whatAction == profilController.actionEnum.updateAction
										&& managedAccess.updateAuthorized}"
									action="#{profilController.update}" />
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									action="#{profilController.goSeeAllProfil}" immediate="true" />
							</t:panelGroup>
						</e:panelGrid>

					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="code" size="11" maxlength="10"
							value="#{profilController.profil.code}" />
						<t:panelGroup>	
							<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
								for="temoinEnService" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<t:selectBooleanCheckbox id="temoinEnService" value="#{profilController.profil.temoinEnService}"/>
						
						
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
								for="libelle" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="libelle" size="51" maxlength="50"
							value="#{profilController.profil.libelle}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
								for="regimeInscription" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:selectOneMenu id="regimeInscription"
							value="#{profilController.profil.codeRI}">
							<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
						</e:selectOneMenu>
					</e:panelGrid>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormDomain" styleClass="blockForm">
					<e:panelGrid styleClass="tableWidthMax" columns="2"
						columnClasses="col1UnDemi,col2UnDemi">
						<e:outputLabel value="#{msgs['DOMAIN.CHOOSE.ONE']}" for="choiceDomain" />
						<t:panelGroup>
								<e:selectOneMenu id="choiceDomain" converter="javax.faces.Integer"
									valueChangeListener="#{profilController.selectDomain}"
									value="#{profilController.idDomainSelected}"
									onchange="javascript:{simulateLinkClick('enterProfilForm:submitSelectDomain');}">
									<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}"
										itemValue="" />
									<t:selectItems var="domain" value="#{domainController.domains}"
										itemLabel="#{domain.libelle}" itemValue="#{domain.id}" />
									<f:selectItem itemLabel="#{msgs['DOMAIN.SELECTED.ALL']}"
										itemValue="#{profilController.selectAllDomain}" />
								</e:selectOneMenu>
								<e:commandButton id="submitSelectDomain" 
										value="#{msgs['_.BUTTON.CHANGE']}"
										action="#{profilController.selectDomain}"/>
						</t:panelGroup>
					</e:panelGrid>
				
				</t:div>
				<t:div id="displayAccess">
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div id="blockDataDomain" styleClass="blockForm">
						<e:dataTable id="domainData"  var="beanAccess" value="#{profilController.accessDomain }"
							styleClass="paginatorData" alternateColors="true"
							rowIndexVar="variable"  renderedIfEmpty="false" >
							<t:column width="80%">
								<f:facet name="header">
									<t:outputText value="#{msgs['DOMAIN']}" />
								</f:facet>
								<e:text value="#{beanAccess.traitement.libelle}" />
								<t:popup styleClass="containerPopup" style="width:400px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="01"
									displayAtDistanceY="0"
									rendered="#{beanAccess.onlyReadDomain}">
									<t:graphicImage url="/media/images/informationSmall.png"
										style="cursor:pointer;" id="infoAnneeUni" />

									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{beanAccess.traitement.libelle}" />
												<t:outputText styleClass="libellesAide"
														value="#{msgs['PROFIL.INFO.DOM.READ']}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
								
							</t:column>
							<t:columns var="codeAccess" value="#{accessRController.codeAccess}" style="width:5%;">
								<f:facet name="header">
									<t:outputText value="#{accessRController.accessTypes[codeAccess].libelle} " />
								</f:facet>
								<t:selectBooleanCheckbox value="#{beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}" rendered="#{!beanAccess.onlyReadDomain}"/>
								<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{beanAccess.onlyReadDomain 
									&& beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}"/>
								<t:graphicImage url="/media/images/check0.gif" 
									rendered="#{beanAccess.onlyReadDomain 
										&& !beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}"/>
							</t:columns>
							<t:column style="width:5%;">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ALL']}" />
								</f:facet>
								<t:selectBooleanCheckbox onclick="javascript:{checkRows('enterProfilForm:domainData',#{variable}, this.checked);}"
									rendered="#{!beanAccess.onlyReadDomain}"/>
							</t:column>
						</e:dataTable>
					</t:div>
					
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div id="blockFormFonction" styleClass="blockForm">
						<e:dataTable id="functionData"  var="beanAccess" value="#{profilController.accessFct }"
							styleClass="paginatorData" alternateColors="true"
							rowIndexVar="variable" renderedIfEmpty="false" >
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['DOMAIN']}" />
								</f:facet>
								<e:text value="#{beanAccess.traitement.domain.libelle}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FUNCTION']}" />
								</f:facet>
								<e:text value="#{beanAccess.traitement.libelle }" />
							</t:column>
							<t:columns var="codeAccess" value="#{accessRController.codeAccess}" style="width:5%;">
								<f:facet name="header">
									<t:outputText value="#{accessRController.accessTypes[codeAccess].libelle} " />
								</f:facet>
								<t:selectBooleanCheckbox value="#{beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}" />
							</t:columns>
							<t:column style="width:5%;">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ALL']}" />
								</f:facet>
								<t:selectBooleanCheckbox onclick="javascript:{checkRows('enterProfilForm:functionData',#{variable}, this.checked);}"/>
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
	highlightInputAndSelect('enterProfilForm');
	hideElement('enterProfilForm:submitSelectDomain');
</script>
</e:page>
