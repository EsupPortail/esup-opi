<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		
		<t:div id="div_subSectionPdf" styleClass="div_subSection"
			rendered="#{printOpinionController.actionEnum.whatAction 
				== printOpinionController.actionEnum.confirmAction}">
				<%@include file="_block/_confirmPdfNotification.jsp"%>
		</t:div>
		<t:div id="choiceColumnExport" 
			rendered="#{printOpinionController.actionEnum.whatAction == printOpinionController.actionEnum.choiceColExport}">
				<%@include file="_block/_choiceColumnExport.jsp"%>
		</t:div>
		<h:form id="printListsForm" styleClass="opiR1_form">		
			<t:div id="blockFormSearch" styleClass="blockForm">
				<t:div style="text-align:right;">
					<e:commandButton value="#{msgs['_.BUTTON.EXPORT']}"
							rendered="#{commissionController.commission.code != null}"
							action="#{printOpinionController.generateCSVListesTransfert}"/>
					<e:commandButton value="#{msgs['_.BUTTON.CHOICE_COL']}" immediate="true">
						<t:updateActionListener 
							value="#{printOpinionController.actionEnum.choiceColExport}"
							 property="#{printOpinionController.actionEnum.whatAction}"/>
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}" 
						rendered="#{printOpinionController.actionEnum.whatAction 
						!= printOpinionController.actionEnum.confirmAction}" />
				</t:div>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['COMMISSIONS']}" for="lacommision" />
					</t:panelGroup>
					<t:panelGroup>
						<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
							value="#{commissionController.commission.id}"
							onchange="javascript:{simulateLinkClick('printListsForm:selectCommission');}" >
							<f:selectItem itemLabel="" itemValue="" />
							<t:selectItems var="commission"
								value="#{commissionController.commissionsItemsByRight}"
								itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
						</e:selectOneMenu>
						<e:commandButton id="selectCommission" 
							action="#{commissionController.selectCommissionForLists}"/>
					</t:panelGroup>
				</e:panelGrid>
		
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
		hideElement('printListsForm:submitSelectTypeOpinion');
		hideElement('printListsForm:selectCommission');
	</script>
</e:page>
