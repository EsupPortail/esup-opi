<%@include file="../../../_include/_include.jsp"%>
<h:form id="formEnterOpinion" >
	<t:div id="blockFormEnterOpinion" styleClass="blockForm">
		<t:div style="width:100%;">
			<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
				styleClass="tableJustWidthMax">
				<e:text value="#{msgs['AVIS.TITLE.ENTER']}" styleClass="section-smallTitle" />
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.addAction}"
						action="#{opinionController.add}" />
					<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
						rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.updateAction}"
						action="#{opinionController.update}" />
					<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
						action="#{opinionController.goSeeOpinionVoeu}">
						<t:updateActionListener
							value="#{opinionController.actionEnum.emptyAction}"
							property="#{opinionController.actionEnum.whatAction}" />
					</e:commandButton>
				</t:panelGroup>
			</e:panelGrid>
			<e:panelGrid styleClass="tableWidthMax" columns="2"
				columnClasses="colUnQuart,colTroisQuart">
				<t:panelGroup>
					<e:outputLabel value="#{msgs['AVIS.LABEL_DECISION']}" for="code" />
					<t:outputText value="*" styleClass="etoileForChpObli" />
				</t:panelGroup>
				<t:panelGroup>
					<e:selectOneMenu id="choiceTypDecision"
						converter="#{typeDecisionConverter}"
						onchange="javascript:{simulateLinkClick('formEnterOpinion:submitChangeTypeDecision');}"
						rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.addAction}"
						value="#{opinionController.selectedTypeDec}">
						<f:selectItems value="#{nomenclatureController.typeDecisionItems}" />
					</e:selectOneMenu>
					<e:text value="#{opinionController.avis.result.libelle}"/>
					<e:commandButton id="submitChangeTypeDecision"
						value="#{msgs[_.BUTTON.CHANGE]}"
						action="#{opinionController.selectTypeDecisionOpinion}" />
				</t:panelGroup>
				<t:panelGroup rendered="#{paginatorIndividuPojo.isUsingLC}">
					<e:outputLabel value="#{msgs['AVIS.RANG']}" />
				</t:panelGroup>
				<t:panelGroup rendered="#{paginatorIndividuPojo.isUsingLC}">
					<e:inputText id="rang"
						value="#{opinionController.avis.rang}" size="5"
						maxlength="5" />
				</t:panelGroup>
				<t:panelGroup rendered="#{paginatorIndividuPojo.isUsingDEF}">
					<e:outputLabel value="#{msgs['AVIS.MOTIVATION']}" />
				</t:panelGroup>
				<t:panelGroup rendered="#{paginatorIndividuPojo.isUsingDEF}">
					<e:selectOneMenu id="choiceMotivAvis" converter="javax.faces.Integer"
						onchange="javascript:{simulateLinkClick('formEnterOpinion:submitChangeMotiv');}"
						value="#{opinionController.idSelectedMotiv}">
						<f:selectItem itemLabel="" itemValue="" />
						<t:selectItems var="motivationAvis" value="#{nomenclatureController.motivationsAvis}"
							itemLabel="#{motivationAvis.shortLabel}" itemValue="#{motivationAvis.nomenclature.id}" />
					</e:selectOneMenu>
					<e:commandButton id="submitChangeMotiv" action="#{opinionController.setSelectedMotivation}"/>
					<t:popup styleClass="containerPopup" style="width:350px;"
						closePopupOnExitingElement="true"
						closePopupOnExitingPopup="true" displayAtDistanceX="-50"
						displayAtDistanceY="0"
						rendered="#{opinionController.selectedMotivation != null 
									&& opinionController.selectedMotivation.isShortLabel}">
						<t:graphicImage url="/media/images/informationSmall.png"
							style="cursor:pointer;" />
						<f:facet name="popup">
							<h:panelGroup>
								<h:panelGrid columns="1">
									<t:outputText styleClass="titrePopupAide"
										value="#{msgs['FIELD_LABEL.LIBELLE']}" />
									<t:outputText styleClass="libellesAide"
										value="#{opinionController.selectedMotivation.nomenclature.libelle}" />
								</h:panelGrid>
							</h:panelGroup>
						</f:facet>
					</t:popup>
				</t:panelGroup>
				<t:panelGroup>
					<e:outputLabel value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
				</t:panelGroup>
				<t:panelGroup>
					<e:inputText id="commentaire"
						value="#{opinionController.avis.commentaire}" size="80"
						maxlength="500" />
				</t:panelGroup>
			</e:panelGrid>
		</t:div>
		
	</t:div>
</h:form>
<script type="text/javascript">
	hideElement('formEnterOpinion:submitChangeTypeDecision');
	hideElement('formEnterOpinion:submitChangeMotiv');
</script>
