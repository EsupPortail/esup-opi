<%@include file="../../../_include/_include.jsp"%>
<h:form id="formGoRecap">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CANDIDAT.SAVE_OK']} " />
				<e:text value="#{msgs['GESTIONNAIRE.CONFIRM.GOTO.SPACE_IND']}" />
			</t:panelGroup>
			
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{individuController.goSeeOneIndividu}">
					<t:updateActionListener
						value="#{individuController.actionEnum.emptyAction}"
						property="#{individuController.actionEnum.whatAction}" />
				</e:commandButton>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{individuController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
