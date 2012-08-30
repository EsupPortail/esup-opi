<%@include file="../../../_include/_include.jsp"%>
<h:form id="formDeleteNomenclature">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['NOMENCLATURE.CONFIRM.DELETE']}" escape="false">
				<f:param value="#{nomenclatureController.nomenclature.code}"/>
				<f:param value="#{nomenclatureController.nomenclature.libelle}"/>
			</e:text>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.YES']}"
					action="#{nomenclatureController.delete}"/>
				<e:commandButton value="#{msgs['_.BUTTON.NO']}"
					action="#{nomenclatureController.reset}"
					immediate="true"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
</h:form>
