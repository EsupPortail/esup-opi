<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['MOTIV_AVIS.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionEnter" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.addAction
						|| nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction}">
			<%@include file="_motivation/_enterMotivationAvis.jsp"%>
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteNomenclature.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeConvocationTypesForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormAdresse" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
							 immediate="true"
							 action="#{nomenclatureController.goAddMotivation}"/>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					<e:dataTable var="nomenclaturePojo" value="#{nomenclatureController.allMotivationsAvis}"
						styleClass="paginatorData" alternateColors="true">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.shortLabel}" />
							<t:popup styleClass="containerPopup" style="width:350px;"
								closePopupOnExitingElement="true"
								closePopupOnExitingPopup="true" displayAtDistanceX="-50"
								displayAtDistanceY="0"
								rendered="#{nomenclaturePojo.isShortLabel}">
								<t:graphicImage url="/media/images/informationSmall.png"
									style="cursor:pointer;" />
								<f:facet name="popup">
									<h:panelGroup>
										<h:panelGrid columns="1">
											<t:outputText styleClass="titrePopupAide"
												value="#{msgs['FIELD_LABEL.LIBELLE']}" />
											<t:outputText styleClass="libellesAide"
												value="#{nomenclaturePojo.nomenclature.libelle}" />
										</h:panelGrid>
									</h:panelGroup>
								</f:facet>
							</t:popup>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.SHORT_LIB']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.shortLabel}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.temoinEnService}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" 
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction
										&& managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.updateAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{nomenclaturePojo.nomenclature}"
									property="#{nomenclatureController.nomenclature}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction
									&& managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.deleteAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{nomenclaturePojo.nomenclature}"
									property="#{nomenclatureController.nomenclature}" />
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
