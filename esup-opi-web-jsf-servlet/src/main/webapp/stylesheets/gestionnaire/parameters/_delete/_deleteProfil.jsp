<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteProfil">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['PROFIL.CONFIRM.DELETE']}">
				<f:param value="#{profilController.profil.code}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{profilController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{profilController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
