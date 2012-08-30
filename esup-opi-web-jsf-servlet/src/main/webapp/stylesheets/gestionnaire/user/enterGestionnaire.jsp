<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['GESTIONNAIRE.TITLE.ADD']}"
			rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.addAction}" />
		<e:section value="#{msgs['GESTIONNAIRE.TITLE.UPDATE']}"
			rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.updateAction}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div styleClass="maxDivTextRight">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CHAMP']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
		</t:div>
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="enterManagerForm">
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<e:panelGrid columns="2" columnClasses="col2UnDemi,col2UnDemi"
							styleClass="twoGroupsButton">
							<e:commandButton value="#{msgs['GESTIONNAIRE.SEARCH_LDAP']}"
								immediate="true" onclick="javascript:{clickAnnuler = true;}"
								action="#{gestionnaireController.goSearchManager}"
								rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.addAction}">
								<t:updateActionListener
									value="#{ldapSearchController.ldapEnum.managerValue}"
									property="#{ldapSearchController.ldapEnum.whereAreYouFrom}" />
							</e:commandButton>
							<t:panelGroup
								rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.updateAction}" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{gestionnaireController.add}"
									rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.addAction
											&& managedAccess.addAuthorized}" />
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{gestionnaireController.update}"
									rendered="#{gestionnaireController.actionEnum.whatAction == gestionnaireController.actionEnum.updateAction
											&& managedAccess.updateAuthorized}" />
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									onclick="javascript:{clickAnnuler = true;}" immediate="true"
									action="#{gestionnaireController.goSeeAllManagers}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.NAME']}" for="nom"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="nom" size="31" maxlength="30"
							value="#{gestionnaireController.manager.nomUsuel}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="prenom" size="22" maxlength="20"
							value="#{gestionnaireController.manager.prenom}" />

						<t:panelGroup>
							<e:outputLabel value="#{msgs['GESTIONNAIRE.LOGIN']}" for="login"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="login" size="35" maxlength="50"
							value="#{gestionnaireController.manager.login}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}"
								styleClass="form-field-label-validator" for="adressMail" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="adressMail" size="43" maxlength="50"
							value="#{gestionnaireController.manager.adressMail}" />

						<t:panelGroup>
							<e:outputLabel value="#{msgs['PROFIL']}" for="profil"
								styleClass="form-field-label-validator" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:selectOneMenu id="profil" converter="javax.faces.Integer"
							value="#{gestionnaireController.idProfilSelected}">
							<f:selectItem itemLabel=""
									itemValue="" />
							<t:selectItems var="beanProfile"
								value="#{profilController.beanProfileInUse}"
								itemLabel="#{beanProfile.profile.libelle}"
								itemValue="#{beanProfile.profile.id}" />
						</e:selectOneMenu>
						<e:outputLabel value="#{msgs['FIELD_LABEL.CGE']}" for="cge" />
						<t:panelGroup>
							<e:selectOneMenu id="cge"
								valueChangeListener="#{gestionnaireController.selectCge}"
								onclick="javascript:{clickAnnuler = true;}"
								onchange="javascript:{simulateLinkClick('enterManagerForm:submitCodCge');}"
								value="#{gestionnaireController.manager.codeCge}">
								<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}"
									itemValue="" />
								<t:selectItems var="centreGestion"
									value="#{gestionnaireController.centreGestion}"
									itemLabel="#{centreGestion.libCge}"
									itemValue="#{centreGestion.codCge}" />
							</e:selectOneMenu>
							<e:commandButton id="submitCodCge"
								onclick="javascript:{clickAnnuler = true;}" />
						</t:panelGroup>

						<t:panelGroup>
							<e:outputLabel value="#{msgs['GESTIONNAIRE.DBT_VALID']}"
								styleClass="form-field-label-validator" for="datdbtvalid" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="datdbtvalid" size="10" maxlength="8"
							value="#{gestionnaireController.manager.dateDbtValidite}">
							<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}" />
							<f:validateLength minimum="8"/>
						</e:inputText>
						<e:outputLabel value="#{msgs['GESTIONNAIRE.END_VALID']}"
							for="datfinvalid" />
						<e:inputText id="datfinvalid" size="10" maxlength="8"
							value="#{gestionnaireController.manager.dateFinValidite}">
							<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}" />
							<f:validateLength minimum="8"/>
						</e:inputText>
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
	highlightInputAndSelect('enterManagerForm');
	hideElement('enterManagerForm:submitCodCge');
	hideElement('submitCmi');
</script>
</e:page>
