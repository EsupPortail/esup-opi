<%@include file="../../../_include/_include.jsp"%>

	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi" styleClass="tableJustWidthMax">
		<e:text value="#{msgs['VOEU.TITLE.ENTER']}" styleClass="section-smallTitle"/>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.addAction}"
				action="#{nbVoeuByCGEController.add}"/>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{nbVoeuByCGEController.actionEnum.whatAction == nbVoeuByCGEController.actionEnum.updateAction}"
				action="#{nbVoeuByCGEController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{nbVoeuByCGEController.reset}"/>
		</t:panelGroup>
	</e:panelGrid>
	<t:panelGrid columns="2" styleClass="displayInfo">
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE_CGE']}"
			styleClass="form-field-label-inHeader-validator" for="inCGE"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.NB_VOEU']}"
			styleClass="form-field-label-inHeader-validator" for="nbV"/>

		<t:selectOneMenu id="inCGE"
			value="#{nbVoeuByCGEController.nbVoeuCge.codeCge}">
			<f:selectItems value="#{nbVoeuByCGEController.codeCgeItems}"/>
		</t:selectOneMenu>
		<e:inputText id="nbV" size="42" maxlength="40"
			value="#{nbVoeuByCGEController.nbVoeuCge.nbVoeu}"/>	
	</t:panelGrid>


