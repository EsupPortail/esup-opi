<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteFunction">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FUNCTION.CONFIRM.DELETE']}">
				<f:param value="#{fonctionController.fonction.code}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{fonctionController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{fonctionController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
