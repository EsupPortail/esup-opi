<%@include file="../../../_include/_include.jsp"%>

<t:div rendered="#{cursusController.confirmeDelete}" styleClass="confirmeDelete">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
		<e:text value="#{msgs['INDIVIDU.CURSUS_PRO.CONFIRM.DELETE']}">
			<f:param value="#{cursusController.indCursusPojo.cursus.annee}"/>
			<f:param value="#{cursusController.indCursusPojo.cursus.organisme}"/>
			<f:param value="#{cursusController.indCursusPojo.cursus.duree}"/>
		</e:text>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.YES']}"
				action="#{cursusController.removeCursusPro}">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
			<e:commandButton value="#{msgs['_.BUTTON.NO']}"
				action="#{cursusController.goSeeCursusPro}"
				immediate="true">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
		</t:panelGroup>
	</e:panelGrid>
</t:div>

<t:div id="blockFormCursusExt" styleClass="blockForm">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		rendered="#{!cursusController.confirmeDelete}"
		styleClass="tableJustWidthMax">
		<t:panelGroup>
			<e:text value="#{msgs['INDIVIDU.CURSUS_EXT']}"
				styleClass="section-smallTitle" />
			<t:popup styleClass="containerPopup" style="width:300px;"
				closePopupOnExitingElement="true"
				closePopupOnExitingPopup="true" displayAtDistanceX="01"
				displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" />
				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['INDIVIDU.CURSUS_EXT']}" />
							<t:outputText styleClass="libellesAide"
								value="#{msgs['INDIVIDU.CURSUS_EXT.DESC']}" />
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
				action="#{cursusController.addCursusPro}" 
				rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{cursusController.goSeeCursusPro}"
				rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
				rendered="#{cursusController.actionEnum.whatAction == cursusController.actionEnum.updateAction}"
				action="#{accueilController.verifyCursusPro}">
				<t:updateActionListener value="goWelcomeCandidat"
					property="#{accueilController.methodIfVerified}" />
				<t:updateActionListener
							value="#{accueilController}"
							property="#{accueilController.object}" />
			</e:commandButton>

		</t:panelGroup>
	</e:panelGrid>
	<e:panelGrid columns="4" styleClass="tableWidthMax"
		rendered="#{!cursusController.confirmeDelete}">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.YEAR']}" 
				for="anneeCursusPro" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="anneeCursusPro" value="#{cursusController.indCursusPojo.cursus.annee}" size="21" maxlength="20" />
		</t:panelGroup>
		<t:panelGroup/>
		<t:panelGroup/>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.QUOTITE']}" for="quotiteCurPro" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="quotiteCurPro" value="#{cursusController.indCursusPojo.cursus.quotite}" size="11" maxlength="10" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.ORGANIZATION']}" for="organismeCurPro" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="organismeCurPro" value="#{cursusController.indCursusPojo.cursus.organisme}" maxlength="70" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.PERIOD']}" for="periodCurPro" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="periodCurPro" 
					value="#{cursusController.indCursusPojo.cursus.duree}" 
					size="20" maxlength="20" />
		</t:panelGroup>
		<t:panelGroup/>
		<t:panelGroup/>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.DESC_ACTV']}" for="commentCurPro" />
			<t:popup styleClass="containerPopup"
                    style="width:350px;"
                    closePopupOnExitingElement="true"
                    closePopupOnExitingPopup="true" displayAtDistanceX="10"
                    displayAtDistanceY="0"
                    rendered="#{cursusPojo.isShortComment}">
                    <t:graphicImage url="../../../media/images/informationSmall.png"
                        style="cursor:pointer;" id="infoAnneeUni" />
                    <f:facet name="popup">
                        <h:panelGroup>
                            <h:panelGrid columns="1">
                                <t:outputText styleClass="titrePopupAide" value="#{msgs['FIELD_LABEL.DESC_ACTV']}"/>
                                <t:outputText styleClass="libellesAide" value="#{msgs['FIELD_LABEL.DESC_ACTV.HELP']}"/>
                            </h:panelGrid>
                        </h:panelGroup>
                    </f:facet>
                </t:popup>
		</t:panelGroup>
		<t:panelGroup>
			<e:inputTextarea id="commentCurPro"  
					value="#{cursusController.indCursusPojo.cursus.comment}" 
					onkeyup="this.value = this.value.slice(0, 2000)" onchange="this.value = this.value.slice(0, 2000)"
					rows="3" style="width:250px;" />
		</t:panelGroup>
		<t:panelGroup/>
		<t:panelGroup/>
	</e:panelGrid>

	<t:htmlTag value="br" />
	<t:htmlTag value="br" />

	<e:dataTable var="cursusPojo"
		value="#{cursusController.indCursusPojo.pojoCursusList}"
		styleClass="displayInfo" alternateColors="false" renderedIfEmpty="false">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.YEAR']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.annee}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.PERIOD']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.duree}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.QUOTITE']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.quotite}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.ORGANIZATION']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.organisme}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.DESC_ACTV']}" />
			</f:facet>
			<t:panelGroup>
				<e:text value="#{cursusPojo.shortComment}" />
				<t:popup styleClass="containerPopup"
                    style="width:250px;"
                    closePopupOnExitingElement="true"
                    closePopupOnExitingPopup="true" displayAtDistanceX="-250"
                    displayAtDistanceY="0"
                    rendered="#{cursusPojo.isShortComment}">
                    <t:graphicImage url="../../../media/images/informationSmall.png"
                        style="cursor:pointer;" id="infoAnneeUni" />
                    <f:facet name="popup">
                        <h:panelGroup>
                            <h:panelGrid columns="1">
                                <t:outputText styleClass="titrePopupAide" value="#{msgs['FIELD_LABEL.DESC_ACTV']}"/>
                                <t:outputText styleClass="libellesAide" value="#{cursusPojo.cursus.comment}"/>
                            </h:panelGrid>
                        </h:panelGroup>
                    </f:facet>
                </t:popup>			
			</t:panelGroup>
		</t:column>
		<t:column styleClass="buttonTD"
			rendered="#{!cursusController.confirmeDelete}">
			<e:commandButton image="/media/images/cancel.png"
				styleClass="form-button-image" immediate="true"
				title="#{msgs['_.BUTTON.DELETE']}"
				rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}">
				<t:updateActionListener value="true"
						property="#{cursusController.confirmeDelete}" />
				<t:updateActionListener value="#{cursusPojo.cursus}"
					property="#{cursusController.indCursusPojo.cursus}" />
			</e:commandButton>
		</t:column>
	</e:dataTable>	

</t:div>
