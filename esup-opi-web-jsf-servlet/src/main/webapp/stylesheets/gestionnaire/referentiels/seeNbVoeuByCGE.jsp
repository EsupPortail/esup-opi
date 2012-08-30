<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}" 
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['VOEU.MANAGED']}" />
	
	<e:messages styleClass="messageForUser"/>
	
	<t:panelGroup styleClass="maxDivTextRight" id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.deleteAction}">
		<%@include file="_nbVoeuByCGE/_deleteNbVoeuByCGE.jsp"%>
	</t:panelGroup>
	
	<h:form id="seeNbVoeuForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{nbVoeuByCGEController.goAddNbVoeu}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}"/>
		</t:div>
		<t:div styleClass="twoGroupsButton"
			rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.updateAction}">
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				action="#{nbVoeuByCGEController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
				action="#{nbVoeuByCGEController.reset}"/>
		</t:div>
		<e:dataTable var="nbVoeuSelect"
			value="#{nbVoeuByCGEController.allNbVoeuByCge}"
			styleClass="paginatorData" alternateColors="true">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.CODE_CGE']}"/>
				</f:facet>
				<e:text value="#{nbVoeuSelect.codeCge}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.NB_VOEU']}"/>
				</f:facet>
				<t:panelGroup 
						rendered="#{nbVoeuByCGEController.actionEnum.whatAction != nbVoeuByCGEController.actionEnum.updateAction
						|| nbVoeuSelect.codeCge != nbVoeuByCGEController.nbVoeuCge.codeCge}">
					<e:text value="#{nbVoeuSelect.nbVoeu}"/>
				</t:panelGroup>
				<t:panelGroup
						rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.updateAction
						&& nbVoeuSelect.codeCge == nbVoeuByCGEController.nbVoeuCge.codeCge}">
					<e:inputText id="nbVoeu" size="11" maxlength="10"
						value="#{nbVoeuByCGEController.nbVoeuCge.nbVoeu}"/>
				</t:panelGroup>
			</t:column>
			<t:column styleClass="buttonTD"
				rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{nbVoeuByCGEController.actionEnum.updateAction}"
						property="#{nbVoeuByCGEController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{nbVoeuSelect}"
						property="#{nbVoeuByCGEController.nbVoeuCge}"/>
				</e:commandButton>
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{nbVoeuByCGEController.actionEnum.deleteAction}"
						property="#{nbVoeuByCGEController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{nbVoeuSelect}"
						property="#{nbVoeuByCGEController.nbVoeuCge}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		</h:form>
		<t:panelGroup id="addNbVoeu"
			rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.addAction}">
			<%@include file="_nbVoeuByCGE/_enterNbVoeuByCGE.jsp"%>
		</t:panelGroup>
	
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
	</script>
</e:page>