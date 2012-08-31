<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}"
	authorized="#{sessionController.regimeInsUser.calInsIsOpen}">
	<%@include file="../../_include/_header.jsp"%>

	<h:form id="navigationHeader" styleClass="opiR1_form">
		<%@include file="../_navigation/_navInscription.jsp"%>
	</h:form>
	
	<%@include file="../../_include/_roadMap.jsp"%>
	
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['APPLICATION.BIG_TITLE']} : #{msgs['WELCOME.CREATE']}" />
		
		<t:htmlTag value="br"/>
		
		<e:messages styleClass="messageForUser"/>
		
		<h:form id="searchEtuR1Form" styleClass="opiR1_form">
			<t:div  style="width:650px; margin-left:5%;">
				<e:section value="#{msgs['INFO.CANDIDAT.SERVER_RI']}">
					<f:param value="#{sessionController.regimeInsUser.label}" />
				</e:section>
			</t:div>
			
			<t:div  styleClass="blockTable" style="width:650px; margin-left:5%;"
				rendered="#{sessionController.regimeInsUser.displayInfoFC}">
				<e:paragraph value="#{msgs['INFO.CANDIDAT.RESERVED_FC']}"
							escape="false" styleClass="section-header" />
			</t:div>

			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			
			<t:div  styleClass="blockTable" style="width:650px; margin-left:5%;">
				<e:paragraph value="#{msgs['INFO.CANDIDAT.RENNES1']}"
							escape="false" styleClass="section-black-smallTitle" />
			</t:div>
			
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			
			<t:div styleClass="blockTable" style="width:650px; margin-left:5%;">
				<t:div>
					<e:paragraph value="#{msgs['INFO.CANDIDAT.NO_INSCRIT']} : "
						styleClass="section-black-smallTitle"/>
				</t:div>
				<e:ul styleClass="listCreateDoss">
					<e:li rendered="#{!sessionController.regimeInsUser.displayInfoFC}">
						<t:div styleClass="labelListCreateDoss">
							<e:text value="#{msgs['FIELD_LABEL.KNOW_INE']} : " escape="false"
								styleClass="form-field-label" style="font-size:14px;"/>
						</t:div>
						<t:div styleClass="blockBlue" style="width:350px;">
							<e:panelGrid columns="2" style="margin-left:30%;">
								<t:panelGroup>
									<e:inputText id="numNNE" size="11" maxlength="10"
										value="#{individuController.pojoIndividu.individu.codeNNE}">
										<f:validateLength minimum="10" />
									</e:inputText>

									<e:inputText size="1" maxlength="1"
										value="#{individuController.pojoIndividu.individu.codeClefNNE}">
										<f:validateLength minimum="1" />
									</e:inputText>
								</t:panelGroup>
								<e:text value="#{msgs['FIELD_LABEL.INFO_INE']}" styleClass="span-text-small" />
							</e:panelGrid>
							
							<t:div style="text-align:center;">
								<e:text
									value="#{msgs['INFO.CANDIDAT.LOOK_FOR.INE']}"
									styleClass="span-text-small" />
							</t:div>
							
							<t:div style="text-align:center;">
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{individuController.goFindEtuR1}" />
							</t:div>
							<t:htmlTag value="br" />
							<t:div style="text-align:center;">
								<e:text
									value="#{msgs['INFO.CANDIDAT.LOOK_FOR.WHAT_INE']}"
									styleClass="span-text-small" escape="false"/>
							</t:div>
						</t:div>
					</e:li>
					<e:li>
						<t:div styleClass="labelListCreateDoss">
							<e:text value="#{msgs['FIELD_LABEL.NEVER_CANDIDATE']} #{msgs['APPLICATION.BIG_TITLE']} : "
								styleClass="form-field-label" style="font-size:14px;"/>
						</t:div>
						<e:ul>
							<e:li>
								<t:div styleClass="labelListCreateDoss">
									<e:text value="#{msgs['FIELD_LABEL.NOT_KNOW_INE']} : "
										styleClass="form-field-label" style="font-size:14px;"/>
								</t:div>
								<t:div style="text-align:center; width:350px;">
									<e:commandButton value="#{msgs['_.BUTTON.CREATE.DOSSIER']}"
										action="#{individuController.goFindEtuR1}">
										<t:updateActionListener value="#{true}"
											property="#{individuController.pojoIndividu.doNotHaveCodeNne}" />
									</e:commandButton>
									<t:htmlTag value="br"/>
									<t:htmlTag value="br"/>
								</t:div>
							</e:li>
						</e:ul>
					</e:li>
					<e:li>
						<t:div styleClass="labelListCreateDoss">
							<e:text value="#{msgs['FIELD_LABEL.EVER_CANDIDATE']} : "
								styleClass="form-field-label" style="font-size:14px;">
								<f:param value="#{msgs['APPLICATION.BIG_TITLE']}"/>
							</e:text>
						</t:div>
						
						<t:div styleClass="blockBlue" style="width:550px;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col1UnDemi">
								<t:panelGroup>
									<e:outputLabel value="#{msgs['INDIVIDU.NOM']}" for="nom" />
									<t:popup styleClass="containerPopup" style="width:180px;"
										closePopupOnExitingElement="true" closePopupOnExitingPopup="true"
										displayAtDistanceX="1" displayAtDistanceY="0">
										<t:graphicImage url="/media/images/informationSmall.png"
											style="cursor:pointer;" />
						
										<f:facet name="popup">
											<h:panelGroup>
												<h:panelGrid columns="1">
													<t:outputText styleClass="titrePopupAide"
														value="#{msgs['INDIVIDU.NOM']}" />
													<t:panelGroup>
														<t:outputText styleClass="libellesAide"
															value="#{msgs['INDIVIDU.NOM.HELP']}" />
													</t:panelGroup>
												</h:panelGrid>
											</h:panelGroup>
										</f:facet>
									</t:popup>
								</t:panelGroup>
								<e:inputText id="nom" size="32" maxlength="30"
									value="#{individuController.pojoIndividu.individu.nomPatronymique}" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['INDIVIDU.PRENOM']}" for="prenom" />
								</t:panelGroup>
								<e:inputText id="prenom" size="22" maxlength="20"
									value="#{individuController.pojoIndividu.individu.prenom}" />
								<t:panelGroup>
									<e:outputLabel value="#{msgs['INDIVIDU.DATE_NAI']}"
										for="dateNaissance" />
								</t:panelGroup>
								<e:inputText id="dateNaissance" size="10" maxlength="8"
									value="#{individuController.pojoIndividu.individu.dateNaissance}">
									<f:convertDateTime pattern="ddMMyyyy"
										timeZone="#{sessionController.timezone}" />
									<f:validateLength minimum="8"/>
								</e:inputText>
								<t:panelGroup>
									<e:outputLabel value="#{msgs['INDIVIDU.PAY_NAI']}"
										for="paysNaissance" />
									<t:outputText value="*" styleClass="etoileForChpObli" />
								</t:panelGroup>
								<t:panelGroup>
									<e:selectOneMenu id="paysNaissance"
										valueChangeListener="#{individuController.selectPay}"
										onchange="javascript:{simulateLinkClick('searchEtuR1Form:submitSelectPays');}"
										value="#{individuController.pojoIndividu.individu.codPayNaissance}">
										<f:selectItem itemLabel="" itemValue="" />
										<t:selectItems var="pays" value="#{adressController.pays}"
											itemLabel="#{pays.libPay}" itemValue="#{pays.codPay}" />
									</e:selectOneMenu>
									<e:commandButton id="submitSelectPays"/>
								</t:panelGroup>
								<t:panelGroup
									rendered="#{individuController.pojoIndividu.individu.codPayNaissance == adressController.codeFrance}">
									<e:outputLabel value="#{msgs['INDIVIDU.DEP_NAI']}" for="depNaissance" />
								</t:panelGroup>
								<t:panelGroup
									rendered="#{individuController.pojoIndividu.individu.codPayNaissance == adressController.codeFrance}">
									<e:selectOneMenu id="depNaissance"
										value="#{individuController.pojoIndividu.individu.codDepPaysNaissance}">
										<f:selectItem itemLabel="" itemValue="" />
										<t:selectItems var="dept" value="#{adressController.departements}"
											itemLabel="#{dept.libDep}" itemValue="#{dept.codDep}" />
									</e:selectOneMenu>
								</t:panelGroup>
							</e:panelGrid>
							
							<t:div style="text-align:center;">
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{individuController.goFindEtuR1}">
									<t:updateActionListener value="#{true}"
												property="#{individuController.pojoIndividu.isUsingSearch}" />
								</e:commandButton>
							</t:div>
						</t:div>
					</e:li>
				</e:ul>
			</t:div>
		</h:form>
		
		<t:htmlTag value="br"/>
		<t:htmlTag value="br"/>
		<t:htmlTag value="br"/>
		
	</t:div>
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navInscription.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigationLogin');
		highlightChildrenLiTags('navigationFooter:navigationLogin');
		hideElement('searchEtuR1Form:submitSelectPays');
		highlightInputAndSelect('searchEtuR1Form');
	</script>
</e:page>

