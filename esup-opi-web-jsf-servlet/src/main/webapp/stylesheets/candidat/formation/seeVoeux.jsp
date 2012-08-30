<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['INDIVIDU.YOUR.CANDIDATURES']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{formationController.action.whatAction == formationController.action.deleteAction}">
			<%@include file="_delete/_deleteIndVoeu.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeVoeuxForm" styleClass="opiR1_form">
				<t:div id="div_subSection" styleClass="div_subSection">
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div id="blockFormFormation" styleClass="blockForm">
						<t:div style="width:100%;">
							<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
								styleClass="tableJustWidthMax">
								<e:text value="#{msgs['FIELD_LABEL.LIST_CANDI']}" styleClass="section-smallTitle" />
								<t:panelGroup>
									<e:commandButton value="#{msgs['_.BUTTON.DOWNLOAD.DOSSIER']}"
										rendered="#{not empty sessionController.currentInd.indVoeuxPojo}"
										immediate="true" action="#{accueilController.printDossier}" />
									<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
										immediate="true"
										rendered="#{sessionController.currentInd.etat.canDoChoiceEtape
											&& sessionController.currentInd.regimeInscription.calInsIsOpen
											&& sessionController.currentInd.asRightsToUpdate}"
										action="#{formationController.goSearchFormation}" />
								</t:panelGroup>
							</e:panelGrid>
						</t:div>
						<e:dataTable var="indVoeuPojo"
							value="#{sessionController.currentInd.indVoeuxPojo}"
							alternateColors="false" renderedIfEmpty="false"
							styleClass="displayInfo">
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.FORMATION_ASK']}" />
								</f:facet>
								<e:text value="#{indVoeuPojo.vrsEtape.libWebVet}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['INDIVIDU.ETAT']}" />
								</f:facet>
								<e:text value="#{indVoeuPojo.etat.label}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.RESULT']}" />
								</f:facet>
								<t:outputText value="#{msgs['VOEU.RESULT.NON_DISPO']}"
									rendered="#{empty indVoeuPojo.indVoeu.avis}" />
								<t:dataList id="datalist" style="myStyle" var="avis"
									value="#{indVoeuPojo.indVoeu.avis}">
									<t:outputText value="#{avis.result.libelle}"
										rendered="#{avis.temoinEnService && avis.validation}" />
								</t:dataList>
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.COMMENTAIRE']}" />
								</f:facet>
								<t:outputText value="#{msgs['VOEU.RESULT.NON_DISPO']}"
									rendered="#{empty indVoeuPojo.indVoeu.avis}" />
								<t:dataList id="datalist" style="myStyle" var="avis2"
									value="#{indVoeuPojo.indVoeu.avis}">
									<t:outputText value="#{avis2.commentaire}"
										rendered="#{avis2.temoinEnService && avis2.validation}" />
								</t:dataList>
							</t:column>
							<t:column>
								<e:commandButton image="/media/images/cancel.png"
									styleClass="form-button-image"
									rendered="#{indVoeuPojo.calIsOpen
									|| sessionController.currentInd.isUpdaterOfThisStudent}"
									title="#{msgs['_.BUTTON.DELETE']}">
									<t:updateActionListener
										value="#{formationController.action.deleteAction}"
										property="#{formationController.action.whatAction}" />
									<t:updateActionListener value="#{indVoeuPojo}"
										property="#{formationController.indVoeuPojo}" />
								</e:commandButton>
							</t:column>
						</e:dataTable>
					</t:div>
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
				</t:div>
			</h:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigationLogin');
	highlightChildrenLiTags('navigationFooter:navigationLogin');
	highlightInputAndSelect('seeVoeuxForm');
</script>
</e:page>
