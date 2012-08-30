<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />



		<%@include
			file="/stylesheets/gestionnaire/opinion/_block/_lookForIndividu.jsp"%>
		<%@include
			file="/stylesheets/gestionnaire/opinion/_block/_proposition.jsp"%>
		<h:form id="seeOpinionsForm">
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />

			<t:div styleClass="messageForUser">
				<e:messages/>
			</t:div>
			<t:div style="width:100%;text-align:right;">
				<t:panelGroup>
					<e:commandButton value="#{msgs['_.BUTTON.SAVE']}"
						action="#{opinionController.saveAllByIndividu}"/>
					<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
						action="#{managedAccess.goDisplayFunction}" />
				</t:panelGroup>
			</t:div>


			<t:div style="width:100%;">
				<e:dataTable var="individuPojo" id="individuData"
					value="#{paginatorIndividuPojo.indPojosWithWishForOneCmi}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false" rowIndexVar="variable">
					<f:facet name="header">
						<h:panelGroup>
							<e:paginator id="paginatorIndividuPojo"
								paginator="#{paginatorIndividuPojo}"
								itemsName="#{msgs['INDIVIDUS']}"
								onchange="javascript:{simulateLinkClick('seeOpinionsForm:individuData:submitPageSize');}" />
							<e:commandButton id="submitPageSize"
								action="#{paginatorIndividuPojo.forceReload}" />
						</h:panelGroup>
					</f:facet>
					<t:column width="10%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
						</f:facet>
						<e:text value="#{individuPojo.individu.numDossierOpi}" />
					</t:column>
					<t:column width="20%">
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NOM_PRENOM']}" />
						</f:facet>
						<e:text
							value="#{individuPojo.individu.nomPatronymique} 
							#{individuPojo.individu.prenom}" />
					</t:column>

					<t:column width="25%">
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
								<t:popup styleClass="containerPopup" style="width:350px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="-50"
									displayAtDistanceY="0">
									<e:text value="#{indVoeuxPojo.vrsEtape.licEtp}"
										style="cursor:pointer;" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}" />
												<t:outputText styleClass="libellesAide"
													value="#{indVoeuxPojo.vrsEtape.libWebVet}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
									<e:text value="#{indVoeuxPojo.avisEnService.result.code}" />
									<e:text value="(#{indVoeuxPojo.avisEnService.rang})" 
										rendered="#{indVoeuxPojo.avisEnService.rang != null}"/>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column width="2%">
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
								<t:popup styleClass="containerPopup" style="width:400px;"
									closePopupOnExitingElement="true"
									rendered="#{indVoeuxPojo.avisEnService.result.code != null}"
									closePopupOnExitingPopup="true" displayAtDistanceX="-50"
									displayAtDistanceY="0">
									<t:graphicImage url="/media/images/informationSmall.png"
										style="cursor:pointer;" id="avisEnService" />
									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['AVIS.END']} : #{indVoeuxPojo.avisEnService.result.libelle}" />
												<t:outputText styleClass="libellesAide"
													value="#{indVoeuxPojo.avisEnService.motivationAvis.libelle}" />
												<t:outputText styleClass="libellesAide"
													value="#{indVoeuxPojo.avisEnService.commentaire}" />
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
								
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column width="2%">
						<f:facet name="header">
							<t:outputText value="#{msgs['AVIS.LABEL_DECISION']}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin:0px;padding:0px; height: 20px;">
								<t:panelGroup>
									<e:selectOneMenu id="choiceTypDecision"
										converter="#{typeDecisionConverter}"
										value="#{indVoeuxPojo.newAvis.result}"
										onchange="javascript:{simulateLinkClick('seeOpinionsForm:changeTypeDecision');}"
										disabled="#{indVoeuxPojo.avisEnService.result.isFinal 
											&& indVoeuxPojo.avisEnService.validation}" >
										<f:selectItems value="#{nomenclatureController.typeDecisionItems}" />
									</e:selectOneMenu>
								</t:panelGroup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column rendered="#{paginatorIndividuPojo.isUsingDEF}">
						<f:facet name="header">
							<t:outputText value="#{msgs['AVIS.PROPOSITION.SMALL_TITLE']}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
								<t:panelGroup>
									<e:commandButton image="/media/images/add.png"
										immediate="true" styleClass="form-button-image"
										title="#{msgs['FIELD_LABEL.PROPOSITION']}"
										rendered="#{managedAccess.updateAuthorized
													&& indVoeuxPojo.isUsingDEF}"
										action="#{opinionController.addProposition}">
										<t:updateActionListener value="#{indVoeuxPojo}"
											property="#{opinionController.indVoeuxPojo}" />
									</e:commandButton>
								</t:panelGroup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column rendered="#{paginatorIndividuPojo.isUsingLC}">
						<f:facet name="header">
								<t:outputText value="#{msgs['AVIS.RANG']}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
								<t:panelGroup>
									<e:inputText id="rang" size="5"
									value="#{indVoeuxPojo.newAvis.rang}" maxlength="5" 
									disabled="#{indVoeuxPojo.avisEnService.result.isFinal 
											&& indVoeuxPojo.avisEnService.validation}" 
									rendered="#{indVoeuxPojo.isUsingLC}"/>
								</t:panelGroup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column width="10%" rendered="#{paginatorIndividuPojo.isUsingDEF}">
						<f:facet name="header">
							<t:outputText value="#{msgs['AVIS.MOTIVATION']}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin:0px;padding:0px; height: 20px;">
								<t:panelGroup>
									<e:selectOneMenu id="choiceMotivAvis"
										converter="#{motivationAvisConverter }"
										value="#{indVoeuxPojo.newAvis.motivationAvis}"
										rendered="#{managedAccess.updateAuthorized
													&& indVoeuxPojo.isUsingDEF}"
										disabled="#{indVoeuxPojo.avisEnService.result.isFinal 
											&& indVoeuxPojo.avisEnService.validation}" >
										<f:selectItem itemLabel="" itemValue="" />
										<t:selectItems var="motivationAvis" 
											value="#{nomenclatureController.motivationsAvis}"
											itemLabel="#{motivationAvis.nomenclature.code}-#{motivationAvis.nomenclature.shortLabel}"
											itemValue="#{motivationAvis.nomenclature}" />
									</e:selectOneMenu>
								</t:panelGroup>
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column width="20%">
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
						</f:facet>
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin:0px;padding:0px; height: 20px;">
								<e:inputText id="commentaire"size="30"
									value="#{indVoeuxPojo.newAvis.commentaire}" maxlength="500"
									disabled="#{indVoeuxPojo.avisEnService.result.isFinal 
											&& indVoeuxPojo.avisEnService.validation}" />
							</e:ul>
						</t:dataList>
					</t:column>
					<t:column width="2%">
						<t:dataList var="indVoeuxPojo" id="indVoeux"
							value="#{individuPojo.voeuxSortedByLic}">
							<e:ul style="margin: 0px; padding: 0px; height: 20px;">
								<e:commandButton image="/media/images/magnifier.png"
									immediate="true" styleClass="form-button-image"
									title="#{msgs['BUTTON.UPDATE']}"
									rendered="#{managedAccess.updateAuthorized}"
									action="#{opinionController.goSeeOpinionVoeu}">
									<t:updateActionListener value="#{indVoeuxPojo}"
										property="#{opinionController.indVoeuxPojo}" />
									<t:updateActionListener value="#{opinionController.wayfEnum.opinionValue}"
										property="#{opinionController.wayfEnum.whereAreYouFrom}" />
								</e:commandButton>
							</e:ul>
						</t:dataList>
					</t:column>
				</e:dataTable>
				<e:commandButton id="changeTypeDecision"
										value="#{msgs[_.BUTTON.CHANGE]}"
										action="#{opinionController.selectTypeDecisionIndividu}" />
			</t:div>
		

		</h:form>

		<e:text value="#{msgs['INDIVIDU.NOT.FOUND']}"
			rendered="#{empty paginatorIndividuPojo.visibleItems}" />

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
	hideElement('seeOpinionsForm:individuData:submitPageSize');
	hideElement('seeOpinionsForm:changeTypeDecision')
	hideElement('seeOpinionsForm:individuData:submitChangeMotiv');
</script>
</e:page>