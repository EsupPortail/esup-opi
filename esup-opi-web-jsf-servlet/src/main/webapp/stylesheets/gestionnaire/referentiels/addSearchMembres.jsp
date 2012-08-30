<%@include file="/stylesheets/_include/_include.jsp"%>
<e:page stringsVar="msgs" menuItem="accueil"
	locale="#{sessionController.locale}">
	<%@include file="/stylesheets/_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['COMMISSION.SEARCH_MBR.TITLE']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="searchMembersForm">
				<%@include file="../user/_manager/_searchLdap.jsp"%>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />


				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="div_subSectionList" styleClass="div_subSection"
					rendered="#{not empty ldapSearchController.managers}">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSION.ADD_MBR.TITLE']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}" action="#{commissionController.addMembers}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="manager" id="managerData"
						value="#{ldapSearchController.managers}"
						styleClass="paginatorData" alternateColors="true">
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
							<jdt:multipleRowsSelector
										selectionList="#{commissionController.objectToAdd}" />
						</t:column>
					</e:dataTable>
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
	highlightInputAndSelect('searchMembersForm');
</script>
</e:page>
