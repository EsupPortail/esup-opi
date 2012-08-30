<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:htmlTag value="br" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:htmlTag value="br" />
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="affectRightManagerForm" styleClass="opiR1_form">
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{gestionnaireController.update}"
									rendered="#{managedAccess.updateAuthorized}" />
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true"
								action="#{gestionnaireController.goSeeOneManager}">
								<t:updateActionListener
									value="#{gestionnaireController.actionEnum.emptyAction}"
									property="#{gestionnaireController.actionEnum.whatAction}" />
							</e:commandButton>

						</t:panelGroup>
					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.NAME']}" for="nom"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="nom"
							value="#{gestionnaireController.manager.nomUsuel}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="prenom"
							value="#{gestionnaireController.manager.prenom}" />

						<t:panelGroup>
							<e:outputLabel value="#{msgs['GESTIONNAIRE.LOGIN']}" for="login"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="login" value="#{gestionnaireController.manager.login}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}"
								styleClass="form-field-label-validator" for="adressMail" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="adressMail"
							value="#{gestionnaireController.manager.adressMail}" />

						<t:panelGroup>
							<e:outputLabel value="#{msgs['PROFIL']}" for="profil"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="profil"
							value="#{gestionnaireController.manager.profile.libelle}" />
						<e:outputLabel value="#{msgs['FIELD_LABEL.CGE']}" for="cge" />
						<t:panelGroup>
							<e:selectOneMenu id="cge"
								valueChangeListener="#{gestionnaireController.selectCge}"
								onchange="javascript:{simulateLinkClick('affectRightManagerForm:submitCodCge');}"
								value="#{gestionnaireController.manager.codeCge}">
								<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}"
									itemValue="" />
								<t:selectItems var="centreGestion"
									value="#{gestionnaireController.centreGestion}"
									itemLabel="#{centreGestion.libCge}"
									itemValue="#{centreGestion.codCge}" />
							</e:selectOneMenu>
							<e:commandButton id="submitCodCge" />
						</t:panelGroup>

						<t:panelGroup>
							<e:outputLabel value="#{msgs['GESTIONNAIRE.DBT_VALID']}"
								styleClass="form-field-label-validator" for="datdbtvalid" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:text id="datdbtvalid"
							value="#{gestionnaireController.manager.dateDbtValidite}">
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
						<e:outputLabel value="#{msgs['GESTIONNAIRE.END_VALID']}"
							for="datfinvalid" />
						<e:text id="datfinvalid"
							value="#{gestionnaireController.manager.dateFinValidite}">
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
					</e:panelGrid>
				</t:div>

				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="divSelectCmi"
					rendered="#{gestionnaireController.manager.codeCge == ''
						|| gestionnaireController.manager.codeCge == null}">
					<%@include
						file="../referentiels/_commission/_selectCmi.jsp"%>
				</t:div>


			</h:form>

		</t:div>

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
	highlightInputAndSelect('affectRightManagerForm');
	hideElement('affectRightManagerForm:submitCodCge');
	hideElement('submitCmi');
</script>
</e:page>
