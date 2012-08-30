<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteManager">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['INDIVIDU.CONFIRM.DELETE']}">
				<f:param value="#{individuController.pojoIndividu.individu.nomPatronymique} #{individuController.pojoIndividu.individu.prenom}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{individuController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{individuController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
