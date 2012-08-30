<%@include file="../../../_include/_include.jsp"%>

<t:div id="blockFormEtatCivil" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['INDIVIDU.ETAT_CIV']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{individuController.update}"
					rendered="#{individuController.actionEnum.whatAction == 
								individuController.actionEnum.updateAction}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" 
					action="#{individuController.goSeeAccount}"
					rendered="#{individuController.actionEnum.whatAction == 
								individuController.actionEnum.updateAction}">
					<t:updateActionListener
						value="#{individuController.currentInd}"
						property="#{individuController.pojoIndividu}" />
					<t:updateActionListener
						value="#{individuController.actionEnum.readAction}"
						property="#{individuController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="2"
		columnClasses="colUnQuart,colTroisQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.CIVILITE']}" for="civilite" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:selectOneMenu id="civilite"
			value="#{individuController.pojoIndividu.individu.sexe}">
			<f:selectItems value="#{individuController.civiliteItems}" />
		</e:selectOneMenu>
		<t:panelGroup />
		<t:panelGroup />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.NOM']}" for="nom" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
			<t:popup styleClass="containerPopup" style="width:180px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['INDIVIDU.NOM']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.NOM.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="nom" size="32" maxlength="30"
			value="#{individuController.pojoIndividu.individu.nomPatronymique}" />
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.NOM_USUEL']}" for="nomUsuel" />
			<t:popup styleClass="containerPopup" style="width:180px;"
				closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
				displayAtDistanceX="1" displayAtDistanceY="0">
				<t:graphicImage url="/media/images/informationSmall.png"
					style="cursor:pointer;" />

				<f:facet name="popup">
					<h:panelGroup>
						<h:panelGrid columns="1">
							<t:outputText styleClass="titrePopupAide"
								value="#{msgs['INDIVIDU.NOM_USUEL']}" />
							<t:panelGroup>
								<t:outputText styleClass="libellesAide"
									value="#{msgs['INDIVIDU.NOM_USUEL.HELP']}" />
							</t:panelGroup>
						</h:panelGrid>
					</h:panelGroup>
				</f:facet>
			</t:popup>
		</t:panelGroup>
		<e:inputText id="nomUsuel" size="32" maxlength="30"
			value="#{individuController.pojoIndividu.individu.nomUsuel}" />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="prenom" size="22" maxlength="20"
			value="#{individuController.pojoIndividu.individu.prenom}" />
		<e:outputLabel value="#{msgs['INDIVIDU.PRENOM_OTHER']}" for="prenom2" />
		<e:inputText id="prenom2" size="22" maxlength="20"
			value="#{individuController.pojoIndividu.individu.prenom2}" />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.DATE_NAI_COURT']}"
				for="dateNaissance" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="jourNaissance"
				value="#{individuController.jourNaissance}">
				<f:selectItem itemLabel="  " itemValue="" />
				<f:selectItem itemLabel="01" itemValue="01" />
				<f:selectItem itemLabel="02" itemValue="02" />
				<f:selectItem itemLabel="03" itemValue="03" />
				<f:selectItem itemLabel="04" itemValue="04" />
				<f:selectItem itemLabel="05" itemValue="05" />
				<f:selectItem itemLabel="06" itemValue="06" />
				<f:selectItem itemLabel="07" itemValue="07" />
				<f:selectItem itemLabel="08" itemValue="08" />
				<f:selectItem itemLabel="09" itemValue="09" />
				<f:selectItem itemLabel="10" itemValue="10" />
				<f:selectItem itemLabel="11" itemValue="11" />
				<f:selectItem itemLabel="12" itemValue="12" />
				<f:selectItem itemLabel="13" itemValue="13" />
				<f:selectItem itemLabel="14" itemValue="14" />
				<f:selectItem itemLabel="15" itemValue="15" />
				<f:selectItem itemLabel="16" itemValue="16" />
				<f:selectItem itemLabel="17" itemValue="17" />
				<f:selectItem itemLabel="18" itemValue="18" />
				<f:selectItem itemLabel="19" itemValue="19" />
				<f:selectItem itemLabel="20" itemValue="20" />
				<f:selectItem itemLabel="21" itemValue="21" />
				<f:selectItem itemLabel="22" itemValue="22" />
				<f:selectItem itemLabel="23" itemValue="23" />
				<f:selectItem itemLabel="24" itemValue="24" />
				<f:selectItem itemLabel="25" itemValue="25" />
				<f:selectItem itemLabel="26" itemValue="26" />
				<f:selectItem itemLabel="27" itemValue="27" />
				<f:selectItem itemLabel="28" itemValue="28" />
				<f:selectItem itemLabel="29" itemValue="29" />
				<f:selectItem itemLabel="30" itemValue="30" />
				<f:selectItem itemLabel="31" itemValue="31" />
			</e:selectOneMenu>
			<e:selectOneMenu id="moisNaissance"
				value="#{individuController.moisNaissance}">
				<f:selectItem itemLabel="  " itemValue="" />
				<f:selectItem itemLabel="01" itemValue="01" />
				<f:selectItem itemLabel="02" itemValue="02" />
				<f:selectItem itemLabel="03" itemValue="03" />
				<f:selectItem itemLabel="04" itemValue="04" />
				<f:selectItem itemLabel="05" itemValue="05" />
				<f:selectItem itemLabel="06" itemValue="06" />
				<f:selectItem itemLabel="07" itemValue="07" />
				<f:selectItem itemLabel="08" itemValue="08" />
				<f:selectItem itemLabel="09" itemValue="09" />
				<f:selectItem itemLabel="10" itemValue="10" />
				<f:selectItem itemLabel="11" itemValue="11" />
				<f:selectItem itemLabel="12" itemValue="12" />
			</e:selectOneMenu>
			<e:selectOneMenu id="anneNaissance" value="#{individuController.anneeNaissance}" > 
				<f:selectItems value="#{individuController.listeAnneeNaissance}" /> 
			</e:selectOneMenu>
		</t:panelGroup>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.PAY_NAI']}"
				for="paysNaissance" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="paysNaissance"
				onchange="javascript:{simulateLinkClick('addAccountForm:submitSelectPay');}"
				valueChangeListener="#{individuController.selectPay}"
				value="#{individuController.pojoIndividu.individu.codPayNaissance}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="pays" value="#{adressController.pays}"
					itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
			</e:selectOneMenu>
			<e:commandButton id="submitSelectPay" />
		</t:panelGroup>

		<t:panelGroup
			rendered="#{individuController.pojoIndividu.individu.codPayNaissance == adressController.codeFrance}">
			<e:outputLabel value="#{msgs['INDIVIDU.DEP_NAI']}" for="depNaissance" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup
			rendered="#{individuController.pojoIndividu.individu.codPayNaissance == adressController.codeFrance}">
			<e:selectOneMenu id="depNaissance"
				value="#{individuController.pojoIndividu.individu.codDepPaysNaissance}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="dept" value="#{adressController.departements}"
					itemLabel="#{dept.libDep}" itemValue="#{dept.codDep}" />
			</e:selectOneMenu>
		</t:panelGroup>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.VIL_NAI']}"
				for="villeNaissance" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="villeNaissance" size="32" maxlength="30"
			value="#{individuController.pojoIndividu.individu.villeNaissance}" />

		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.NAT']}" for="nationalite" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:selectOneMenu id="nationalite"
			value="#{individuController.pojoIndividu.individu.codPayNationalite}">
			<f:selectItem itemLabel="" itemValue="" />
			<t:selectItems var="pays" value="#{adressController.nationalite}"
				itemLabel="#{pays.libNat}" itemValue="#{pays.codPay}" />
		</e:selectOneMenu>

		<t:panelGroup
			rendered="#{individuController.actionEnum.whatAction == 
								individuController.actionEnum.updateAction}">
			<e:outputLabel value="#{msgs['INDIVIDU.NUM_NNE']}" for="numNNE" />
		</t:panelGroup>
		<t:panelGroup
			rendered="#{individuController.actionEnum.whatAction == 
								individuController.actionEnum.updateAction}">
			<e:inputText id="numNNE" size="12" maxlength="10"
				value="#{individuController.pojoIndividu.individu.codeNNE}">
				<f:validateLength minimum="10" />
			</e:inputText>

			<e:inputText size="1" maxlength="1"
				value="#{individuController.pojoIndividu.individu.codeClefNNE}">
				<f:validateLength minimum="1" />
			</e:inputText>
		</t:panelGroup>

	</e:panelGrid>
</t:div>

