<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_PJ.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>

		<%/* Ligne de delete */%>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteNomenclature.jsp"%>
		</t:div>



		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seePJForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormButton" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								action="#{nomenclatureController.goAddPJ}" 
								rendered="#{nomenclatureController.rightPjForAllVet && managedAccess.addAuthorized}"/>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								action="#{nomenclatureController.goAddOrChoicesPJ}" 
								rendered="#{!nomenclatureController.rightPjForAllVet && managedAccess.addAuthorized}"/>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					
					<e:dataTable var="nomenclaturePojo" value="#{nomenclatureController.pieceJustificatives}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.code}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
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
								<t:outputText value="#{msgs['PJ.IS_FOR_ALL_VET']}" />
							</f:facet>
							<t:selectBooleanCheckbox value="#{nomenclatureController.nomenclature.isForAllVet}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}"/>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.isForAllVet
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.isForAllVet
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" />
							</f:facet>
							<e:selectOneMenu id="regimeInscription"
								value="#{nomenclatureController.nomenclature.codeRI}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}">
								<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
							</e:selectOneMenu>
							<e:selectOneMenu id="regimeInscription2" readonly="true"
								value="#{nomenclaturePojo.nomenclature.codeRI}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature}">
								<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
							</e:selectOneMenu>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:selectBooleanCheckbox value="#{nomenclatureController.nomenclature.temoinEnService}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}"/>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.temoinEnService
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.temoinEnService
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{nomenclatureController.goSeeOnePJ}"
								title="#{msgs['_.BUTTON.DISPLAY']}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.readAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{nomenclaturePojo.nomenclature}"
									property="#{nomenclatureController.nomenclature}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								action="#{nomenclatureController.goUpdatePJ}"
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
									&& managedAccess.deleteAuthorized && nomenclaturePojo.allRight}">
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
