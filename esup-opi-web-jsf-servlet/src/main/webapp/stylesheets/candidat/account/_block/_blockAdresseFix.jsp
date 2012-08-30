<%@include file="../../../_include/_include.jsp"%>

<t:div id="blockFormAdresseFix" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.CONTACT']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{individuController.update}"
					rendered="#{adressController.actionEnum.whatAction == 
								adressController.actionEnum.updateAction}" />							
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
					immediate="true"
					action="#{individuController.initAdresse}"
					rendered="#{adressController.actionEnum.whatAction == 
								adressController.actionEnum.updateAction}">
					<t:updateActionListener
						value="#{individuController.currentInd}"
						property="#{individuController.pojoIndividu}" />
					<t:updateActionListener
						value="#{adressController.actionEnum.emptyAction}"
						property="#{adressController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="2"
		columnClasses="colUnQuart,colTroisQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" for="email" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"   />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['FIELD_LABEL.MAIL_PERSO.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="email"
			value="#{individuController.pojoIndividu.individu.adressMail}"
			size="35" maxlength="50" onkeypress="javascript:{return disableCtrlKeyCombination(event);}"
        	onkeydown="javascript:{return disableCtrlKeyCombination(event);}"/>
	
			<t:panelGroup>
				<t:outputLabel styleClass="form-field-label" value="#{msgs['FIELD_LABEL.MAIL_CHECK']}" for="emailCheck" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:inputText id="emailCheck"
					value="#{individuController.checkEmail}"
					size="35" maxlength="50" onkeypress="javascript:{return disableCtrlKeyCombination(event);}"
        			onkeydown="javascript:{return disableCtrlKeyCombination(event);}"/>
			</t:panelGroup>

		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_ANNUAIRE']}"
			rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}"
				for="emailAnnuaire" />
		</t:panelGroup>
		<t:panelGroup>
			<e:text id="emailAnnuaire"
				rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}"
				value="#{individuController.pojoIndividu.individu.emailAnnuaire}" />
		</t:panelGroup>

		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}" for="telephone" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"   />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['ADRESS.TEL_FIX']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ADRESS.TEL_POR.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="telephone" size="11" maxlength="15"
			value="#{adressController.fixAdrPojo.adresse.phoneNumber}" />


		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.TEL_POR']}" for="portable" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"   />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['ADRESS.TEL_POR']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ADRESS.TEL_POR.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="portable" size="11" maxlength="15"
			value="#{individuController.pojoIndividu.individu.numeroTelPortable}" />
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="paysAdr" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="paysAdr" 
				value="#{adressController.fixAdrPojo.adresse.codPays}"
				onchange="javascript:{simulateLinkClick('addAccountForm:submitSelectPayAdr');}"
				valueChangeListener="#{adressController.selectPayAdr}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="pays" value="#{adressController.pays}"
					itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
			</e:selectOneMenu>		
			<e:commandButton id="submitSelectPayAdr" />
		</t:panelGroup>
		<t:panelGroup />
		<t:panelGroup />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['ADRESS']}" for="adr1" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"   />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['ADRESS']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ADRESS.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="adr1" value="#{adressController.fixAdrPojo.adresse.adr1}" size="32" maxlength="32" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr2" />
		<e:inputText id="adr2" value="#{adressController.fixAdrPojo.adresse.adr2}" size="40" maxlength="70" />
		<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr3" />
		<e:inputText id="adr3" value="#{adressController.fixAdrPojo.adresse.adr3}" size="40" maxlength="70" />
		<t:panelGroup 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}">
			<e:outputLabel value="#{msgs['ADRESS.COD_POST']}"
				for="codePostalFix" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:200px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;"   />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['ADRESS.COD_POST']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['ADRESS.COD_POST.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<t:panelGroup 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}">
			<e:inputText id="codePostalFix" value="#{adressController.fixAdrPojo.adresse.codBdi}" 
				size="6" maxlength="5" />
			<e:commandButton id="submitSelectCpFix" action="#{adressController.selectCpFix}"/>
		</t:panelGroup>
		
		<t:panelGroup 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}">
			<e:outputLabel value="#{msgs['ADRESS.VIL']}" for="ville" />
			<t:outputText value="*" styleClass="etoileForChpObli"/>
		</t:panelGroup>
		<e:selectOneMenu id="ville" 
				rendered="#{adressController.fixAdrPojo.adresse.codPays 
					== adressController.codeFrance}"
				value="#{adressController.fixAdrPojo.adresse.codCommune}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems var="ville" value="#{adressController.communesFix}"
				itemLabel="#{ville.libCommune}" itemValue="#{ville.codeCommune}" />
		</e:selectOneMenu>
		<t:panelGroup 
			rendered="#{adressController.fixAdrPojo.adresse.codPays 
					!= adressController.codeFrance}">
			<e:outputLabel value="#{msgs['ADRESS.VIL.ETR']}" for="villeEtr" />
			<t:outputText value="*" styleClass="etoileForChpObli"/>
		</t:panelGroup>
		<e:inputText id="villeEtr" 
				rendered="#{adressController.fixAdrPojo.adresse.codPays 
					!= adressController.codeFrance}"
				value="#{adressController.fixAdrPojo.adresse.libComEtr}">
		</e:inputText>

		<t:panelGroup />
		<t:panelGroup />
	</e:panelGrid>
</t:div>