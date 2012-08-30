<%@include file="../../../_include/_include.jsp"%>
<h:form id="formSendMail">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CANDIDAT.SAVE_OK']} " />
				<e:text value="#{msgs['GESTIONNAIRE.CONFIRM.SEND.MAIL']}" />
			</t:panelGroup>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{individuController.goSendMailStudent}">
					<t:updateActionListener
						value="#{individuController.actionEnum.readAction}"
						property="#{individuController.actionEnum.whatAction}" />
				</e:commandButton>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					immediate="true">
					<t:updateActionListener
						value="#{individuController.actionEnum.readAction}"
						property="#{individuController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
