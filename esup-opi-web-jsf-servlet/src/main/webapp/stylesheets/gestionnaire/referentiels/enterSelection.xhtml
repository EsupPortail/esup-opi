<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionFirstForm" styleClass="div_subSection">
			<h:form id="enterSelectionForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="div_subSection" >
					<e:paragraph value="Recherche des VET dans les commissions" />
					<t:div id="blockFormInfo" styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
									action="#{selectionController.lookForBeanTrtCmi}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{selectionController.goBackFunction}" />
							</t:panelGroup>
						</t:div>

						<e:panelGrid styleClass="tableWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<e:outputLabel value="#{msgs['FIELD_LABEL.COD_ETP']}"
								for="codEtp" />
							<e:inputText id="codEtp" size="10" maxlength="10"
								value="#{etapeController.codEtp}" />
							<e:outputLabel
								value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}"
								for="libWebVet" />
							<e:inputText id="libWebVet" size="40" maxlength="60"
								value="#{etapeController.libWebVet}" />
							<e:outputLabel value="#{msgs['COMMISSIONS']}" for="commissions" />
							<e:selectOneMenu id="lesCommissions"
								converter="javax.faces.Integer"
								value="#{trtCmiController.idCmi}">
								<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{commissionController.commissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
							</e:selectOneMenu>
						</e:panelGrid>

					</t:div>
				</t:div>


				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />

				<t:div id="blockFormSeeEtape" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.SAVE']}"
								action="#{selectionController.saveUpdate}" />
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								immediate="true" action="#{selectionController.reset}" />
						</t:panelGroup>
					</t:div>
					<e:dataTable id="trtCmi" var="beanTrtCmi"
						value="#{selectionController.beansTrt}" rowIndexVar="rowVar"
						styleClass="displayInfo" alternateColors="false">
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['COMMISSIONS']}" />
							</f:facet>
							<e:text value="#{beanTrtCmi.traitementCmi.commission.libelle}" />
						</t:column>
						<t:column width="35%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
							</f:facet>
							<e:text value="#{beanTrtCmi.etape.libWebVet}" />

						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['COMMISSION.TRT_CMI.DAT_RES_ADMI']}" />
							</f:facet>
							<e:inputText id="dateDbtres" size="20" maxlength="60"
								value="#{selectionController.selections[beanTrtCmi].resultSelection}" />
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['COMMISSION.TRT_CMI.DAT_ADMI']}" />
							</f:facet>
							<e:inputText id="periodeAdmissibilite" size="20" maxlength="60"
								value="#{selectionController.selections[beanTrtCmi].periodeAdmissibilite}" />
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['REUNION_CMI.PLACE']}" />
							</f:facet>
							<e:inputText id="place" size="25" maxlength="80"
								value="#{selectionController.selections[beanTrtCmi].place}" />
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
							</f:facet>
							<e:inputText id="comment" size="30" maxlength="500"
								value="#{selectionController.selections[beanTrtCmi].comment}" />
						</t:column>
					</e:dataTable>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
			</h:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	highlightInputAndSelect('enterSelectionForm');
</script>
</e:page>
