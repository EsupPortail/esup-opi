<%@include file="../../../_include/_include.jsp"%>
<t:div id="div_subSectionSendMail" styleClass="div_subSection"
	rendered="#{pjController.actionEnum.whatAction == pjController.actionEnum.sendMail}">

<h:form id="formSendMail">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['GESTIONNAIRE.CONFIRM.SEND.MAIL']}" />
			</t:panelGroup>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{pjController.sendMail}">
				</e:commandButton>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					immediate="true"
					action="#{pjController.reset}"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>

</t:div>
