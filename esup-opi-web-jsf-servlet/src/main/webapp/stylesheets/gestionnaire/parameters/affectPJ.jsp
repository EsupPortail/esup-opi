<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_PJ.TITLE.ADD']}" 
			rendered="#{nomenclatureController.actionEnum.whatAction 
						== nomenclatureController.actionEnum.addAction}"/>
		<e:section value="#{msgs['TYP_PJ.TITLE.UPDATE']}" 
			rendered="#{nomenclatureController.actionEnum.whatAction 
						== nomenclatureController.actionEnum.updateAction}"/>
		<e:section value="#{msgs['TYP_PJ.TITLE.SEE']}" 
			rendered="#{nomenclatureController.actionEnum.whatAction 
						== nomenclatureController.actionEnum.readAction}"/>
		
		<t:div styleClass="messageForUser">
			<e:messages />
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
							<e:text value="#{msgs['PJ']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{nomenclatureController.update}" 
									rendered="#{managedAccess.updateAuthorized}"/>
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									immediate="true" action="#{nomenclatureController.goSeeAllAffectPJ}"	/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<%@include file="_blockPJ/_blockDescPJ.jsp"%>
					
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				
				<t:div id="blockFormSeeEtape" styleClass="blockForm">
					<%@include file="_blockPJ/_seeEtape.jsp"%>
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
