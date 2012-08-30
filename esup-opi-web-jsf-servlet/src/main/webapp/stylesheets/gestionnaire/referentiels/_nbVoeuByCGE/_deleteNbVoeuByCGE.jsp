<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteNbVoeuCGE">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['VOEU.CONFIRM.DELETE']}">
				<f:param value="#{nbVoeuByCGEController.nbVoeuCge.codeCge}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{nbVoeuByCGEController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{nbVoeuByCGEController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
