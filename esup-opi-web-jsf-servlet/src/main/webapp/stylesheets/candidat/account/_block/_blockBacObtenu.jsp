<%@include file="../../../_include/_include.jsp"%>
<t:div id="blockFormBac" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['INDIVIDU.BAC']}"
				styleClass="section-smallTitle" />
			</t:panelGroup>
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
					action="#{indBacController.add}" 
					rendered="#{empty indBacController.indBacPojos
					&& indBacController.actionEnum.whatAction == indBacController.actionEnum.addAction
					&& individuController.pojoIndividu.asRightsToUpdate}"
					/>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{indBacController.update}" 
					rendered="#{indBacController.actionEnum.whatAction == indBacController.actionEnum.updateAction}"
					/>
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					action="#{indBacController.goSeeIndBac}"
					rendered="#{indBacController.actionEnum.whatAction == indBacController.actionEnum.updateAction}"
					/>
				<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
					immediate="true" 
					rendered="#{!indBacPojo.indBac.temoinFromApogee 
						&& sessionController.whoIsConnectInPortlet != 'individu.not_exist'}"
					action="#{accueilController.goWelcomeCandidat}" />

			</t:panelGroup>
				
		</e:panelGrid>
	</t:div>
	
	<e:panelGrid styleClass="tableWidthMax" columns="2"
		columnClasses="colUnQuart,colTroisQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.PAY']}"
				for="paysBac" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="paysBac"
				onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectPay');}"
					valueChangeListener="#{indBacController.selectPay}"
				value="#{indBacController.indBac.codPay}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="pays" value="#{adressController.pays}"
					itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
			</e:selectOneMenu>
			<e:commandButton id="submitSelectPay"/>
		</t:panelGroup>
		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}">
			<e:outputLabel value="#{msgs['FIELD_LABEL.DEPARTEMENT']}" for="depBac" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>

		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}">
			<e:selectOneMenu id="depBac"
				onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectDep');}"
				valueChangeListener="#{indBacController.selectDep}"
				value="#{indBacController.indBac.codDep}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="dept" value="#{adressController.departements}"
					itemLabel="#{dept.libDep}" itemValue="#{dept.codDep}" />
			</e:selectOneMenu>
			<e:commandButton id="submitSelectDep"/>
		</t:panelGroup>
		
		
		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}">
			<e:outputLabel value="#{msgs['ADRESS.VIL']}"
				for="commune" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}">
			<%/*problème de rafraichissement du au valueChangeListener mais je ne sais pas pourquoi*/ %>
			<e:selectOneMenu id="commune"
					onchange="javascript:{simulateLinkClick('addCursusForm:submitSelectCommune');}"
					valueChangeListener="#{indBacController.selectCommune}" 
					value="#{indBacController.indBac.codCom}">
				<f:selectItem itemLabel="" itemValue=""/>
				<t:selectItems var="commune" 
					value="#{indBacController.communes}"
					itemLabel="#{commune.libCom}" itemValue="#{commune.codCom}"/>
			</e:selectOneMenu>
			<e:commandButton id="submitSelectCommune" />
		</t:panelGroup>
		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}" >
			<e:outputLabel value="#{msgs['ETABLISSEMENT']}" for="etabBac" />
			<t:popup styleClass="containerPopup" style="width:220px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"  />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
									value="#{msgs['ETABLISSEMENT']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ETABLISSEMENT.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<t:panelGroup rendered="#{indBacController.indBac.codPay == adressController.codeFrance}">
			<e:selectOneMenu id="etabBac"
				value="#{indBacController.indBac.codEtb}">
				<t:selectItems var="etab" 
					value="#{indBacController.lycees}"
					itemLabel="#{etab.libOffEtb}" itemValue="#{etab.codEtb}"/>
			</e:selectOneMenu>
		</t:panelGroup>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.BAC.DAA_OBT']}"
				for="daaObt" />
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
									value="#{msgs['INDIVIDU.BAC.DAA_OBT']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.BAC.DAA_OBT.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="daaObt" size="6" maxlength="4" 
				value="#{indBacController.indBac.dateObtention}"/>
			<e:commandButton id="submitDaaObt" />
		</t:panelGroup>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.SERIE_BAC']}"
				for="serieBac" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:250px;"
				rendered="#{indBacController.indBac.codPay != adressController.codeFrance}"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"  />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
									value="#{msgs['INDIVIDU.CURSUS.SERIE_BAC']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.CURSUS.SERIE_BAC.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:selectOneMenu id="serieBac"
				 value="#{indBacController.indBac.codBac}">
			<f:selectItem itemLabel="" itemValue=""/>
			<t:selectItems var="bac" 
				value="#{indBacController.bacs}"
				itemLabel="#{bac.libBac}" itemValue="#{bac.codBac}"/>
		</e:selectOneMenu>
		
		<e:outputLabel value="#{msgs['INDIVIDU.CURSUS.MENTION']}"
			for="mention" />
		<e:selectOneMenu id="mention"
			 value="#{indBacController.indBac.codMnb}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems var="mention" 
				value="#{indBacController.mentionNivBacs}"
				itemLabel="#{mention.libMnb}" itemValue="#{mention.codMnb}"/>
		</e:selectOneMenu>
		<t:panelGroup/>
		<t:panelGroup/>
	</e:panelGrid>
</t:div>
