<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['COMMISSION.TITLE.SEE']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionCmiForm" styleClass="div_subSection">
			<h:form id="seeCommissionForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSIONS']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['COMMISSION.EDIT_LISTE_PREPA']}"
									rendered="#{managedAccess.updateAuthorized}" immediate="true">
									<t:updateActionListener
										value="#{commissionController.actionEnum.listePrepa}"
										property="#{commissionController.actionEnum.whatAction}" />
								</e:commandButton>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{commissionController.goSeeAllCmi}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:div style="width:100%;"
						rendered="#{commissionController.actionEnum.whatAction == commissionController.actionEnum.listePrepa}">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text />
							<t:panelGroup>
								<e:commandButton value="#{msgs['COMMISSION.EDIT_LISTE_PREPA_ALPHA']}"
									rendered="#{managedAccess.updateAuthorized}"
									action="#{commissionController.makePDFListesPreparatoireAlpha}"/>
								<e:commandButton value="#{msgs['COMMISSION.EDIT_LISTE_PREPA_ETAPE']}"
									rendered="#{managedAccess.updateAuthorized}"
									action="#{commissionController.makePDFListesPreparatoireEtape}"/>
								<e:commandButton value="#{msgs['COMMISSION.EDIT_LISTE_PREPA_TITRE']}"
									rendered="#{managedAccess.updateAuthorized}"
									action="#{commissionController.makePDFListesPreparatoireTitre}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
						<e:text id="code" value="#{commissionController.commission.code}"/>
						<t:panelGroup/>
						<t:panelGroup style="text-align:right;">
							<e:commandButton image="/media/images/update.png"
							action="#{commissionController.goUpdateCmi}" styleClass="form-button-image"
							title="#{msgs['BUTTON.UPDATE']}" rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.updateAction}"
									property="#{commissionController.actionEnum.whatAction}" />
							</e:commandButton>
							<e:commandButton
								image="/media/images/calendar.png"
								styleClass="form-button-image" title="#{msgs['CALENDAR.TITLE.CONSULT']}" 
								action="#{calendarController.goSeeCalCmi}"/>
							<e:commandButton
								image="/media/images/page_white_acrobat.png"
								styleClass="form-button-image" title="#{msgs['COMMISSION.EDIT.ARRETE_NOM']}" 
								rendered="#{managedAccess.updateAuthorized}" immediate="true"
								action="#{commissionController.makePDFNomination}"/>
							<e:commandButton 
								image="/media/images/email.png"
								styleClass="form-button-image" title="#{msgs['COMMISSION.SEND_CONVOC_MAIL']}"
								rendered="#{managedAccess.updateAuthorized}" immediate="true"
								action="#{commissionController.sendMailConvocation}"/>
						</t:panelGroup>
						
												
						<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
								for="temoinEnService" />
						<t:panelGroup>
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{commissionController.commission.temoinEnService}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!commissionController.commission.temoinEnService}" />
						</t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
								for="libelle" />
						<e:text id="libelle" value="#{commissionController.commission.libelle}"/>
						
					</e:panelGrid>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				
				<t:div id="blockFormContact" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col1UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSION.CONTACT']}"
								styleClass="section-smallTitle" />
							<t:panelGroup/>
						</e:panelGrid>
					</t:div>
					<t:div styleClass="tableWidthMax">
						<e:panelGrid styleClass="tableJustWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}"
									for="mail" />
							<e:text id="mail" value="#{commissionController.contactCommission.adresse.mail}" />
							<e:outputLabel value="#{msgs['COMMISSION.CORRESPONDING']}"
									for="corresponding" />
							<e:text id="corresponding" value="#{commissionController.contactCommission.corresponding}"/>
							<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}"
									for="phoneNumber" />
							<e:text id="phoneNumber" value="#{commissionController.contactCommission.adresse.phoneNumber}"/>	
							<e:outputLabel value="#{msgs['ADRESS.TEL_FAX']}"
									for="faxNumber" />
							<e:text id="faxNumber" value="#{commissionController.contactCommission.adresse.faxNumber}" />	
							<e:outputLabel value="#{msgs['COMMISSION.SIGNATAIRE']}"
									for="codSig" />
							<e:text id="codSig" value="#{commissionController.contactCommission.codSig}" />		
						</e:panelGrid>
						<%@include file="_commission/_blockseeAddressCmi.jsp"%>
						<t:div id="blockFormManager" styleClass="blockForm" rendered="#{commissionController.managerUsed}">
							<t:div style="width:100%;">
								<e:panelGrid columns="2" columnClasses="col1UnDemi,col1UnDemi"
									styleClass="tableJustWidthMax">
									<e:text value="#{msgs['COMMISSION.MANAGER']}"
										styleClass="section-smallTitle" />
									<t:panelGroup />
								</e:panelGrid>
							</t:div>
							<e:panelGrid styleClass="tableWidthMax" columns="4"
								columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSION.MANAGER.NAME']}"
										for="manager" />
								</t:panelGroup>
								<e:text id="manager"
									value="#{commissionController.contactCommission.managerName}" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSION.MANAGER.PHONE']}"
										for="phoneManager" />
								</t:panelGroup>
								<e:text id="phoneManager"
									value="#{commissionController.contactCommission.managerPhone}" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSION.MANAGER.MAIL']}"
										for="mailManager" />
								</t:panelGroup>
								<e:text id="mailManager"
									value="#{commissionController.contactCommission.managerMail}" />
							</e:panelGrid>
						</t:div>
					</t:div>
				</t:div>
				
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				
				<t:div id="blockFormSeeEtape" styleClass="blockForm">
					<%@include file="_commission/_seeTypeTrt.jsp"%>
				</t:div>
				

				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormMembre" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['MEMBERS']}"
									styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['COMMISSION.EDIT.ARRETE_NOM']}"
									rendered="#{managedAccess.updateAuthorized}" immediate="true"
									action="#{commissionController.makePDFNomination}"/>
								<e:commandButton value="#{msgs['COMMISSION.SEND_CONVOC_MAIL']}"
									rendered="#{managedAccess.updateAuthorized}" immediate="true"
									action="#{commissionController.sendMailConvocation}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="member"
						value="#{commissionController.keySetMbrToDisplay}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.NAME']}" />
							</f:facet>
							<e:text value="#{member.nom}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
							</f:facet>
							<e:text value="#{member.prenom}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.MAIL']}" />
							</f:facet>
							<e:text value="#{member.adressMail}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.TYPE']}" />
							</f:facet>
							<e:text value="#{member.type}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:popup styleClass="containerPopup" style="width:200px;"
									closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
									 displayAtDistanceX="-200" displayAtDistanceY="-100">
									<t:graphicImage url="/media/images/email.png"
										style="cursor:pointer;"   />
									<f:facet name="popup">
										<h:panelGroup>
											<e:text value="#{msgs['COMMISSION.SELECT_MEMBERS']}"/>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</f:facet>
							<jdt:multipleRowsSelector
								selectionList="#{commissionController.membersSelected}" />
						
						</t:column>
					</e:dataTable>
				</t:div>
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
</script>
</e:page>
