<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['GESTIONNAIRE.TITLE.LIST']}" />
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.deleteAction}">
			<%@include file="_manager/_deleteManager.jsp"%>
		</t:div>
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		
		
		<t:div id="blockFormSearch" styleClass="blockForm">
			<h:form id="lookForIndForm" styleClass="opiR1_form">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.GEST']}"
						styleClass="section-smallTitle" />
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
							action="#{gestionnaireController.filterPaginator}" />
					</t:panelGroup>
				</e:panelGrid>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.NAME']}"
							for="nom" />
					</t:panelGroup>
					<e:inputText id="nom"
						value="#{gestionnaireController.paginator.gestRechPojo.nom}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.FIRST_NAME']}"
							for="prenom" />
					</t:panelGroup>
					<e:inputText id="prenom"
						value="#{gestionnaireController.paginator.gestRechPojo.prenom}" />
				</e:panelGrid>
			</h:form>
		</t:div>

		<t:htmlTag value="br" />
		
		
		
		
		<h:form id="seeManagersForm">
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="blockFormListDom" styleClass="blockForm">
				<t:div styleClass="blockButton">
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
							styleClass="form-button" immediate="true"
							rendered="#{managedAccess.addAuthorized}"
							action="#{gestionnaireController.goEnterManager}">
							<t:updateActionListener
								value="#{gestionnaireController.actionEnum.addAction}"
								property="#{gestionnaireController.actionEnum.whatAction}" />
						</e:commandButton>
						<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
							immediate="true"
							action="#{welcomeController.goWelcomeManager}">
							<t:updateActionListener
								value="#{gestionnaireController.actionEnum.emptyAction}"
								property="#{gestionnaireController.actionEnum.whatAction}" />
						</e:commandButton>
					</t:panelGroup>
				</t:div>
				<e:dataTable var="manager" id="managerData"
					value="#{gestionnaireController.paginator.visibleItems}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false">
					<f:facet name="header">
						<h:panelGroup >
							<e:paginator id="managerPaginator"
								paginator="#{gestionnaireController.paginator}"
								itemsName="#{msgs['GESTIONNAIRES']}"
								onchange="javascript:{simulateLinkClick('seeManagersForm:managerData:submitPageSize');}" />
							<e:commandButton id="submitPageSize"
								action="#{gestionnaireController.paginator.forceReload}" />
						</h:panelGroup>
					</f:facet>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['GESTIONNAIRE.LOGIN']}" />
						</f:facet>
						<e:text value="#{manager.login}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['PROFIL']}" />
						</f:facet>
						<e:text value="#{manager.profile.libelle}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.NAME']}" />
						</f:facet>
						<e:text value="#{manager.displayName}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.MAIL']}" />
						</f:facet>
						<e:text value="#{manager.adressMail}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
						</f:facet>
						<t:graphicImage url="/media/images/check2.gif"
							rendered="#{manager.temoinEnService}" />
						<t:graphicImage url="/media/images/check0.gif"
							rendered="#{!manager.temoinEnService}" />
					</t:column>
					<t:column>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							title="#{msgs['_.BUTTON.DISPLAY']}" 
							action="#{gestionnaireController.goSeeOneManager}">
							<t:updateActionListener
									value="#{manager}"
									property="#{gestionnaireController.manager}" />
						</e:commandButton>
					</t:column>
					<t:column>
						<e:commandButton image="/media/images/update.png"
							immediate="true" styleClass="form-button-image"
							title="#{msgs['BUTTON.UPDATE']}"
							rendered="#{managedAccess.updateAuthorized}"
							action="#{gestionnaireController.goEnterManager}">
							<t:updateActionListener
								value="#{gestionnaireController.actionEnum.updateAction}"
								property="#{gestionnaireController.actionEnum.whatAction}" />
							<t:updateActionListener
									value="#{manager}"
									property="#{gestionnaireController.manager}" />
						</e:commandButton>
					</t:column>
					<t:column>
						<e:commandButton image="/media/images/cancel.png"
							styleClass="form-button-image" immediate="true"
							rendered="#{managedAccess.deleteAuthorized}"
							title="#{msgs['_.BUTTON.DELETE']}">
								<t:updateActionListener
									value="#{gestionnaireController.actionEnum.deleteAction}"
									property="#{gestionnaireController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{manager}"
									property="#{gestionnaireController.manager}" />
						</e:commandButton>
					</t:column>
				</e:dataTable>
			</t:div>
		</h:form>
		
		<e:text value="#{msgs['GESTIONNAIRE.NOT.FOUND']}"
			rendered="#{empty gestionnaireController.paginator.visibleItems}" />
		
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
	hideElement('seeManagersForm:managerData:submitPageSize');
</script>
</e:page>
