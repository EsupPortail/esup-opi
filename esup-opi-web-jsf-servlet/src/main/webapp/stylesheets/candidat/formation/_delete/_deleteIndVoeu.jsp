<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteVoeu">
	<t:div style="width:100%;">
		<e:text value="#{msgs['FORMATION.CONFIRM.DELETE']}" escape="false">
			<f:param value="#{formationController.indVoeuPojo.vrsEtape.libWebVet}"/>
		</e:text>
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.CONFIRM']}"/>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{formationController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{formationController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
