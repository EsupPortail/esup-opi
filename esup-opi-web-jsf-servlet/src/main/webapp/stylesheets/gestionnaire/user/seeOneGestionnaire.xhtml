<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['GESTIONNAIRE.TITLE.SEE']}" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

		<t:div rendered="#{not empty managedAccess.traitementDisplay}">
			<e:form id="updateRightManagerForm" styleClass="opiR1_form">
				<opi:menuDynTag id="menuFonction" styleClass="displayTraitement">
					<opi:menuDynItems value="#{managedAccess.traitementDisplay}"
						styleClass="form-button-link" />
				</opi:menuDynTag>
			</e:form>
		</t:div>
		<t:htmlTag value="br" />
		<t:div id="div_subSection" styleClass="div_subSection">

			<t:div id="blockFormInfo" styleClass="blockForm">
				<t:div styleClass="blockButton">
					<t:panelGroup>
						<h:form id="seeOneManagerForm" styleClass="opiR1_form">
							<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
								styleClass="form-button" immediate="true"
								rendered="#{managedAccess.updateAuthorized}"
								action="#{gestionnaireController.goEnterManager}">
								<t:updateActionListener
									value="#{gestionnaireController.actionEnum.updateAction}"
									property="#{gestionnaireController.actionEnum.whatAction}" />
							</e:commandButton>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true"
								action="#{gestionnaireController.goSeeAllManagers}">
								<t:updateActionListener
									value="#{gestionnaireController.actionEnum.emptyAction}"
									property="#{gestionnaireController.actionEnum.whatAction}" />
							</e:commandButton>
						</h:form>
					</t:panelGroup>
				</t:div>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.NAME']}" for="nom"
							styleClass="form-field-label-validator" />
						<t:outputText value="*" styleClass="etoileForChpObli" />
					</t:panelGroup>
					<e:text id="nom" value="#{gestionnaireController.manager.nomUsuel}" />
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
					<e:text id="cge" value="#{gestionnaireController.manager.codeCge}" />

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
			<t:div id="divSelectCmi"
				rendered="#{not empty commissionController.selectedCommissions}">
				<t:div style="width:100%;">
					<e:text value="#{msgs['COMMISSIONS']}"
						styleClass="section-smallTitle" />
				</t:div>
				<e:dataTable var="commission"
					value="#{commissionController.selectedCommissions}"
					renderedIfEmpty="false" styleClass="displayInfo"
					alternateColors="false">
					<t:column width="30%">
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
						</f:facet>
						<e:text value="#{commission.code}" />
					</t:column>
					<t:column width="70%">
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
						</f:facet>
						<e:text value="#{commission.libelle}" />
					</t:column>
				</e:dataTable>
			</t:div>




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
</script>
</e:page>
