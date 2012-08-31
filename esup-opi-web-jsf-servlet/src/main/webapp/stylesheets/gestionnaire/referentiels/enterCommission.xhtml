<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['COMMISSION.TITLE.ADD']}"
			rendered="#{commissionController.actionEnum.whatAction 
						== commissionController.actionEnum.addAction}" />
		<e:section value="#{msgs['COMMISSION.TITLE.UPDATE']}"
			rendered="#{commissionController.actionEnum.whatAction 
						== commissionController.actionEnum.updateAction}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionCmiForm" styleClass="div_subSection">
			<h:form id="enterCommissionForm">
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
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{commissionController.add}"
									rendered="#{commissionController.actionEnum.whatAction 
										== commissionController.actionEnum.addAction
										&& managedAccess.addAuthorized}" />
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{commissionController.update}"
									rendered="#{commissionController.actionEnum.whatAction 
										== commissionController.actionEnum.updateAction
										&& managedAccess.updateAuthorized}" />
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									immediate="true" action="#{commissionController.goSeeAllCmi}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="code"
							value="#{commissionController.commission.code}" size="11"
							maxlength="10" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
								for="temoinEnService" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:selectBooleanCheckbox id="temoinEnService"
							value="#{commissionController.commission.temoinEnService}" />

						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
								for="libelle" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<e:inputText id="libelle"
							value="#{commissionController.commission.libelle}" size="40"
							maxlength="60" />
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
							<t:panelGroup rendered="#{commissionController.contactCommission.adresse == null}">
								<e:outputLabel value="#{msgs['COMMISSION.SELECT_ADRESS']}"
									for="selectAdress" />
							</t:panelGroup>		
							<t:panelGroup rendered="#{commissionController.contactCommission.adresse == null}">
								<e:selectOneMenu id="selectAdress"
									onchange="javascript:{simulateLinkClick('enterCommissionForm:selectCommAdress');}"
									converter="javax.faces.Integer"
									value="#{commissionController.idCmiForAdress}">
									<f:selectItem itemLabel="" itemValue="" />
									<t:selectItems var="commission"
										value="#{commissionController.commissionsForAdresses}"
										itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
								</e:selectOneMenu>
								<t:commandButton id="selectCommAdress"
										action="#{commissionController.selectCommAdress}"
										onclick="javascript:{clickAnnuler = true;}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:div styleClass="tableWidthMax">
						<e:panelGrid styleClass="tableJustWidthMax" columns="4"
							columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
							<t:panelGroup>
								<e:outputLabel value="#{msgs['FIELD_LABEL.MAIL']}" for="mail" />
							</t:panelGroup>
	
							<e:inputText id="mail" value="#{adressController.fixAdrPojo.adresse.mail}" 
										size="35" maxlength="50" />
							<t:panelGroup>
								<e:outputLabel value="#{msgs['COMMISSION.CORRESPONDING']}"
									for="corresponding" />
							</t:panelGroup>
							<e:inputText id="corresponding"
								value="#{commissionController.contactCommission.corresponding}"
								size="35" />
							<t:panelGroup>
								<e:outputLabel value="#{msgs['ADRESS.TEL_FIX']}"
									for="phoneNumber" />
								<t:outputText value="*" styleClass="etoileForChpObli" />
							</t:panelGroup>
	
							<e:inputText id="phoneNumber" value="#{adressController.fixAdrPojo.adresse.phoneNumber}" 
										size="12" maxlength="10" />	
										
							<t:panelGroup>
								<e:outputLabel value="#{msgs['ADRESS.TEL_FAX']}"
									for="faxNumber" />
							</t:panelGroup>
							<e:inputText id="faxNumber" value="#{adressController.fixAdrPojo.adresse.faxNumber}" 
										size="12" maxlength="10" />		
							<t:panelGroup>
								<e:outputLabel value="#{msgs['COMMISSION.SIGNATAIRE']}"
									for="codSig" />
							</t:panelGroup>		
							<e:selectOneMenu id="codSig"
									value="#{commissionController.contactCommission.codSig}">
									<f:selectItem itemLabel=""
									itemValue="" />
									<t:selectItems var="sign" value="#{commissionController.signataireInUse}"
										itemLabel="#{sign.codSig} - #{sign.nomSig}" itemValue="#{sign.codSig}"/>
							</e:selectOneMenu>
						</e:panelGrid>
						<%@include file="_commission/_blockAddressCmi.jsp"%>
						<t:div id="blockFormManager" styleClass="blockForm" rendered="#{commissionController.managerUsed}" >
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
								<e:inputText id="manager"
									value="#{commissionController.contactCommission.managerName}"
									size="35" maxlength="255" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSION.MANAGER.PHONE']}"
										for="phoneManager" />
								</t:panelGroup>
								<e:inputText id="phoneManager"
									value="#{commissionController.contactCommission.managerPhone}"
									size="12" maxlength="10" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSION.MANAGER.MAIL']}"
										for="mailManager" />
								</t:panelGroup>
								<e:inputText id="mailManager"
									value="#{commissionController.contactCommission.managerMail}"
									size="35" maxlength="50" />
							</e:panelGrid>
						</t:div>
					</t:div>
				</t:div>
						

				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormMembre" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<t:panelGroup>
								<e:text value="#{msgs['MEMBERS']}"
									styleClass="section-smallTitle" />
								<t:popup styleClass="containerPopup" style="width:400px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="01"
									displayAtDistanceY="0">
									<t:graphicImage url="/media/images/informationSmall.png"
										style="cursor:pointer;" id="infoAnneeUni" />

									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['MEMBERS']}" />
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_green.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['MEMBERS.INFO.ALLREADY_USER']}" />
												</t:panelGroup>
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_orange.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['MEMBERS.INFO.NOT_USER']}" />
												</t:panelGroup>
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_red.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['MEMBERS.NEVER.USER']}" />
												</t:panelGroup>
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>

							</t:panelGroup>

							<t:panelGroup>
								<e:commandButton value="#{msgs['GESTIONNAIRE.SEARCH_LDAP']}"
									action="#{commissionController.goSearchMembers}">
									<t:updateActionListener
										value="#{ldapSearchController.ldapEnum.memberCmiValue}"
										property="#{ldapSearchController.ldapEnum.whereAreYouFrom}" />
								</e:commandButton>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									action="#{commissionController.addOneMember}">
								</e:commandButton>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="member"
						value="#{commissionController.keySetMbrToDisplay}"
						styleClass="displayInfo" alternateColors="false">
						<t:column styleClass="buttonTD">
							<t:graphicImage url="/media/images/flag_green.png"
								rendered="#{commissionController.membersToDisplay[member] == commissionController.isGestionnaire}" />
							<t:graphicImage url="/media/images/flag_orange.png"
								rendered="#{commissionController.membersToDisplay[member] == commissionController.mustBeGest}" />
							<!-- /*TODO probleme d'affichage flag pour les membres sasie a voir*/ -->
							<t:graphicImage url="/media/images/flag_red.png"
								rendered="#{commissionController.membersToDisplay[member] == commissionController.enterMbr
									|| (commissionController.membersToDisplay[member] != commissionController.mustBeGest
										&& commissionController.membersToDisplay[member] != commissionController.isGestionnaire)}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.NAME']}" />
							</f:facet>
							<e:text value="#{member.nom}" rendered="#{member.nom != null}" />
							<e:inputText value="#{member.nom}" size="31" maxlength="30"
								rendered="#{member.nom == null}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
							</f:facet>
							<e:text value="#{member.prenom}"
								rendered="#{member.prenom != null}" />
							<e:inputText value="#{member.prenom}" size="21" maxlength="20"
								rendered="#{member.prenom == null}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.MAIL']}" />
							</f:facet>
							<e:text value="#{member.adressMail}"
								rendered="#{member.adressMail != null}" />
							<e:inputText value="#{member.adressMail}" size="42"
								maxlength="50" rendered="#{member.adressMail == null}" />

						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.TYPE']}" />
							</f:facet>
							<e:selectOneMenu value="#{member.type}">
								<f:selectItems value="#{commissionController.typMbrItems}" />
							</e:selectOneMenu>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								rendered="#{member.adressMail != null}"
								action="#{commissionController.removeMember}"
								title="#{msgs['_.BUTTON.DELETE']}">
								<t:updateActionListener value="#{member}"
									property="#{commissionController.memberToDelete}" />
							</e:commandButton>
							<e:commandButton image="/media/images/add.png"
								styleClass="form-button-image"
								rendered="#{member.adressMail == null}"
								title="#{msgs['_.BUTTON.VALIDATE']}" />
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
	highlightInputAndSelect('enterCommissionForm');
	hideElement('enterCommissionForm:submitSelectCpFix');
	hideElement('enterCommissionForm:selectCommAdress');
	onblurInput('enterCommissionForm:codePostalFix',
			'enterCommissionForm:submitSelectCpFix');
</script>
</e:page>
