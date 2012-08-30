<%@include file="../../_include/_include.jsp"%>
<t:div id="div_subSectionChoiceEtp" styleClass="div_subSection"
	rendered="#{not empty formationController.searchFormationPojo.vrsEtpSelected}">
	<h:form id="recapEtapeForm" styleClass="opiR1_form">
		<t:div id="div_subSectionListChoiceEtp" styleClass="div_subSection">
			<t:div id="blockFormEtapeChoice" styleClass="blockForm">
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['FIELD_LABEL.FORMATION_SELECT']}"
							styleClass="section-smallTitle" />
						<e:commandButton value="#{msgs['_.BUTTON.BACK.RECAP']}"
							action="#{formationController.goRecapChoice}" />
					</e:panelGrid>
				</t:div>
				<t:div styleClass="blockTable">
					<t:dataList var="versionEtpPojo"
						value="#{formationController.searchFormationPojo.vrsEtpSelected}">
						<e:li>
							<e:text value="#{versionEtpPojo.versionEtape.libWebVet}" />
						</e:li>
					</t:dataList>
				</t:div>
			</t:div>
		</t:div>
	</h:form>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
</t:div>

