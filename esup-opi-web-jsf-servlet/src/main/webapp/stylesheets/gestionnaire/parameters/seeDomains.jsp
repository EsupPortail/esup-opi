<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['DOMAIN.TITLE.LIST']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{domainController.actionEnum.whatAction == domainController.actionEnum.deleteAction}">
			<%@include file="_domain/_deleteDomain.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeDomainsForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormListDom" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								rendered="#{managedAccess.addAuthorized}"
								action="#{domainController.goAddDomain}"
								immediate="true"
								styleClass="form-button"/>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								action="go_managed_fct">
								<t:updateActionListener
									value="#{domainController.actionEnum.emptyAction}"
									property="#{domainController.actionEnum.whatAction}" />
							</e:commandButton>
						</t:panelGroup>
					</t:div>
					<e:dataTable var="domain" value="#{domainController.domains}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{domain.code}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{domain.libelle}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.SHORT_LIB']}" />
							</f:facet>
							<e:text value="#{domain.shortLabel}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ACTION']}" />
							</f:facet>
							<e:text value="#{domain.action}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.RANG']}" />
							</f:facet>
							<e:text value="#{domain.rang}" />
						</t:column>
						<t:column>
							<e:commandButton image="/media/images/update.png"
								rendered="#{managedAccess.updateAuthorized}"
								immediate="true"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" >
								<t:updateActionListener
									value="#{domainController.actionEnum.updateAction}"
									property="#{domainController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{domain}"
									property="#{domainController.domain}" />
							</e:commandButton>
						</t:column>
						<t:column>
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								rendered="#{managedAccess.deleteAuthorized}"
								immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}" >
								<t:updateActionListener
									value="#{domainController.actionEnum.deleteAction}"
									property="#{domainController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{domain}"
									property="#{domainController.domain}" />
							</e:commandButton>
						</t:column>
					</e:dataTable>
				</t:div>
			</h:form>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="addDomain"
				rendered="#{domainController.actionEnum.whatAction == domainController.actionEnum.addAction
					|| domainController.actionEnum.whatAction == domainController.actionEnum.updateAction}">
				<%@include file="_domain/_enterDomain.jsp"%>
			</t:div>
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
	highlightInputAndSelect('formAddDomain');
</script>
</e:page>
