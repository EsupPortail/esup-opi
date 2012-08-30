<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['FUNCTION.TITLE.ADD']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="addFonctionForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								rendered="#{managedAccess.addAuthorized}"
								action="#{fonctionController.add}" />
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								immediate="true" action="#{fonctionController.goSeeAllFonction}" />
						</t:panelGroup>
					</t:div>
					<e:dataTable var="fonction"
						value="#{fonctionController.addFonctions}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{fonction.code}" rendered="#{fonction.id != 0}" />
							<e:inputText id="codeFct" value="#{fonction.code}" size="12" maxlength="11"
								rendered="#{fonction.id == 0}" />
						</t:column>
					
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['DOMAIN']}" />
							</f:facet>
							<e:text value="#{fonction.domain.libelle}"
								rendered="#{fonction.id != 0}" />
							<t:panelGroup rendered="#{fonction.id == 0}">
								<e:selectOneMenu id="domain" converter="javax.faces.Integer"
									value="#{fonctionController.selectedDomId}">
									<t:selectItems var="domain" value="#{domainController.domains}"
										itemLabel="#{domain.shortLabel}" itemValue="#{domain.id}" />
								</e:selectOneMenu>
								<e:commandButton image="/media/images/add.png"
									styleClass="form-button-image" title="#{_.BUTTON.ADD}">
									<t:updateActionListener
										value="#{domainController.actionEnum.addAction}"
										property="#{domainController.actionEnum.whatAction}" />
								</e:commandButton>
							</t:panelGroup>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{fonction.libelle}"
								rendered="#{fonction.id != 0}" />
							<e:inputText id="libFct" value="#{fonction.libelle}" size="31" maxlength="60"
								rendered="#{fonction.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ACTION']}" />
							</f:facet>
							<e:text value="#{fonction.action}" rendered="#{fonction.id != 0}" />
							<e:inputText id="actionFct" value="#{fonction.action}" size="51" maxlength="50"
								rendered="#{fonction.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.RANG']}" />
							</f:facet>
							<e:text value="#{fonction.rang}" rendered="#{fonction.id != 0}" />
							<e:inputText id="rangFct" value="#{fonction.rang}" size="3" maxlength="2"
								rendered="#{fonction.id == 0}" />
						</t:column>
					</e:dataTable>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="addDomain"
					rendered="#{domainController.actionEnum.whatAction == domainController.actionEnum.addAction}">
					<%@include file="_domain/_enterDomain.jsp"%>
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
	highlightInputAndSelect('addFonctionForm');
</script>
</e:page>
