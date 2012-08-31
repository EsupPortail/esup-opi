<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['FUNCTION.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteFunction.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeFonctionsForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.emptyAction}">
						<e:panelGrid columns="2" columnClasses="col2UnDemi,col2UnDemi"
							styleClass="twoGroupsButton">
							<e:commandButton value="#{msgs['DOMAIN.SEE.BUTTON']}"
								immediate="true"
								action="#{domainController.goSeeAllDomain}" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									rendered="#{managedAccess.addAuthorized}"
									immediate="true"
									action="#{fonctionController.goAddFonction}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:div styleClass="blockButton"
						rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.updateAction}">
						<t:div>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								action="#{fonctionController.update}" />
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								immediate="true"
								action="#{fonctionController.reset}"/>
						</t:div>
					</t:div>
					<e:dataTable var="fonction" value="#{fonctionController.fonctions}"
						styleClass="paginatorData" alternateColors="true">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{fonction.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['DOMAIN']}" />
							</f:facet>
							<e:text value="#{fonction.domain.libelle}" 
								rendered="#{fonctionController.actionEnum.whatAction != fonctionController.actionEnum.updateAction
									|| fonction != fonctionController.fonction}"/>
							<t:panelGroup rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.updateAction
									&& fonction == fonctionController.fonction}">
								<e:selectOneMenu converter="javax.faces.Integer"
									value="#{fonctionController.selectedDomId}">
									<t:selectItems var="domain"  
										value="#{domainController.domains}" 
										itemLabel="#{domain.shortLabel}" itemValue="#{domain.id}"/>
								</e:selectOneMenu>
								<e:commandButton image="/media/images/add.png"
									styleClass="form-button-image"
									rendered="#{managedAccess.addAuthorized}"
									title="#{_.BUTTON.ADD}">
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
								rendered="#{fonctionController.actionEnum.whatAction != fonctionController.actionEnum.updateAction
									|| fonction != fonctionController.fonction}"/>
							<e:inputText value="#{fonctionController.fonction.libelle}" size="31" maxlength="60"
								rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.updateAction
									&& fonction == fonctionController.fonction}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ACTION']}" />
							</f:facet>
							<e:text value="#{fonction.action}" 
								rendered="#{fonctionController.actionEnum.whatAction != fonctionController.actionEnum.updateAction
									|| fonction != fonctionController.fonction}"/>
							<e:inputText value="#{fonctionController.fonction.action}" size="51" maxlength="50"
								rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.updateAction
									&& fonction == fonctionController.fonction}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.RANG']}" />
							</f:facet>
							<e:text value="#{fonction.rang}" 
								rendered="#{fonctionController.actionEnum.whatAction != fonctionController.actionEnum.updateAction
									|| fonction != fonctionController.fonction}"/>
							<e:inputText value="#{fonctionController.fonction.rang}" size="3" maxlength="2" 
								rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.updateAction
									&& fonction == fonctionController.fonction}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true"
								action="#{fonctionController.goUpdateFonction}"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" 
								rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.emptyAction
										&& managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{fonctionController.actionEnum.updateAction}"
									property="#{fonctionController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{fonction}"
									property="#{fonctionController.fonction}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{fonctionController.actionEnum.whatAction == fonctionController.actionEnum.emptyAction
									&& managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{fonctionController.actionEnum.deleteAction}"
									property="#{fonctionController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{fonction}"
									property="#{fonctionController.fonction}" />
							</e:commandButton>
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
	highlightInputAndSelect('seeFonctionsForm');
</script>
</e:page>
