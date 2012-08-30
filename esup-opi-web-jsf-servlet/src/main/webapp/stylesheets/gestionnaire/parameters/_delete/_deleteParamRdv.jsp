<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteRdv">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['CALENDAR_RDV.CONFIRM.DELETE']}">
				<f:param value="#{paramRdvController.calendarRDV.titre}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{paramRdvController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{paramRdvController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
