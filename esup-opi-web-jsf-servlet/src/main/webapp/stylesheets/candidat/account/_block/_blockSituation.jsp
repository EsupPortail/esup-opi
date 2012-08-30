<%@include file="../../../_include/_include.jsp"%>

<t:div id="blockFormChoiceSituation" styleClass="blockForm"
	rendered="#{situationController.indSituation == null}">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['SITUATION.TITRE_CHOICE']}"
					styleClass="section-smallTitle" style="margin-right:4px;"/>
			</t:panelGroup>

			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{situationController.selectTypeSituation}"/>
				<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
					rendered="#{situationController.displayCreateDossier}"
					action="#{accueilController.goWelcomeCandidat}"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<t:div styleClass="blockTable">
		<t:dataList var="typeSit" value="#{situationController.typeSituations}"
			layout="unorderedList">
			<t:column>
				<opi:radioButton id="myRadioId1" overrideName="true"
					name="radioTypeSit"
					value="#{situationController.codeTypeSituation}"
					itemValue="#{typeSit.code}"/>
				<e:text value="#{typeSit.label}" styleClass="form-field-label" />
			</t:column>
		</t:dataList>
	</t:div>
</t:div>
<t:div id="blockFormSaveSituation" styleClass="blockForm"
	rendered="#{situationController.indSituation != null}">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
			<t:panelGroup/>

			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.SAVE']}"
					rendered="#{situationController.actionEnum.whatAction 
						== situationController.actionEnum.addAction
						&& situationController.displayCreateDossier}"
					action="#{situationController.add}"/>
				<e:commandButton value="#{msgs['_.BUTTON.SAVE']}"
					rendered="#{situationController.actionEnum.whatAction 
						== situationController.actionEnum.updateAction
						&& situationController.displayCreateDossier}"
					action="#{situationController.update}"/>
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					rendered="#{situationController.actionEnum.whatAction 
						!= situationController.actionEnum.readAction}"
					action="#{situationController.cancelSituation}"/>
				<e:commandButton value="#{msgs['BUTTON.UPDATE']}"
					rendered="#{situationController.actionEnum.whatAction 
						== situationController.actionEnum.readAction}"
					action="#{situationController.goUpdateSituation}"/>
				<e:commandButton value="#{msgs['_.BUTTON.CHANGE_SIT']}"
					rendered="#{situationController.actionEnum.whatAction 
						== situationController.actionEnum.readAction}"
					action="#{situationController.reset}"/>
				<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
					rendered="#{situationController.actionEnum.whatAction 
						== situationController.actionEnum.readAction
						&& situationController.displayCreateDossier}"
					action="#{accueilController.goWelcomeCandidat}"/>
			</t:panelGroup>
	</e:panelGrid>
</t:div>
<t:div id="blockSituationSal" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeSal}">
	<%@include file="_situation/_blockEnterSituationSalarie.jsp"%>
	
	<%@include file="_situation/_blockSeeSituationSalarie.jsp"%>
</t:div>
<t:div id="blockSituationProLib" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeProLib}">
	<%@include file="_situation/_blockEnterSituationProLib.jsp"%>
	
	<%@include file="_situation/_blockSeeSituationProLib.jsp"%>
</t:div>
<t:div id="blockSituationDemEmpl" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeDemEmp}">
	<%@include file="_situation/_blockEnterSituationDemEmpl.jsp"%>

	<%@include file="_situation/_blockSeeSituationDemEmpl.jsp"%>
</t:div>
<t:div id="blockSituationEtu" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeEtu}">
	<%@include file="_situation/_blockEnterSituationEtu.jsp"%>
</t:div>
<t:div id="blockSituationOther" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeOther}">
	<%@include file="_situation/_blockEnterSituationOther.jsp"%>

	<%@include file="_situation/_blockSeeSituationOther.jsp"%>
</t:div>
<t:div id="blockFormSituationHandi" styleClass="blockForm"
	rendered="#{situationController.indSituation != null}">
	<t:htmlTag value="br"/>
	<t:htmlTag value="hr"/>
	<e:panelGrid columns="2" width="100%"
		columnClasses="colUnQuart,colTroisQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['SITUATION.HANDI.LABEL']}" />
			<t:popup styleClass="containerPopup" style="width:300px;"
				closePopupOnExitingElement="true"
				closePopupOnExitingPopup="true" displayAtDistanceX="01"
				displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" />
				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="libellesAide"
								value="#{msgs['SITUATION.HANDI.INFO']}" />
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
			
		</t:panelGroup>
		<t:panelGroup>
			<t:graphicImage url="/media/images/check2.gif"
				rendered="#{situationController.indSituation.handicapTravail &&
							situationController.actionEnum.whatAction 
								== situationController.actionEnum.readAction}" />
			<t:graphicImage url="/media/images/check0.gif"
				rendered="#{!situationController.indSituation.handicapTravail &&
							situationController.actionEnum.whatAction 
								== situationController.actionEnum.readAction}" />
			<e:selectBooleanCheckbox id="handiWork"
				rendered="#{situationController.actionEnum.whatAction 
								!= situationController.actionEnum.readAction}"
				value="#{situationController.indSituation.handicapTravail}" />
			<e:outputLabel value="#{msgs['SITUATION.HANDI.WORK']}" style="margin-left:4px;"
				for="handiWork" />
		</t:panelGroup>
		<t:panelGroup/>
		<t:panelGroup>
			<t:graphicImage url="/media/images/check2.gif"
				rendered="#{situationController.indSituation.handicapAdulte &&
							situationController.actionEnum.whatAction 
								== situationController.actionEnum.readAction}" />
			<t:graphicImage url="/media/images/check0.gif"
				rendered="#{!situationController.indSituation.handicapAdulte &&
							situationController.actionEnum.whatAction 
								== situationController.actionEnum.readAction}" />
			<e:selectBooleanCheckbox id="handiAd"
				rendered="#{situationController.actionEnum.whatAction 
								!= situationController.actionEnum.readAction}"
				value="#{situationController.indSituation.handicapAdulte}" />
			<e:outputLabel value="#{msgs['SITUATION.HANDI.ADULTE']}" style="margin-left:4px;"
				for="handiAd" />
		</t:panelGroup>
	</e:panelGrid>
	
</t:div>
<t:div id="blockFormSituationEtuEnd" styleClass="blockForm"
	rendered="#{situationController.indSituation != null
				&& situationController.codeTypeSituation == situationController.typeEtu}">
	<e:text value="#{msgs['SITUATION.ETU.TEXT2']}" 
					escape="false" 
					style="font-weight:bold;color:#a71817;text-align: left;"/>
</t:div>
	