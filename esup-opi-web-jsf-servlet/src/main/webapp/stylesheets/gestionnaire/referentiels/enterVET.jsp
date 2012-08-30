<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PJ.IS_FOR_ONE_VET']}#{nomenclatureController.vetDTO.libWebVet}" />
		
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		
		<t:div id="info_valider" styleClass="messageForUser">
			<e:text styleClass="msg-info" value="#{msgs['INFO.VALIDATE']}" />
		</t:div>
		
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="addPJForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FIELD_LABEL.VRS_VET']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								immediate="true" action="#{nomenclatureController.updatePJs}"
								/>		
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true" action="#{nomenclatureController.goBackFromSearchPJ}"
								/>					
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<%@include file="_blockVET/_blockDescVET.jsp"%>
					
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				
				<t:div id="blockFormSeePJ" styleClass="blockForm">
					<%@include file="_blockPJ/_seePJ.jsp"%>
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
	highlightInputAndSelect('enterCommissionForm');
</script>
</e:page>
