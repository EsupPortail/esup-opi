<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['GESTIONNAIRE.SEARCH_LDAP']}" />
		<h:form id="searchManagerForm">
			<%@include file="_manager/_searchLdap.jsp"%>

			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="div_subSectionList" styleClass="div_subSection">
				<e:dataTable var="manager" id="managerData"
					value="#{ldapSearchController.managers}" styleClass="paginatorData"
					alternateColors="true" renderedIfEmpty="false">
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['GESTIONNAIRE.LOGIN']}" />
						</f:facet>
						<e:text value="#{manager.login}" />
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
						<e:commandButton image="/media/images/add.png"
							styleClass="form-button-image" immediate="true"
							action="#{ldapSearchController.goBack}"
							title="#{_.BUTTON.ADD}">
							<t:updateActionListener value="#{manager}"
								property="#{gestionnaireController.manager}" />
						</e:commandButton>
					</t:column>
				</e:dataTable>
			</t:div>

		</h:form>
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
	highlightInputAndSelect('searchManagerForm');
</script>
</e:page>
