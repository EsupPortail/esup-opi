<%@include file="../../../_include/_include.jsp"%>

<t:div rendered="#{cursusController.confirmeDelete}" styleClass="confirmeDelete">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
		<e:text value="#{msgs['INDIVIDU.CURSUS_SCOL.CONFIRM.DELETE']}">
			<f:param value="#{cursusController.pojoCursusScol.cursus.annee}"/>
			<f:param value="#{cursusController.pojoCursusScol.libEtb}"/>
			<f:param value="#{cursusController.pojoCursusScol.libCur}"/>
		</e:text>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.YES']}"
				action="#{cursusController.removeCursusScol}">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
			<e:commandButton value="#{msgs['_.BUTTON.NO']}"
				action="#{cursusController.initCursusScol}"
				immediate="true">
				<t:updateActionListener value="false"
					property="#{cursusController.confirmeDelete}" />
			</e:commandButton>
		</t:panelGroup>
	</e:panelGrid>
</t:div>

<t:div id="blockFormCursusUni" styleClass="blockForm">
	<t:div style="width:100%;"
		rendered="#{!cursusController.confirmeDelete}">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['INDIVIDU.CURSUS_UNI']}"
					styleClass="section-smallTitle" style="margin-right:4px;"/>
				<e:text value="#{msgs['INDIVIDU.CURSUS_UNI.INFO']}"
					styleClass="span-text-small-italic" />
				<t:popup styleClass="containerPopup" style="width:400px;"
					closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
					displayAtDistanceX="01" displayAtDistanceY="0">
					<t:graphicImage url="/media/images/informationSmall.png"
						style="cursor:pointer;" />
					<f:facet name="popup">
						<h:panelGroup>
							<h:panelGrid columns="1">
								<t:outputText styleClass="titrePopupAide"
									value="#{msgs['INDIVIDU.CURSUS_UNI']}" />
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.CURSUS_UNI.DESC']}" />
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.CURSUS_UNI.DESC2']}" />
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.CURSUS_UNI.DESC3']}" />
							</h:panelGrid>
						</h:panelGroup>
					</f:facet>
				</t:popup>
			</t:panelGroup>

			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
					action="#{cursusController.addCursusScol}" 
					rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}"/>
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					action="#{cursusController.initCursusScol}"
					rendered="#{sessionController.currentInd == null || sessionController.currentInd.asRightsToUpdate}">
				</e:commandButton>

				<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
					rendered="#{cursusController.actionEnum.whatAction == cursusController.actionEnum.updateAction}"
					action="#{accueilController.verifyCursusScol}">
					<t:updateActionListener value="goWelcomeCandidat"
						property="#{accueilController.methodIfVerified}" />
					<t:updateActionListener
							value="#{accueilController}"
							property="#{accueilController.object}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<t:div styleClass="blockTable"
		rendered="#{!cursusController.confirmeDelete}">
		<e:panelGrid columns="2" width="100%"
			columnClasses="colUnQuart,colTroisQuart">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="paysBac" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="paysBac"
					onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectPay');}"
					valueChangeListener="#{cursusController.selectPay}"
					value="#{cursusController.pojoCursusScol.codPay}">
					<t:selectItems var="pays" value="#{adressController.pays}"
						itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
				</e:selectOneMenu>
				<e:commandButton id="submitSelectPay" />
			</t:panelGroup>

			<t:panelGroup
				rendered="#{cursusController.pojoCursusScol.codPay == adressController.codeFrance}">
				<e:outputLabel value="#{msgs['FIELD_LABEL.DEPARTEMENT']}"
					for="depBac" />
			</t:panelGroup>
			<t:panelGroup
				rendered="#{cursusController.pojoCursusScol.codPay == adressController.codeFrance}">
				<e:selectOneMenu id="depBac"
					onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectDep');}"
					valueChangeListener="#{cursusController.selectDep}"
					value="#{cursusController.pojoCursusScol.codDep}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="dept" value="#{adressController.departements}"
						itemLabel="#{dept.libDep}" itemValue="#{dept.codDep}" />
				</e:selectOneMenu>
				<e:commandButton id="submitSelectDep" />
			</t:panelGroup>

			<t:panelGroup
				rendered="#{cursusController.pojoCursusScol.codPay == adressController.codeFrance}">
				<e:outputLabel value="#{msgs['ADRESS.VIL']}" for="commune" />
			</t:panelGroup>
			<t:panelGroup
				rendered="#{cursusController.pojoCursusScol.codPay == adressController.codeFrance}">
				<e:selectOneMenu id="commune"
					valueChangeListener="#{cursusController.selectCommune}"
					onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectCommune');}"
					value="#{cursusController.pojoCursusScol.codCom}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="commune" value="#{cursusController.communes}"
						itemLabel="#{commune.libCom}" itemValue="#{commune.codCom}" />
				</e:selectOneMenu>
				<e:commandButton id="submitSelectCommune" />
			</t:panelGroup>


			<t:panelGroup>
				<e:text value="#{msgs['FIELD_LABEL.ETABLISSEMENT']}"
					styleClass="form-field-label" />
				<t:outputText value="*" styleClass="etoileForChpObli"
					rendered="#{cursusController.pojoCursusScol.codPay != adressController.codeFrance}" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="codEtablissementUni"
					rendered="#{cursusController.pojoCursusScol.codPay == adressController.codeFrance}"
					value="#{cursusController.pojoCursusScol.cursus.codEtablissement}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="etb" value="#{cursusController.etablissements}"
						itemLabel="#{etb.libOffEtb}" itemValue="#{etb.codEtb}" />
				</e:selectOneMenu>
				<e:inputText value="#{cursusController.pojoCursusScol.cursus.libEtbEtr}"  rendered="#{cursusController.pojoCursusScol.codPay != adressController.codeFrance}"/>
			</t:panelGroup>

			<t:panelGroup>
				<e:text value="#{msgs['INDIVIDU.CURSUS.ANNEE_UNI']}"
					styleClass="form-field-label" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
				<t:popup styleClass="containerPopup" style="width:220px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"  />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
									value="#{msgs['INDIVIDU.CURSUS.ANNEE_UNI']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.CURSUS.ANNEE_UNI.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
			</t:panelGroup>
			<e:inputText id="anneeCursusUni"
				value="#{cursusController.pojoCursusScol.cursus.annee}" size="6"
				maxlength="4" />

			<t:panelGroup>
				<e:text value="#{msgs['FIELD_LABEL.DIPLOME_PREP']}"
					styleClass="form-field-label"/>
				<t:outputText value="*" styleClass="etoileForChpObli" />
				<t:popup styleClass="containerPopup" style="width:220px;"
					closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
					displayAtDistanceX="1" displayAtDistanceY="0">
					<t:graphicImage url="/media/images/informationSmall.png"
						style="cursor:pointer;"  />
	
					<f:facet name="popup">
						<h:panelGroup>
							<h:panelGrid columns="1">
								<t:outputText styleClass="titrePopupAide"
									value="#{msgs['FIELD_LABEL.DIPLOME_PREP']}" />
								<t:panelGroup>
									<t:outputText styleClass="libellesAide"
										value="#{msgs['FIELD_LABEL.DIPLOME_PREP.INFOS']}" />
								</t:panelGroup>
							</h:panelGrid>
						</h:panelGroup>
					</f:facet>
				</t:popup>
			</t:panelGroup>
			<e:selectOneMenu id="codDiplomeUni"
				value="#{cursusController.pojoCursusScol.cursus.codDac}">
				<t:selectItems var="dipAutCur"
					value="#{cursusController.dipAutCurs}"
					itemLabel="#{dipAutCur.libDac}" itemValue="#{dipAutCur.codDac}" />
			</e:selectOneMenu>

			<t:panelGroup>
				<e:text value="#{msgs['FIELD_LABEL.DIPLOME.OBT']}"
					styleClass="form-field-label" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:selectOneMenu id="resultatUni"
				value="#{cursusController.pojoCursusScol.cursus.resultat}">
				<f:selectItem itemLabel="" itemValue="" />
				<f:selectItems value="#{cursusController.resultItems}" />
			</e:selectOneMenu>
			
			<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.MENTION']}"
					for="mention" />
			<e:selectOneMenu id="mention"
				 value="#{cursusController.pojoCursusScol.cursus.libMention}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="mention" 
					value="#{indBacController.mentionNivBacs}"
					itemLabel="#{mention.libMnb}" itemValue="#{mention.libMnb}"/>
			</e:selectOneMenu>

		</e:panelGrid>
		<t:htmlTag value="br"/>
		<t:htmlTag value="hr"/>
		<t:div>
			<e:text value="#{msgs['FIELD_LABEL.DIPLOME.EXACT']}"
				styleClass="form-field-label" style="margin-right:10px;"/>
			<e:inputText size="100" maxlength="200" value="#{cursusController.pojoCursusScol.cursus.libelle}"/>
		</t:div>
	</t:div>

	<t:htmlTag value="br" />
	<t:htmlTag value="br" />

	<t:div style="text-align:right;width:100%;"  
					rendered="#{sessionController.currentInd.isManager && sessionController.currentInd.asRightsToUpdate
								&& !cursusController.confirmeDelete}">
		<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
					action="#{cursusController.updateCursusApogee}"/>
	</t:div>
	<e:dataTable var="cursusPojo" value="#{cursusController.cursusList}"
		styleClass="displayInfo" alternateColors="false"
		renderedIfEmpty="false">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['INDIVIDU.CURSUS.ANNEE_UNI']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.annee}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.FORMATION']}" />
			</f:facet>
			<e:text value="#{cursusPojo.libCur}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.ETABLISSEMENT']}" />
			</f:facet>
			<e:text value="#{cursusPojo.libEtb}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.DIPLOME.OBT']}" />
			</f:facet>
			<e:text value="#{cursusPojo.resultatExt}" />
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.MENTION']}" />
			</f:facet>
			<e:text value="#{cursusPojo.cursus.libMention}"/>
		</t:column>
		<t:column styleClass="buttonTD"
			rendered="#{!cursusController.confirmeDelete}">
			<e:commandButton image="/media/images/cancel.png"
				styleClass="form-button-image" immediate="true"
				title="#{msgs['_.BUTTON.DELETE']}"
				rendered="#{!cursusPojo.cursus.temoinFromApogee &&
				(sessionController.currentInd == null 
				  || sessionController.currentInd.asRightsToUpdate)}">
				<t:updateActionListener value="true"
						property="#{cursusController.confirmeDelete}" />
				<t:updateActionListener value="#{cursusPojo}"
					property="#{cursusController.pojoCursusScol}" />
			</e:commandButton>
		</t:column>
	</e:dataTable>

</t:div>
