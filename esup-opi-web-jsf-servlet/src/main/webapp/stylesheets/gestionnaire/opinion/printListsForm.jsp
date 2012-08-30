<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		
		<h:form id="choiceColFormComp" styleClass="opiR1_form"
			rendered="#{exportFormOrbeonController.actionEnum.whatAction 
					== exportFormOrbeonController.actionEnum.choiceColExport}">		
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}">
				<t:updateActionListener value="#{exportFormOrbeonController.actionEnum.emptyAction}"
					property="#{exportFormOrbeonController.actionEnum.whatAction}" />
			</e:commandButton>
			
			<e:text value="#{msgs['COMMISSION.EXPORT_LISTE_PREPA_CHAMPS']}" />
			
			<t:div id="listcolumn">
				<t:dataList var="key" id="titrecom"
					value="#{exportFormOrbeonController.champsDispos}">
					<e:li >
						<jdt:multipleRowsSelector
							selectionList="#{exportFormOrbeonController.champsChoisis}" />
						<e:text value="#{exportFormOrbeonController.libBase[key]}" style="padding-right:10px;" />
					</e:li>
				</t:dataList>
			</t:div>
		</h:form>
		
		
		<h:form id="printListsFormComp" styleClass="opiR1_form"
			rendered="#{exportFormOrbeonController.actionEnum.whatAction 
					!= exportFormOrbeonController.actionEnum.choiceColExport}">		
			<t:div id="blockFormSearch" styleClass="blockForm">
				<t:div style="text-align:right;">
					<e:commandButton value="#{msgs['_.BUTTON.CHOICE_COL']}" immediate="true">
						<t:updateActionListener value="#{exportFormOrbeonController.actionEnum.choiceColExport}"
							property="#{exportFormOrbeonController.actionEnum.whatAction}" />
					</e:commandButton>
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}"/>
				</t:div>
				
				<e:panelGrid styleClass="tableWidthMax" columns="3"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<e:outputLabel value="#{msgs['COMMISSIONS']}" for="lacommision" />
					
					<t:panelGroup>
						<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
							value="#{printOpinionController.idCommissionSelected}"
							onchange="javascript:{simulateLinkClick('printListsFormComp:selectCommission');}">
							<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.commissionsItemsByRightAndIsFormComp}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
						</e:selectOneMenu>
						<e:commandButton id="selectCommission"/>
					</t:panelGroup>
					
					<t:htmlTag value="br" />
					
					<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
					
					<e:selectManyCheckbox value="#{commissionController.listeRI}" converter="#{regimeInscriptionConverter}"
						disabled="#{!paginatorIndividu.indRechPojo.canModifyRISearch}"
						layout="lineDirection">
						<t:selectItems var="ri" 
								itemLabel="#{ri.shortLabel}" itemValue="#{ri}"
								value="#{nomenclatureController.allRegimeInscription}"/>
					</e:selectManyCheckbox>
					
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.EXPORT_FORM']}"
							rendered="#{printOpinionController.idCommissionSelected != null}"
							action="#{printOpinionController.makeCsvFormulaire}"/>
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
		hideElement('printListsFormComp:selectCommission');
	</script>
</e:page>
