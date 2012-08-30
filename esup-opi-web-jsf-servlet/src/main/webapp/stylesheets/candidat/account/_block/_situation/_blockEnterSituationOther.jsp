<%@include file="../../../../_include/_include.jsp"%>

<t:div id="blockFormSituationOther"  
	rendered="#{situationController.actionEnum.whatAction 
					!= situationController.actionEnum.readAction}"
	styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['SITUATION.OTHER.TITLE']}"
					styleClass="section-smallTitle" style="margin-right:4px;"/>
			</t:panelGroup>

			<t:panelGroup>
				<e:text value="#{msgs['INFO.CHAMP']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>

		</e:panelGrid>
	</t:div>
	<t:div styleClass="blockTable">
		<e:panelGrid columns="2" width="100%"
			columnClasses="colUnQuart,colTroisQuart">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.OTHER.ACTUAL_SIT']}" for="actualStatut" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="actualStatut" size="110" maxlength="100" 
				value="#{situationController.indSituation.actualSituation}"/>

		</e:panelGrid>
	</t:div>
</t:div>
