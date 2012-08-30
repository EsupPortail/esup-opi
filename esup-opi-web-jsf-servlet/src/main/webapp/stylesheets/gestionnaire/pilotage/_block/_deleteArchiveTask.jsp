<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteZrchiveTask">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['ARCHIVAGE.CONFIRM.DELETE']}" escape="false">
				<f:param value="#{archiveTaskController.archiveTask.campagneToArchive.libelle}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{archiveTaskController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{archiveTaskController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
