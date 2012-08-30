<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		
		<t:div id="div_sectionConfirmPdf" styleClass="div_subSection"
			rendered="#{printOpinionController.actionEnum.whatAction 
				== printOpinionController.actionEnum.confirmAction}">
				<%@include file="_block/_confirmPdfNotification.jsp"%>
		</t:div>
		
		<t:div id="div_sectionMail" styleClass="div_subSection"
			rendered="#{printOpinionController.actionEnum.whatAction 
				== printOpinionController.actionEnum.sendMail}">
			<h:form id="formSendMail">
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['OPINION.CONFIRM.SEND_MAIL']}">
						</e:text>
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.YES']}"
								action="#{validOpinionController.validateStudentsForCommissions}">
								<t:updateActionListener value="#{true}"
									property="#{validOpinionController.valideFC}" />
								<t:updateActionListener value="#{true}"
									property="#{validOpinionController.sendMail}" />
							</e:commandButton>
							
							<e:commandButton value="#{msgs['_.BUTTON.NO']}"
								action="#{validOpinionController.validateStudentsForCommissions}">
								<t:updateActionListener value="#{true}"
									property="#{validOpinionController.valideFC}" />
								<t:updateActionListener value="#{false}"
									property="#{validOpinionController.sendMail}" />
							</e:commandButton>
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
			</h:form>
		</t:div>
		
		
		<h:form id="lookForOpinionForm" styleClass="opiR1_form">		
			<t:div id="blockFormSearch" styleClass="blockForm"
				rendered="#{not empty managedAccess.domainAccueilGest}">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['FIELD_LABEL.VALIDATE_OPINIO']}"
						styleClass="section-smallTitle" />
				</e:panelGrid>

				<e:panelGrid columns="1" align="center">
					<t:panelGroup>
						<e:selectOneRadio id="choixTypeOpinion"
						value="#{printOpinionController.isFinal}"
						onclick="javascript:{simulateLinkClick('lookForOpinionForm:submitSelectTypeOpinion');}">
  							<f:selectItem itemLabel="#{msgs['AVIS.NON_DEFINITIFS']}" itemValue="#{false}" />
  							<f:selectItem itemLabel="#{msgs['AVIS.DEFINITIFS']}" itemValue="#{true}" />
						</e:selectOneRadio>
						<e:commandButton id="submitSelectTypeOpinion"
							value="#{msgs['_.BUTTON.CHANGE']}"
							action="#{printOpinionController.setListTypeOpinions}" />
						
					</t:panelGroup>
					
					<t:dataList var="typeDec" id="typeOpinionData" 
						value="#{nomenclatureController.typeDecisionsSorted}"
						>
						<t:div 
							rendered="#{typeDec.isFinal == printOpinionController.isFinal}" 
							style="padding:0px;margin:0px;margin-left:45%;">
								<jdt:multipleRowsSelector 
									selectionList="#{printOpinionController.resultSelected}" />
								<e:text value="#{typeDec.libelle }" />
							</t:div>
					</t:dataList>
					<t:panelGroup>
						<t:htmlTag value="br" />
						<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="regime" />
						<e:selectManyCheckbox id="regime" value="#{commissionController.listeRI}" converter="#{regimeInscriptionConverter}"
							disabled="#{!commissionController.canModifyRISearch}"
							layout="lineDirection">
							<t:selectItems var="ri" 
									itemLabel="#{ri.shortLabel}" itemValue="#{ri}"
									value="#{nomenclatureController.allRegimeInscription}"/>
						</e:selectManyCheckbox>
					</t:panelGroup>
				</e:panelGrid>
			</t:div>
		
			<t:div id="blockCommission" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSIONS']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{validOpinionController.validateStudentsForCommissions}" 
									rendered="#{printOpinionController.actionEnum.whatAction 
										!= printOpinionController.actionEnum.confirmAction}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{managedAccess.goDisplayFunction}" 
									rendered="#{printOpinionController.actionEnum.whatAction 
										!= printOpinionController.actionEnum.confirmAction}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="commission" id="lesCommissions"
						value="#{commissionController.commissionsItemsByRight}"
						styleClass="paginatorData" alternateColors="true">
						<t:column width="20%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{commission.code}" />
						</t:column>
						<t:column width="64%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{commission.libelle}" />
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{validOpinionController.goSeeStudientForCommission}"
								title="#{msgs['INFO.CANDIDAT.DISPLAY']}"
								rendered="#{printOpinionController.actionEnum.whatAction 
									!= printOpinionController.actionEnum.confirmAction}" >
								<t:updateActionListener value="#{commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<f:facet name="header">
								<t:selectBooleanCheckbox
									value="#{printOpinionController.allChecked}"
									onclick="checkAllInElement('lookForOpinionForm:lesCommissions' ,this.checked)" />
							</f:facet>
							<jdt:multipleRowsSelector
								selectionList="#{printOpinionController.commissionsSelected}" />
						
						</t:column>
					</e:dataTable>
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
		hideElement('lookForOpinionForm:submitSelectTypeOpinion');
	</script>
</e:page>
