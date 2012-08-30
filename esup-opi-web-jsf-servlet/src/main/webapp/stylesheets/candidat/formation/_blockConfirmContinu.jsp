<%@include file="../../_include/_include.jsp"%>
<t:div id="div_confirmFinish" styleClass="div_subSection"
	rendered="#{formationController.action.whatAction ==
				formationController.action.confirmAction}">
	<h:form id="formDeleteCmi">
		<t:div style="width:100%;" styleClass="blockImportant">
			<e:text
				value="Vous venez de postuler pour la(les) formation(s) suivante(s) : " />
			<t:dataList var="vetPojo" value="#{formationController.searchFormationPojo.vrsEtpSelected}"
				layout="unorderedList">
				<t:column>
					<e:text value="#{vet.versionEtape.libWebVet}" styleClass="span-text-small" />
				</t:column>
			</t:dataList>
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<e:text value="Voulez vous valider vos choix ?">
				</e:text>
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.YES']}" immediate="true"
						action="#{formationController.goRecapChoice}" />
					<e:commandButton value="#{msgs['_.BUTTON.NO']}"
						action="#{formationController.goSearchAgain}" immediate="true" />
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
	</h:form>
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
</t:div>

