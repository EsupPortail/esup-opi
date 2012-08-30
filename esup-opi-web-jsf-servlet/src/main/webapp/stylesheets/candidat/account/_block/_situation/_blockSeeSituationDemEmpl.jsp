<%@include file="../../../../_include/_include.jsp"%>

<t:div id="blockFormSeeSituationDemEmpl"  
	rendered="#{situationController.actionEnum.whatAction 
					== situationController.actionEnum.readAction}"
	styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['SITUATION.DEM_EMPL.TITLE']}"
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
			<e:outputLabel value="#{msgs['SITUATION.DEM_EMPL.DATE_INSCR']}" for="dateInscSee" />
			<e:text id="dateInscSee" value="#{situationController.indSituation.dateInscriptionPE}">
				<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
			</e:text>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.DEM_EMPL.INDEMN']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<t:graphicImage url="/media/images/check2.gif"
					rendered="#{situationController.indSituation.compensated}" />
				<t:graphicImage url="/media/images/check0.gif"
					rendered="#{!situationController.indSituation.compensated}" />
			</t:panelGroup>
			
			<e:outputLabel value="#{msgs['SITUATION.DEM_EMPL.INDEMN.DATE_START']}" for="dateDebIndemSee" />
			<e:text id="dateDebIndemSee" value="#{situationController.indSituation.dateStartCompensation}">
				<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
			</e:text>
			<e:outputLabel value="#{msgs['SITUATION.DEM_EMPL.INDEMN.DATE_END']}" for="dateFinIndemSee" />
			<e:text id="dateFinIndemSee" value="#{situationController.indSituation.dateEndCompensation}">
				<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
			</e:text>

		</e:panelGrid>
	</t:div>
</t:div>
