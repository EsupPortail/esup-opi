<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_TRT.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeTraitementTypesForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					<e:dataTable var="typeTraitement" value="#{nomenclatureController.typeTrts}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{typeTraitement.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{typeTraitement.label}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" />
						</t:column>
					</e:dataTable>

				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
			</h:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
</script>
</e:page>
