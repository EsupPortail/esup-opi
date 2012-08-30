<%@include file="../../../../_include/_include.jsp"%>

<t:div id="blockFormSituationSal" 
	rendered="#{situationController.actionEnum.whatAction 
					!= situationController.actionEnum.readAction}"
	styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<t:panelGroup>
				<e:text value="#{msgs['SITUATION.SALARIE.TITLE']}"
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
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.TYPE_CONTRAT']}" for="typeContrat" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeContrat"
					onchange="javascript:{simulateLinkClick('addSituationForm:submitSelectType');}"
					value="#{situationController.indSituation.codeTypeContrat}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="contrat" value="#{situationController.typeContrats}"
						itemLabel="#{contrat.label}" itemValue="#{contrat.code}" />
				</e:selectOneMenu>
				<e:commandButton id="submitSelectType"/>
				<e:outputLabel value="jusqu'au : (ddMMyyyy)" for="dateFinCDD" 
					style="margin-left:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"/>
				<t:outputText value="*" styleClass="etoileForChpObli" 
					style="margin-right:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"/>
				<e:inputText id="dateFinCDD" size="10" maxlength="8"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"
					value="#{situationController.indSituation.dateFinCDD}">
					<f:convertDateTime pattern="ddMMyyyy"
						timeZone="#{sessionController.timezone}" />
					<f:validateLength minimum="8"/>
				</e:inputText>
				<e:outputLabel value="précisez :" for="autreTypeContrat"
					style="margin-left:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"/>
				<t:outputText value="*" styleClass="etoileForChpObli" 
					style="margin-right:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"/>
				<e:inputText id="autreTypeContrat" size="60" maxlength="50" 
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"
					value="#{situationController.indSituation.autreTypeContrat}"/>
			</t:panelGroup>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.TYPE_STATUT']}" for="typeStatut" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeStatut"
					value="#{situationController.indSituation.codeTypeStatut}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="statut" value="#{situationController.typeStatuts}"
						itemLabel="#{statut.label}" itemValue="#{statut.code}" />
				</e:selectOneMenu>
			</t:panelGroup>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.GRADE']}" for="grade"/>
				<e:text value="#{msgs['SITUATION.SALARIE.GRADE.LEGEND']}" 
						styleClass="span-text-small-italic"
						style="text-align: left;margin-left:4px;"/>
			</t:panelGroup>
			<e:inputText id="grade" size="60" maxlength="50" 
				value="#{situationController.indSituation.grade}"/>

		</e:panelGrid>
		<t:htmlTag value="hr"/>
		<e:panelGrid columns="2" width="100%"
			columnClasses="colUnQuart,colTroisQuart">
			<e:text value="#{msgs['SITUATION.SALARIE.EMPLOYEUR']}" styleClass="section-smallTitle"/>
			<t:panelGroup/>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.RAISON_SOC']}" for="raisonSoc" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="raisonSoc" size="60" maxlength="50" 
				value="#{situationController.indSituation.raisonSociale}"/>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.TYPE_ORGA']}" for="typeOrga" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeOrga"
					value="#{situationController.indSituation.codeTypeOrga}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="orga" value="#{situationController.typeOrganismes}"
						itemLabel="#{orga.label}" itemValue="#{orga.code}" />
				</e:selectOneMenu>
			</t:panelGroup>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.SECTEUR_ACT']}" for="secteurAct" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="secteurAct" size="60" maxlength="50" 
				value="#{situationController.indSituation.secteurActivity}"/>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="paysAdr" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="paysAdr" 
					value="#{adressController.emplAdrPojo.adresse.codPays}"
					onchange="javascript:{simulateLinkClick('addSituationForm:submitSelectPayAdr');}"
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
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.ADRESSE']}" for="adr1" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="adr1" value="#{adressController.emplAdrPojo.adresse.adr1}" size="32" maxlength="32" />
			<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr2" />
			<e:inputText id="adr2" value="#{adressController.emplAdrPojo.adresse.adr2}" size="40" maxlength="70" />
			<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="adr3" />
			<e:inputText id="adr3" value="#{adressController.emplAdrPojo.adresse.adr3}" size="40" maxlength="70" />
			<t:panelGroup 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}">
				<e:outputLabel value="#{msgs['ADRESS.COD_POST']}"
					for="codePostalFix" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}">
				<e:inputText id="codePostalEmpl" value="#{adressController.emplAdrPojo.adresse.codBdi}" 
					size="6" maxlength="5" />
				<e:commandButton id="submitSelectCpEmpl" action="#{adressController.selectCpEmpl}"/>
			</t:panelGroup>
			
			<t:panelGroup 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}">
				<e:outputLabel value="#{msgs['ADRESS.VIL']}" for="ville" />
				<t:outputText value="*" styleClass="etoileForChpObli"/>
			</t:panelGroup>
			<e:selectOneMenu id="ville" 
					rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}"
					value="#{adressController.emplAdrPojo.adresse.codCommune}">
				<f:selectItem itemLabel="" itemValue="" />
				<t:selectItems var="ville" value="#{adressController.communesEmpl}"
					itemLabel="#{ville.libCommune}" itemValue="#{ville.codeCommune}" />
			</e:selectOneMenu>
			<t:panelGroup 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}">
				<e:outputLabel value="#{msgs['ADRESS.VIL.ETR']}" for="villeEtr" />
				<t:outputText value="*" styleClass="etoileForChpObli"/>
			</t:panelGroup>
			<e:inputText id="villeEtr" 
					rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}"
					value="#{adressController.emplAdrPojo.adresse.libComEtr}">
			</e:inputText>
	
			<t:panelGroup>
				<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}" for="telephone" />
			</t:panelGroup>
			<e:inputText id="telephone" size="11" maxlength="15"
				value="#{adressController.emplAdrPojo.adresse.phoneNumber}" />
	
			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}" for="email" />
			</t:panelGroup>
			<e:inputText id="email"
				value="#{adressController.emplAdrPojo.adresse.mail}"
				size="35" maxlength="50" />
	
	
		</e:panelGrid>
	</t:div>
</t:div>
