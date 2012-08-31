	<%@include file="../../_include/_include.jsp"%>
	<e:page stringsVar="msgs" locale="#{sessionController.locale}"
		authorized="#{managedAccess.readAuthorized}">
		<%@include file="../../_include/_header.jsp"%>
		<h:form id="navigationHeader">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
		<t:div id="maPage" styleClass="div_body">
			<e:section value="#{msgs['AVIS.TITLE.HISTO']}" />
			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>
			<t:div rendered="#{opinionController.actionEnum.whatAction != 
				opinionController.actionEnum.propositionAction}">
				<e:text value="#{msgs['FIELD_LABEL.AVIS.STUDENT']}" 
					styleClass="section-subheader"/>
				<e:text value="#{opinionController.indVoeuxPojo.indVoeu.individu.nomPatronymique} 
					#{opinionController.indVoeuxPojo.indVoeu.individu.prenom} 
					(#{opinionController.indVoeuxPojo.indVoeu.individu.numDossierOpi})"/>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<e:text value="#{msgs['FIELD_LABEL.AVIS.ETAPE']}" 
					styleClass="section-subheader"/>
				<e:text value="#{opinionController.indVoeuxPojo.vrsEtape.libWebVet}"/>
			</t:div>
			<t:div id="div_subSectionEnter" styleClass="div_subSection"
				rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.addAction
							|| opinionController.actionEnum.whatAction == opinionController.actionEnum.updateAction}">
				<%@include file="_block/_enterOpinion.jsp"%>
			</t:div>
			<%@include
				file="/stylesheets/gestionnaire/opinion/_block/_proposition.jsp"%>
			<t:div id="div_subSection" styleClass="div_subSection">
				<h:form id="seeAvisForm">
					<t:div id="blockFormAdresse" styleClass="blockForm">
						<t:div styleClass="blockButton"
							rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.emptyAction}">
							<t:panelGroup>
								<e:commandButton value="#{msgs['FIELD_LABEL.PROPOSITION']}"
									immediate="true"
									action="#{opinionController.addProposition}"/>
								<e:commandButton value="#{msgs['AVIS.BUTTON.APPEL']}"
									 immediate="true"
									 action="#{opinionController.goAddAvisAppel}"/>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									 immediate="true"
									 action="#{opinionController.goAddAvis}"/>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
										immediate="true"
										action="#{opinionController.goBackOpinions}"/>
							</t:panelGroup>
						</t:div>
						<e:dataTable var="avisPojo" value="#{opinionController.listAvisPojo}"
							styleClass="paginatorData" alternateColors="true">
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.LABEL_DECISION']}" />
								</f:facet>
								<e:text value="#{avisPojo.avis.result.libelle}"/>
							</t:column>
							<t:column rendered="#{opinionController.indVoeuxPojo.isUsingLC}">
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.RANG']}" />
								</f:facet>
								<e:text value="#{avisPojo.avis.rang}"/>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.COMM_MOTIV']}" />
								</f:facet>
								<e:text value="#{avisPojo.shortCommentaireMotiv}" />
								<t:popup styleClass="containerPopup" style="width:350px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="-50"
									displayAtDistanceY="0"
									rendered="#{avisPojo.isShortCommentaireMotiv}">
									<t:graphicImage url="/media/images/informationSmall.png"
										style="cursor:pointer;" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['FIELD_LABEL.LIBELLE']}" />
												<t:outputText styleClass="libellesAide"
													value="#{avisPojo.commentaireMotiv}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.DATE_CREATION']}" />
								</f:facet>
								<e:text value="#{avisPojo.avis.dateCreaEnr}">
									<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
										timeZone="#{sessionController.timezone}" />
								</e:text>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.DATE_MODIF']}" />
								</f:facet>
								<e:text value="#{avisPojo.avis.dateModifEnr}">
									<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
										timeZone="#{sessionController.timezone}" />
								</e:text>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.APPEL']}" />
								</f:facet>
								<t:graphicImage url="/media/images/check2.gif" 
									rendered="#{avisPojo.avis.appel}"/>
								<t:graphicImage url="/media/images/check0.gif" 
									rendered="#{!avisPojo.avis.appel}"/>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['AVIS.VALIDATE']}" />
								</f:facet>
								<t:graphicImage url="/media/images/check2.gif" 
									rendered="#{avisPojo.avis.validation}"/>
								<t:graphicImage url="/media/images/check0.gif" 
									rendered="#{!avisPojo.avis.validation}"/>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
								</f:facet>
								<t:graphicImage url="/media/images/check2.gif" 
									rendered="#{avisPojo.avis.temoinEnService}"/>
								<t:graphicImage url="/media/images/check0.gif" 
									rendered="#{!avisPojo.avis.temoinEnService}"/>
							</t:column>
							<t:column styleClass="buttonTD">
								<e:commandButton image="/media/images/update.png"
									immediate="true"
									styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" 
									action="#{opinionController.goUpdateAvis}"
									rendered="#{opinionController.actionEnum.whatAction == opinionController.actionEnum.emptyAction
											&& managedAccess.updateAuthorized && avisPojo.avis.temoinEnService
											&& !(avisPojo.avis.result.isFinal && avisPojo.avis.validation)}">
									<t:updateActionListener
										value="#{avisPojo.avis}"
										property="#{opinionController.avis}" />
								</e:commandButton>
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
	</script>
	</e:page>
