<%@include file="/stylesheets/_include/_include.jsp"%>
<t:div id="blockFormSearch" styleClass="blockForm"
	rendered="#{not empty managedAccess.domainAccueilGest
				&& opinionController.actionEnum.whatAction != 
				opinionController.actionEnum.propositionAction}">
	<h:form id="lookForIndForm" styleClass="opiR1_form">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.IND']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton id="lookForIndividu" value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{opinionController.searchStudents}" />
			</t:panelGroup>
		</e:panelGrid>
		<e:panelGrid styleClass="tableWidthMax" columns="4"
			columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['INDIVIDU.NUM_DOSSIER']}"
					for="numDossierOpi" />
			</t:panelGroup>
			<e:inputText id="numDossierOpi"
				value="#{paginatorIndividuPojo.indRechPojo.numDossierOpiRecherche}" />

			<t:panelGroup>
				<e:outputLabel value="#{msgs['INDIVIDU.NOM']}" for="nom" />
			</t:panelGroup>
			<e:inputText id="nom"
				value="#{paginatorIndividuPojo.indRechPojo.nomRecherche}" />
			<t:panelGroup>
				<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom" />
			</t:panelGroup>
			<e:inputText id="prenom"
				value="#{paginatorIndividuPojo.indRechPojo.prenomRecherche}" />

			<t:panelGroup>
				<e:outputLabel value="#{msgs['COMMISSION.CHOICE']}"
					for="lesCommissions" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
					onchange="javascript:{simulateLinkClick('lookForIndForm:lookForIndividuComm');}"
					value="#{paginatorIndividuPojo.indRechPojo.idCmi}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="commission"
						value="#{commissionController.commissionsItemsByRight}"
						itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
				</e:selectOneMenu>
				<e:commandButton id="lookForIndividuComm" value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{opinionController.searchStudentsByComm}" />
			</t:panelGroup>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['AVIS.LABEL_DECISION']}" for="lesTypesDecision"/>
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="lesTypesDecision" converter="#{typeDecisionConverter}"
					onchange="javascript:{simulateLinkClick('lookForIndForm:lookForIndividuTypeDec');}"
					value="#{paginatorIndividuPojo.indRechPojo.typeDecRecherchee}">
					<f:selectItems value="#{nomenclatureController.typeDecisionItems}" />
				</e:selectOneMenu>
				<e:commandButton id="lookForIndividuTypeDec" value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{opinionController.searchStudentsByTypeDec}" />
			</t:panelGroup>
			<t:panelGroup rendered="#{opinionController.isFilterLCAndCommissionOK}">
				<e:outputLabel value="#{msgs['FIELD_LABEL.ETAPES']}" for="lesEtapes"/>
			</t:panelGroup>
			<t:panelGroup rendered="#{opinionController.isFilterLCAndCommissionOK}">
				<e:selectOneMenu id="lesEtapes" converter="javax.faces.Integer"
					onchange="javascript:{simulateLinkClick('lookForIndForm:lookForIndividuEtp');}"
					value="#{paginatorIndividuPojo.indRechPojo.codeTrtCmiRecherchee}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="beanTrtCmi"
						value="#{trtCmiController.allTraitementCmi}"
						itemLabel="#{beanTrtCmi.etape.licEtp}" itemValue="#{beanTrtCmi.traitementCmi.id}" />
				</e:selectOneMenu>
				<e:commandButton id="lookForIndividuEtp" value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{opinionController.searchStudentsByEtp}" />
			</t:panelGroup>
			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
			</t:panelGroup>
			<e:selectManyCheckbox value="#{paginatorIndividuPojo.listeRI}" converter="javax.faces.Integer"
				disabled="#{!paginatorIndividuPojo.indRechPojo.canModifyRISearch}"
				layout="lineDirection">
				<t:selectItems var="ri" 
						itemLabel="#{ri.shortLabel}" itemValue="#{ri.code}"
						value="#{nomenclatureController.allRegimeInscription}"/>
			</e:selectManyCheckbox>
			
		</e:panelGrid>
	</h:form>
</t:div>
<script type="text/javascript">
	hideElement('lookForIndForm:lookForIndividuComm');
	hideElement('lookForIndForm:lookForIndividuTypeDec')
	hideElement('lookForIndForm:lookForIndividuEtp');
</script>
