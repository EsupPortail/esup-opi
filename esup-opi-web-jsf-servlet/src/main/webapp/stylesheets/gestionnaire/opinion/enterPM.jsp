<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>

		<%@include file="_block/_sendMailStudent.jsp"%>

		
		<t:div id="blockInfoCoord" styleClass="blockForm">
			<t:div style="width:100%;">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['ETUDIANT']}" styleClass="section-smallTitle" />
					<e:form id="backSeeStudentForm" styleClass="opiR1_form">
						<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
							action="#{pjController.goBackMPStudent}" />
					</e:form>
				</e:panelGrid>
			</t:div>
			<t:panelGrid columns="5" styleClass="displayInfo"
				rowClasses="headerRow, ">
				<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
				<t:outputText value="#{msgs['INDIVIDU.NOM']}" />
				<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
				<t:outputText value="#{msgs['INDIVIDU.NAISSANCE']}" />
				<t:outputText value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" />

				<e:text
					value="#{pjController.mpPojoSelected.individuPojo.individu.numDossierOpi}" />
				<e:text
					value="#{pjController.mpPojoSelected.individuPojo.individu.nomPatronymique}" />
				<e:text value="#{pjController.mpPojoSelected.individuPojo.individu.prenom}" />
				<e:text
					value="#{pjController.mpPojoSelected.individuPojo.individu.dateNaissance}">
					<f:convertDateTime pattern="dd/MM/yyyy"
						timeZone="#{sessionController.timezone}" />
				</e:text>
				<h:outputLink
					value="mailto:#{pjController.mpPojoSelected.individuPojo.individu.adressMail}">
					<h:outputText
						value="#{pjController.mpPojoSelected.individuPojo.individu.adressMail}" />
				</h:outputLink>
			</t:panelGrid>


		</t:div>
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

		<h:form id="detailPMform" styleClass="opiR1_form">
			<t:div style="width:100%;">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['COMMISSIONS']}"
						styleClass="section-smallTitle" />
				</e:panelGrid>
			</t:div>

			<e:dataTable var="commissionPojo" value="#{pjController.mpPojoSelected.cmiKeySet}"
				alternateColors="false" renderedIfEmpty="false"
				styleClass="displayInfo" rowIndexVar="rowVar">
				<t:column>
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
					</f:facet>
					<e:text value="#{commissionPojo.commission.code}" />
				</t:column>
				<t:column>
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
					</f:facet>
					<e:text value="#{commissionPojo.commission.libelle}" />
				</t:column>
				<t:column>
					<f:facet name="header">
						<e:text value="#{msgs['STATE.NON_ARRIVE']}" />
					</f:facet>
					<opi:radioButton id="myRadioId1" name="myRadioCol#{rowVar}"
						overrideName="true" value="#{commissionPojo.stateCurrent}"
						itemValue="STATE.NON_ARRIVE" />
				</t:column>
				<t:column>
					<f:facet name="header">
						<e:text value="#{msgs['STATE.ARRIVE_COMPLET']}" />
					</f:facet>
					<opi:radioButton id="myRadioId2" name="myRadioCol#{rowVar}"
						overrideName="true" value="#{commissionPojo.stateCurrent}"
						itemValue="STATE.ARRIVE_COMPLET" />
				</t:column>
				<t:column>
					<f:facet name="header">
						<e:text value="#{msgs['STATE.ARRIVE_INCOMPLET']}" />
					</f:facet>
					<opi:radioButton id="myRadioId3" name="myRadioCol#{rowVar}"
						overrideName="true" value="#{commissionPojo.stateCurrent}"
						itemValue="STATE.ARRIVE_INCOMPLET" />
				</t:column>
				<t:column styleClass="buttonTD">
					<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
						action="#{pjController.changeState}"
						rendered="#{managedAccess.updateAuthorized
								|| managedAccess.addAuthorized}">
						<t:updateActionListener value="#{commissionPojo}"
							property="#{pjController.currentCmiPojo}" />
					</e:commandButton>
				</t:column>
				<t:column styleClass="buttonTD">
					<e:commandButton image="/media/images/magnifier.png"
						styleClass="form-button-image" immediate="true"
						action="#{pjController.seeMissingPiecePrincipal}"
						title="#{msgs['_.BUTTON.DISPLAY']}">
						<t:updateActionListener value="#{commissionPojo}"
							property="#{pjController.currentCmiPojo}" />
					</e:commandButton>
				</t:column>
			</e:dataTable>
		</h:form>
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

	<%@include file="_block/_enterPM.jsp"%>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
</script>
</e:page>
