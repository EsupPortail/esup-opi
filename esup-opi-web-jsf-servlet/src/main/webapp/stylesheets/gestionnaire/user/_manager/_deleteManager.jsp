<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteManager">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['GESTIONNAIRE.CONFIRM.DELETE']}">
				<f:param value="#{gestionnaireController.manager.displayName}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{gestionnaireController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{gestionnaireController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
