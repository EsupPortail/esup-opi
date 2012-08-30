<%@include file="../../../_include/_include.jsp"%>
<h:form id="formEnterArchiveTask" >
	<t:div id="blockFormEnterMotivation" styleClass="blockForm">
		<t:div style="width:100%;">
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<e:text value="#{msgs['ARCHIVAGE.ENTER.TITLE']}" styleClass="section-smallTitle" />
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.addAction}"
						action="#{archiveTaskController.add}" />
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{archiveTaskController.actionEnum.whatAction == archiveTaskController.actionEnum.updateAction}"
						action="#{archiveTaskController.update}" />
					<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
						action="#{archiveTaskController.reset}"
						onclick="javascript:{clickAnnuler = true;}"/>
				</t:panelGroup>
			</e:panelGrid>
			<e:panelGrid styleClass="tableWidthMax" columns="4"
				columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.CAMP_ARCH']}" for="code" />
				</t:panelGroup>
				<t:panelGroup>
					<e:text value="#{archiveTaskController.campToArch.libelle}"/>
					<e:commandButton id="refreshCampToAct"
						value="#{msgs[_.BUTTON.CHANGE]}"
						action="#{archiveTaskController.refreshCampToAct}" />
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.CAMP_ACT']}" for="code" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectOneMenu id="choiceCampActive"
						converter="#{campagneConverter}"
						value="#{archiveTaskController.campToActive}">
						<f:selectItems value="#{archiveTaskController.campagnesToActiveItems}" />
					</e:selectOneMenu>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.DATE_ARCH']}" for="dateArchive" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="dateArchive" size="10" maxlength="8"
						value="#{archiveTaskController.dateArch}">
						<f:convertDateTime pattern="ddMMyyyy"
							timeZone="#{sessionController.timezone}" />
						<f:validateLength minimum="8"/>
					</e:inputText>
				</t:panelGroup>
				<t:panelGroup style="text-align: right;">
					<e:selectBooleanCheckbox id="affArchivedCamps"
						onchange="javascript:{simulateLinkClick('formEnterArchiveTask:refreshCampToAct');}"
						value="#{archiveTaskController.affArchivedCamps}" />
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['ARCHIVAGE.ENTER.AFF_ARCH']}" />
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
		
	</t:div>
</h:form>
<script type="text/javascript">
	hideElement('formEnterArchiveTask:refreshCampToAct');
	hideElement('lookForIndForm:lookForIndividuTypeDec')
	hideElement('lookForIndForm:lookForIndividuEtp');
</script>

