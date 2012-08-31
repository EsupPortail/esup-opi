<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['ARCHIVAGE.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionEnter" styleClass="div_subSection"
			rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.addAction
						|| archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.updateAction}">
			<%@include file="_block/_enterArchiveTask.jsp"%>
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.deleteAction}">
			<%@include file="_block/_deleteArchiveTask.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeArchiveTasksForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormArchiveTasks" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.emptyAction}">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
							 immediate="true"
							 action="#{archiveTaskController.goAddArchiveTask}"/>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					<e:dataTable var="archiveTask" value="#{archiveTaskController.archiveTasks}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ARCHIVE.CAMP_ARCH']}" />
							</f:facet>
							<e:text value="#{archiveTask.campagneToArchive.libelle}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ARCHIVE.CAMP_ACTIVE']}" />
							</f:facet>
							<e:text value="#{archiveTask.campagneToActive.libelle}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ARCHIVE.DATE_ARCH']}" />
							</f:facet>
							<e:text value="#{archiveTask.dateArchive}">
								<f:convertDateTime pattern="dd/MM/yyyy"
									timeZone="#{sessionController.timezone}" />
							</e:text>
							
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ARCHIVE.EXECUTED']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{archiveTask.isExecuted}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!archiveTask.isExecuted}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" 
								rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.emptyAction
										&& !archiveTask.isExecuted && managedAccess.updateAuthorized}"
								action="#{archiveTaskController.goUpdateArchiveTask}">
								<t:updateActionListener
									value="#{archiveTask}"
									property="#{archiveTaskController.archiveTask}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.emptyAction
										&& !archiveTask.isExecuted && managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{archiveTaskController.actionEnum.deleteAction}"
									property="#{archiveTaskController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{archiveTask}"
									property="#{archiveTaskController.archiveTask}" />
							</e:commandButton>
						</t:column>
					</e:dataTable>
					
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
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
</script>
</e:page>
