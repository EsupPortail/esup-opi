<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{managedAccess.readAuthorized}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<script type="text/javascript">
			var userSelected = false;
			function selectUser(linkId) {
				if (!userSelected) {
			  		userSelected = true;
					simulateLinkClick(linkId);
			  	}
			}
		</script>

		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		
		<%@include file="_block/_sendMailStudent.jsp"%>
		
		<%@include file="_block/_enterPM.jsp"%>
		
		<t:div rendered="#{!(pjController.currentCmiPojo != null 
			&& pjController.currentCmiPojo.state.displayPJ)}"> 
			<%@ include file="/stylesheets/gestionnaire/opinion/_block/_lookForIndividuPM.jsp"%>
		</t:div>
				
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />


		<h:form id="seeIndividusForm">

			<t:div style="width:100%;">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['PM.TITLE.SEE']}"
						styleClass="section-smallTitle" />
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
								action="#{pjController.changeStateAll}"
								rendered="#{(managedAccess.updateAuthorized
									|| managedAccess.addAuthorized) && !(pjController.currentCmiPojo != null 
									&& pjController.currentCmiPojo.state.displayPJ)}">
						</e:commandButton>
						<e:commandButton id="changeAllState" value="#{msgs['_.BUTTON.CHANGE']}"
							action="#{pjController.putStateAll}" />
						<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
							action="#{managedAccess.goDisplayFunction}" 
							rendered="#{!(pjController.currentCmiPojo != null 
						&& pjController.currentCmiPojo.state.displayPJ)}"/>
					</t:panelGroup>
				</e:panelGrid>
			</t:div>


			<t:div style="width:100%;">
			
				<e:dataTable var="missingPiecePojos" id="individuData"
					value="#{paginatorPM.missingPiecePojos}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false" rowIndexVar="variable">
					<f:facet name="header">
						<h:panelGroup>
							<e:paginator id="paginatorIndividu"
								paginator="#{paginatorPM}"
								itemsName="#{msgs['INDIVIDUS']}"
								onchange="javascript:{simulateLinkClick('seeIndividusForm:individuData:submitPageSize');}" />
							<e:commandButton id="submitPageSize"
								action="#{paginatorPM.forceReload}" />
						</h:panelGroup>
					</f:facet>
					<t:column onclick="selectUser('seeIndividusForm:individuData:#{variable}:updateState')">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
						</f:facet>
						<e:text value="#{missingPiecePojos.individuPojo.individu.numDossierOpi}" />
					</t:column>
					<t:column onclick="selectUser('seeIndividusForm:individuData:#{variable}:updateState')">
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.NAME']}" />
						</f:facet>
						<e:text value="#{missingPiecePojos.individuPojo.individu.nomPatronymique} #{missingPiecePojos.individuPojo.individu.prenom}" />
					</t:column>
					<t:column onclick="selectUser('seeIndividusForm:individuData:#{variable}:updateState')">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.DATE_NAI_COURT']}" />
						</f:facet>
						<e:text value="#{missingPiecePojos.individuPojo.individu.dateNaissance}">
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.MAIL_PERSO']}" />
						</f:facet>
						<h:outputLink
							value="mailto:#{missingPiecePojos.individuPojo.individu.adressMail}">
							<h:outputText
								value="#{missingPiecePojos.individuPojo.individu.adressMail}" />
						</h:outputLink>
					</t:column>
					<t:column>
						<e:commandButton image="/media/images/update.png"
							styleClass="form-button-image" id="updateState" immediate="true"
							title="#{msgs['_.BUTTON.UPDATE']}"
							rendered="#{managedAccess.updateAuthorized && !(pjController.currentCmiPojo != null 
									&& pjController.currentCmiPojo.state.displayPJ)}"
							action="#{pjController.goSeePMEtudiant}">
							<t:updateActionListener value="#{missingPiecePojos}"
									property="#{pjController.mpPojoSelected}" />
						</e:commandButton>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['COMMISSIONS']}" />
						</f:facet>
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}" >
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
 								<t:popup styleClass="containerPopup" style="width:350px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="-50"
									displayAtDistanceY="0">
 									<e:text value="#{commissionPojo.commission.code}" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.COMMISSION']}" />
												<t:outputText styleClass="libellesAide"
													value="#{commissionPojo.commission.libelle}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:panelGroup>
								<e:text value="#{msgs['STATE.NON_ARRIVE']}" />
								<opi:radioButton id="radioHeaderId1" name="myRadioHeader"
									overrideName="true" value="#{pjController.stateSelected}"
									onClick="simulateLinkClick('seeIndividusForm:changeAllState')"
									itemValue="STATE.NON_ARRIVE" />
							</t:panelGroup>
							
						</f:facet>
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}" 
 							 rowIndexVar="rowVar">
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
								<opi:radioButton id="myRadioId1" name="myRadioCol#{variable}#{rowVar}"
									overrideName="true" value="#{commissionPojo.stateCurrent}"
									itemValue="STATE.NON_ARRIVE" />
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:panelGroup>
								<e:text value="#{msgs['STATE.ARRIVE_COMPLET']}" />
								<opi:radioButton id="radioHeaderId2" name="myRadioHeader"
									overrideName="true" value="#{pjController.stateSelected}"
									onClick="simulateLinkClick('seeIndividusForm:changeAllState')"
									itemValue="STATE.ARRIVE_COMPLET" />
							</t:panelGroup>
						</f:facet>
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}" 
 							 rowIndexVar="rowVar">
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
								<opi:radioButton id="myRadioId2" name="myRadioCol#{variable}#{rowVar}"
									overrideName="true" value="#{commissionPojo.stateCurrent}"
									itemValue="STATE.ARRIVE_COMPLET" />
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:panelGroup>
								<e:text value="#{msgs['STATE.ARRIVE_INCOMPLET']}" />
								<opi:radioButton id="radioHeaderId3" name="myRadioHeader"
									overrideName="true" value="#{pjController.stateSelected}"
									onClick="simulateLinkClick('seeIndividusForm:changeAllState')"
									itemValue="STATE.ARRIVE_INCOMPLET" />
							</t:panelGroup>
						</f:facet>
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}" 
 							 rowIndexVar="rowVar">
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
								<opi:radioButton id="myRadioId3" name="myRadioCol#{variable}#{rowVar}"
									overrideName="true" value="#{commissionPojo.stateCurrent}"
									itemValue="STATE.ARRIVE_INCOMPLET" />
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column styleClass="buttonTD">
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}">
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
								<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
									action="#{pjController.changeState}"
									rendered="#{(managedAccess.updateAuthorized
										|| managedAccess.addAuthorized) && !(pjController.currentCmiPojo != null 
										&& pjController.currentCmiPojo.state.displayPJ)}">
									<t:updateActionListener value="#{commissionPojo}"
										property="#{pjController.currentCmiPojo}" />
									<t:updateActionListener value="#{missingPiecePojos}"
										property="#{pjController.mpPojoSelected}" />
								</e:commandButton>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column styleClass="buttonTD">
						<t:dataList
							 var="commissionPojo" id="titrecom"
 							 value="#{missingPiecePojos.cmiKeySet}">
 							 <e:ul style="margin:0px;padding:0px;height:20px;">
								<e:commandButton image="/media/images/magnifier.png"
									styleClass="form-button-image" immediate="true"
									action="#{pjController.seeMissingPiecePrincipal}"
									title="#{msgs['_.BUTTON.DISPLAY']}"
									rendered="#{!(pjController.currentCmiPojo != null 
									&& pjController.currentCmiPojo.state.displayPJ)}">
									<t:updateActionListener value="#{commissionPojo}"
										property="#{pjController.currentCmiPojo}" />
									<t:updateActionListener value="#{missingPiecePojos}"
										property="#{pjController.mpPojoSelected}" />
								</e:commandButton>
							</e:ul>
						</t:dataList>
					</t:column>
				</e:dataTable>
			</t:div>
		</h:form>


		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />

	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	hideElement('seeIndividusForm:individuData:submitPageSize');
	hideElement('seeIndividusForm:individuData:submitSelectTypeTrt');
	hideElement('seeIndividusForm:changeAllState');
	highlightTableRows('seeIndividusForm:individuData');
</script>
</e:page>
