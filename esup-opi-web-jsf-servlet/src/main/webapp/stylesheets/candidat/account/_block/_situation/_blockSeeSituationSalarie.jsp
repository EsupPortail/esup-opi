<%@include file="../../../../_include/_include.jsp"%>

<t:div id="blockFormSeeSituationSal"   
	rendered="#{situationController.actionEnum.whatAction 
					== situationController.actionEnum.readAction}"
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
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.TYPE_CONTRAT']}" for="typeContratSee" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeContratSee"
					onchange="javascript:{simulateLinkClick('addSituationForm:submitSelectType');}"
					value="#{situationController.indSituation.codeTypeContrat}" disabled="true">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="contrat" value="#{situationController.typeContrats}"
						itemLabel="#{contrat.label}" itemValue="#{contrat.code}" />
				</e:selectOneMenu>
				<e:outputLabel value="jusqu'au : (ddMMyyyy)" for="dateFinCDDSee" 
					style="margin-left:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"/>
				<t:outputText value="*" styleClass="etoileForChpObli" 
					style="margin-right:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"/>
				<e:text id="dateFinCDDSee" rendered="#{situationController.indSituation.codeTypeContrat == situationController.codCDD}"
					value="#{situationController.indSituation.dateFinCDD}">
					<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
				</e:text>
				<e:outputLabel value="précisez :" for="autreTypeContratSee"
					style="margin-left:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"/>
				<t:outputText value="*" styleClass="etoileForChpObli" 
					style="margin-right:4px;"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"/>
				<e:text id="autreTypeContratSee"
					rendered="#{situationController.indSituation.codeTypeContrat == situationController.codAutre}"
					value="#{situationController.indSituation.autreTypeContrat}"/>
			</t:panelGroup>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.TYPE_STATUT']}" for="typeStatutSee" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeStatutSee" disabled="true"
					value="#{situationController.indSituation.codeTypeStatut}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="statut" value="#{situationController.typeStatuts}"
						itemLabel="#{statut.label}" itemValue="#{statut.code}" />
				</e:selectOneMenu>
			</t:panelGroup>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.GRADE']}" for="gradeSee"/>
				<e:text value="#{msgs['SITUATION.SALARIE.GRADE.LEGEND']}" 
						styleClass="span-text-small-italic"
						style="text-align: left;margin-left:4px;"/>
			</t:panelGroup>
			<e:text id="gradeSee" value="#{situationController.indSituation.grade}"/>

		</e:panelGrid>
		<t:htmlTag value="hr"/>
		<e:panelGrid columns="2" width="100%"
			columnClasses="colUnQuart,colTroisQuart">
			<e:text value="#{msgs['SITUATION.SALARIE.EMPLOYEUR']}" styleClass="section-smallTitle"/>
			<t:panelGroup/>

			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.RAISON_SOC']}" for="raisonSocSee" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:text id="raisonSocSee" value="#{situationController.indSituation.raisonSociale}"/>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.TYPE_ORGA']}" for="typeOrgaSee" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="typeOrgaSee" disabled="true"
					value="#{situationController.indSituation.codeTypeOrga}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="orga" value="#{situationController.typeOrganismes}"
						itemLabel="#{orga.label}" itemValue="#{orga.code}" />
				</e:selectOneMenu>
			</t:panelGroup>
			<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.SECTEUR_ACT']}" for="secteurActSee" />
			<e:text id="secteurActSee" value="#{situationController.indSituation.secteurActivity}"/>
		</e:panelGrid>
		<e:panelGrid columns="4" width="100%"
			columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
	
			<e:outputLabel value="#{msgs['ADRESS.PAY']}" for="seePaysAdr" />
			<e:text id="seePaysAdr" value="#{adressController.emplAdrPojo.pays.libPay}" />
			<t:panelGroup />
			<t:panelGroup />
			<e:outputLabel value="#{msgs['SITUATION.SALARIE.EMPLOYEUR.ADRESSE']}" for="seeAdr1" />
			<e:text id="seeAdr1" value="#{adressController.emplAdrPojo.adresse.adr1}" />
			<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="seeAdr2" />
			<e:text id="seeAdr2" value="#{adressController.emplAdrPojo.adresse.adr2}" />
			<e:outputLabel value="#{msgs['ADRESS.COMP']}" for="seeAdr3" />
			<e:text id="seeAdr3" value="#{adressController.emplAdrPojo.adresse.adr3}" />
			
			<t:panelGroup />
			<t:panelGroup />
			<t:panelGroup
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}">
				<e:outputLabel value="#{msgs['ADRESS.COD_POST']}" for="seeCodePostal" />
			</t:panelGroup>
			<e:text id="seeCodePostal" 
					rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}"
					value="#{adressController.emplAdrPojo.adresse.codBdi}" />
			<e:outputLabel value="#{msgs['ADRESS.VIL']}" 
					rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}"
					for="seeVille" />
			<e:text id="seeVille" 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						== adressController.codeFrance}"
				value="#{adressController.emplAdrPojo.commune.libCommune}" />
			<e:outputLabel value="#{msgs['ADRESS.VIL.ETR']}" 
					rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}"
					for="seeForeignCity" />
			<e:text id="seeForeignCity" 
				rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}"
				value="#{adressController.emplAdrPojo.adresse.libComEtr}" />
			<t:panelGroup rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}"/>
			<t:panelGroup rendered="#{adressController.emplAdrPojo.adresse.codPays 
						!= adressController.codeFrance}"/>
			<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}" for="telephoneSee" />
			<e:text id="telephoneSee" value="#{adressController.emplAdrPojo.adresse.phoneNumber}" />
			<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}" for="emailSee" />
			<e:text id="emailSee" value="#{adressController.emplAdrPojo.adresse.mail}" />
	
	
		</e:panelGrid>
	</t:div>
</t:div>
