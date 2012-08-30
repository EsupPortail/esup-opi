<%@include file="/stylesheets/_include/_include.jsp"%>
<t:div id="blockFormSearch" styleClass="blockForm"
	rendered="#{not empty managedAccess.domainAccueilGest}">
	<h:form id="lookForIndForm" styleClass="opiR1_form">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.IND']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton id="cancelLookForIndividu"
					value="#{msgs['_.BUTTON.CANCEL']}"
					action="#{consultRdvController.searchStudents}">
					<t:updateActionListener
						value="#{false}"
						property="#{consultRdvController.inSearch}" />
				</e:commandButton>
				<e:commandButton id="lookForIndividu"
					value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{consultRdvController.searchStudents}">
					<t:updateActionListener
						value="#{true}"
						property="#{consultRdvController.inSearch}" />
				</e:commandButton>
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
				<e:outputLabel value="#{msgs['COMMISSION.CHOICE']}"
					for="lesCommissions" />
			</t:panelGroup>
			<t:panelGroup>
				<e:selectOneMenu id="lesCommissions" converter="javax.faces.Integer"
					value="#{paginatorIndividuPojo.indRechPojo.idCmi}">
					<f:selectItem itemLabel="" itemValue="" />
					<t:selectItems var="commission"
						value="#{commissionController.commissionsItems}"
						itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
				</e:selectOneMenu>
			</t:panelGroup>

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
				<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" for="fc" />
			</t:panelGroup>
			<e:selectManyCheckbox value="#{paginatorIndividuPojo.listeRI}" converter="javax.faces.Integer"
				disabled="#{!paginatorIndividuPojo.indRechPojo.canModifyRISearch}"
				layout="lineDirection">
				<t:selectItems var="ri" 
						itemLabel="#{ri.shortLabel}" itemValue="#{ri.code}"
						value="#{nomenclatureController.allRegimeInscription}"/>
			</e:selectManyCheckbox>
			<t:panelGroup />
			<t:panelGroup />
		</e:panelGrid>

	</h:form>
</t:div>
<script type="text/javascript">
	hideElement('lookForIndForm:lookForIndividuComm');
	hideElement('lookForIndForm:lookForIndividuTypeDec')
	hideElement('lookForIndForm:lookForIndividuEtp');
</script>
