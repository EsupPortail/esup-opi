<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['INDIVIDU.TITLE.AJOUT']}" />

	
		<t:div id="div_subSectionConfirmAdd" styleClass="div_subSection"
			rendered="#{individuController.actionEnum.whatAction == individuController.actionEnum.addAction}">
			<%@include file="_student/_confirmAddStudent.jsp"%>
		</t:div>

		<t:div id="div_subSectionSendMail" styleClass="div_subSection"
			rendered="#{individuController.actionEnum.whatAction == individuController.actionEnum.sendMail}">
			<%@include file="_student/_sendMailStudent.jsp"%>
		</t:div>
		
		<t:div id="div_subSectionGoRecap" styleClass="div_subSection"
			rendered="#{individuController.actionEnum.whatAction == individuController.actionEnum.readAction}">
			<%@include file="_student/_gotoRecapIndividu.jsp"%>
		</t:div>
		

			<h:form id="seeManagersForm">
			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="blockFormButton" styleClass="blockForm">
			<t:div styleClass="blockButton">
				<t:panelGroup>
					<e:commandButton value="#{msgs['GESTIONNAIRE.GENERE.DOSSIER']}"
						action="#{individuController.addIndGestionnaire}" 
						rendered="#{individuController.actionEnum.whatAction == individuController.actionEnum.emptyAction}">
					</e:commandButton>
						
				</t:panelGroup>
			</t:div>
	
			<%@include file="_student/_blockAddStudent.jsp"%>

			
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
	hideElement('seeManagersForm:managerData:submitPageSize');
</script>
</e:page>
