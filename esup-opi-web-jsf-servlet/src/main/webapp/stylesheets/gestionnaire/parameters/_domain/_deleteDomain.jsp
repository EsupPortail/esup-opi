<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteDomain">
	<t:div style="width:100%;">
		<t:graphicImage url="/media/images/information.png"/>
		<e:text value="#{msgs['DOMAIN.INFO.DELETE']}"/>
	</t:div>
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['DOMAIN.CONFIRM.DELETE']}">
				<f:param value="#{domainController.domain.code}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{domainController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{domainController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
