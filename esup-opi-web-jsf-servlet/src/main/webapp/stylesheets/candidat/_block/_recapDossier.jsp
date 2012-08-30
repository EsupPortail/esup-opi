<%@include file="../../_include/_include.jsp"%>
<t:div id="blockrecapDossier" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="Récapitulatif des données saisies"
				styleClass="section-smallTitle" />
			<e:commandButton value="Imprimer TODO" action="#{accueilController.printDossier}"/>
		</e:panelGrid>
	</t:div>
	<t:div style="width:100%;" styleClass="blockImportant">
		<t:htmlTag value="br" />
	</t:div>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />