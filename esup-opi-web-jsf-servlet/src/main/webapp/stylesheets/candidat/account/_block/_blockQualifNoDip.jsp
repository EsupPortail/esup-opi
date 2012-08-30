<%@include file="../../../_include/_include.jsp"%>

<t:div rendered="#{cursusController.confirmeDelete}" styleClass="confirmeDelete">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
		<e:text value="#{msgs['INDIVIDU.QUALIF.CONFIRM.DELETE']}">
			<f:param value="#{cursusController.pojoQualif.cursus.libelle}"/>
		</e:text>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.YES']}"
				action="#{cursusController.removeQualif}">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
			<e:commandButton value="#{msgs['_.BUTTON.NO']}"
				action="#{cursusController.goSeeQualif}"
				immediate="true">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
		</t:panelGroup>
	</e:panelGrid>
</t:div>

<t:div id="blockFormQualifNoDip" styleClass="blockForm">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		rendered="#{!cursusController.confirmeDelete}"
		styleClass="tableJustWidthMax">
		<t:panelGroup>
			<e:text value="#{msgs['INDIVIDU.QUALIF.NO_DIP']}"
				styleClass="section-smallTitle" />
			<t:popup styleClass="containerPopup" style="width:400px;"
				closePopupOnExitingElement="true"
				closePopupOnExitingPopup="true" displayAtDistanceX="01"
				displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" />
				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['INDIVIDU.QUALIF.NO_DIP']}" />
							<t:outputText styleClass="libellesAide"
								value="#{msgs['INDIVIDU.QUALIF.NO_DIP.DESC']}" />
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
				action="#{cursusController.addQualifNoDip}" 
				rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{cursusController.goSeeQualif}"
				rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
				rendered="#{cursusController.actionEnum.whatAction == cursusController.actionEnum.updateAction}"
				action="#{accueilController.verifyQualif}">
				<t:updateActionListener value="goWelcomeCandidat"
					property="#{accueilController.methodIfVerified}" />
				<t:updateActionListener
							value="#{accueilController}"
							property="#{accueilController.object}" />
			</e:commandButton>
						
						
		</t:panelGroup>
	</e:panelGrid>
	<e:panelGrid columns="6" styleClass="tableWidthMax"
		rendered="#{!cursusController.confirmeDelete}">
		<t:panelGroup>
			<e:text value="#{msgs['FIELD_LABEL.YEAR']}" styleClass="form-field-label" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="anneeQualif" value="#{cursusController.pojoQualif.cursus.annee}" size="11" maxlength="10" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:text value="#{msgs['FIELD_LABEL.PERIOD']}" styleClass="form-field-label" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="dureeQualif" value="#{cursusController.pojoQualif.cursus.duree}" size="11" maxlength="10" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:text value="#{msgs['FIELD_LABEL.INTITULE']}" styleClass="form-field-label" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="libelleQualif" value="#{cursusController.pojoQualif.cursus.libelle}" maxlength="50" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:text value="#{msgs['INDIVIDU.QUALIF.NO_DIP.ORG']}" styleClass="form-field-label" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="organismeQualif" value="#{cursusController.pojoQualif.cursus.organisme}" maxlength="70" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:text value="#{msgs['INDIVIDU.QUALIF.NO_DIP.OBJ']}" styleClass="form-field-label" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputTextarea id="commentQualif" value="#{cursusController.pojoQualif.cursus.comment}" style="width:250px;" />
		</t:panelGroup>

	</e:panelGrid>
	
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />

	<e:dataTable var="cursusPojo"
		value="#{cursusController.pojoQualif.pojoCursusList}"
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
				<t:outputText value="#{msgs['FIELD_LABEL.INTITULE']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.libelle}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['INDIVIDU.QUALIF.NO_DIP.ORG']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.organisme}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['INDIVIDU.QUALIF.NO_DIP.OBJ']}" />
			</f:facet>
			<t:panelGroup>
                <e:text value="#{cursusPojo.shortComment}" />
                <t:popup styleClass="containerPopup"
                    style="width:400px;"
                    closePopupOnExitingElement="true"
                    closePopupOnExitingPopup="true" displayAtDistanceX="-350"
                    displayAtDistanceY="-100"
                    rendered="#{cursusPojo.isShortComment}">
                    <t:graphicImage url="../../../media/images/informationSmall.png"
                        style="cursor:pointer;" id="infoAnneeUni" />
                    <f:facet name="popup">
                        <h:panelGroup>
                            <h:panelGrid columns="1">
                                <t:outputText styleClass="titrePopupAide" value="#{msgs['FIELD_LABEL.COMMENT']}"/>
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
				rendered="#{(sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate)}">
				<t:updateActionListener value="true"
						property="#{cursusController.confirmeDelete}" />
				<t:updateActionListener value="#{cursusPojo.cursus}"
					property="#{cursusController.pojoQualif.cursus}" />
			</e:commandButton>
		</t:column>		
	</e:dataTable>	
	
</t:div>