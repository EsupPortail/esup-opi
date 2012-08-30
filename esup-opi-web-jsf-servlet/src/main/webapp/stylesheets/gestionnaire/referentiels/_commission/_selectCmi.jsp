<%@include file="/stylesheets/_include/_include.jsp"%>
<t:div id="blockSelectcmi" styleClass="blockForm"
	rendered="#{commissionController.actionEnum.whatAction == commissionController.actionEnum.seeSelectCmi}">
	<e:panelGrid styleClass="tableWidthMax" columns="2"
		columnClasses="col1UnDemi,col2UnDemi">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['COMMISSION.CHOICE']}" for="selectCmi" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu converter="javax.faces.Integer"
				valueChangeListener="#{commissionController.selectCommission}"
				value="#{commissionController.commission.id}"
				onclick="javascript:{clickAnnuler = true;}"
				onchange="javascript:{simulateLinkClick('submitCmi');}">
				<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}" itemValue="" />
				<t:selectItems var="cmi"
					value="#{commissionController.commissionsItemsByRight}"
					itemLabel="#{cmi.libelle}" itemValue="#{cmi.id}" />
			</e:selectOneMenu>
			<t:commandButton id="submitCmi" forceId="true" styleClass="form-button"
					action="#{commissionController.selectCommission}"
					onclick="javascript:{clickAnnuler = true;}">
				<t:updateActionListener
					value="#{commissionController.actionEnum.emptyAction}"
					property="#{commissionController.actionEnum.whatAction}" />
			</t:commandButton>
		</t:panelGroup>
	</e:panelGrid>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />

<t:div id="blockCmiSelected" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['COMMISSIONS']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
					onclick="javascript:{clickAnnuler = true;}">
					<t:updateActionListener
						value="#{commissionController.actionEnum.seeSelectCmi}"
						property="#{commissionController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:dataTable var="commission"
		value="#{commissionController.selectedCommissions}"
		styleClass="displayInfo" alternateColors="false">
		<t:column width="30%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
			</f:facet>
			<e:text value="#{commission.code}" />
		</t:column>
		<t:column width="65%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
			</f:facet>
			<e:text value="#{commission.libelle}" />
		</t:column>
		<t:column width="5%">
			<e:commandButton image="/media/images/cancel.png"
				styleClass="form-button-image"
				action="#{commissionController.removeCmi}"
				title="#{msgs['_.BUTTON.DELETE']}">
				<t:updateActionListener value="#{commission}"
					property="#{commissionController.commission}" />
			</e:commandButton>
		</t:column>
	</e:dataTable>
</t:div>
