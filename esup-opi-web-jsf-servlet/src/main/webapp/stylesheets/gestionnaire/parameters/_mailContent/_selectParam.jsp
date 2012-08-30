<%@include file="../../../_include/_include.jsp"%>
<h:form id="formselectParam">
	<t:div id="blockFormButton" styleClass="blockForm">
		<t:div style="width:100%;text-align:right;">
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
					action="#{mailContentController.reset}">
				</e:commandButton>
			</t:panelGroup>
		</t:div>


		<e:panelGrid styleClass="tableWidthMax" columns="2"
			columnClasses="col1UnDemi,col2UnDemi">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['MAIL_CONTENT.SELECT_PARAM']}" for="param" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="param"
					valueChangeListener="#{mailContentController.selectAttribut}"
					onchange="javascript:{simulateLinkClick('formselectParam:selectAttribut');}"
					value="#{mailContentController.attributSelected}">
					<f:selectItem itemLabel="" itemValue=""/>
					<f:selectItems value="#{mailContentController.attributes}" />
				</e:selectOneMenu>
				<e:commandButton id="selectAttribut" 
						value="#{msgs['_.BUTTON.CHANGE']}"/>
			</t:panelGroup>
		</e:panelGrid>

		<e:paragraph rendered="#{mailContentController.attributSelected != null}"
			value="#{msgs['MAIL_CONTENT.ATTRIBUT_SELECT']}" escape="false">
			<f:param value="#{mailContentController.attributSelected}"/>
		</e:paragraph>

	</t:div>
</h:form>
<script type="text/javascript">
	highlightInputAndSelect('formselectParam');
	hideElement('formselectParam:selectAttribut');
</script>