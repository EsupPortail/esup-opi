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
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
					rendered="#{individuController.actionEnum.whatAction == 
								individuController.actionEnum.updateAction}">
					<t:updateActionListener
						value="#{individuController.currentInd}"
						property="#{individuController.pojoIndividu}" />
					<t:updateActionListener
						value="#{individuController.actionEnum.emptyAction}"
						property="#{individuController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="4"
		columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.CIVILITE']}" for="civilite" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="civilite"
				value="#{individuController.pojoIndividu.individu.sexe}">
				<f:selectItems value="#{individuController.civiliteItems}" />
			</e:selectOneMenu>
		</t:panelGroup>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.NOM']}" for="nom" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="nom" size="32" maxlength="30"
				value="#{individuController.pojoIndividu.individu.nomPatronymique}" />
		</t:panelGroup>

		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="prenom" size="22" maxlength="20"
				value="#{individuController.pojoIndividu.individu.prenom}" />
		</t:panelGroup>
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
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" for="email" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="email"
				value="#{individuController.pojoIndividu.individu.adressMail}"
									size="35" maxlength="50" onkeypress="javascript:{return disableCtrlKeyCombination(event);}"
        			onkeydown="javascript:{return disableCtrlKeyCombination(event);}"/>
			</t:panelGroup>
	
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

		<t:panelGroup
			rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}">
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL_ANNUAIRE']}"
				for="emailAnnuaire" />
		</t:panelGroup>
		<t:panelGroup
			rendered="#{individuController.pojoIndividu.individu.emailAnnuaire != null}">
			<e:text id="emailAnnuaire"
				value="#{individuController.pojoIndividu.individu.emailAnnuaire}" />
		</t:panelGroup>


		<t:panelGroup>
			<e:outputLabel value="#{msgs['INDIVIDU.NUM_NNE']}" for="numNNE" />
		</t:panelGroup>
		<t:panelGroup>
			<e:inputText id="numNNE" size="11" maxlength="10"
				value="#{individuController.pojoIndividu.individu.codeNNE}" />
			<e:inputText id="clefNNE" size="1" maxlength="1"
				value="#{individuController.pojoIndividu.individu.codeClefNNE}" />
		</t:panelGroup>

		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
				for="regimeInscription" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<t:panelGroup>
			<e:selectOneMenu id="regimeInscription"
				converter="#{regimeInscriptionConverter}"
				value="#{individuController.pojoIndividu.regimeInscription}">
				<f:selectItems value="#{nomenclatureController.regimeInscriptionsItemsConv}" />
			</e:selectOneMenu>
		</t:panelGroup>

	</e:panelGrid>
</t:div>

