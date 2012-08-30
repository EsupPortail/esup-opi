<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PJ.IS_FOR_VET.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>

		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seePJForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormButton" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					
					<e:dataTable var="nomenclaturePojo" value="#{nomenclatureController.pieceJustificatives}"
						styleClass="displayInfo" alternateColors="false">
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.code}" />
						</t:column>
						<t:column width="40%">
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
						<t:column width="18%">
							<f:facet name="header">
								<t:outputText value="#{msgs['PJ.IS_FOR_ALL_VET']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.isForAllVet
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.isForAllVet
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature)}"/>
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.regimeInscription.label}"/>
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
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
								action="#{nomenclatureController.goSeeAffectPJ}"
								title="#{msgs['_.BUTTON.DISPLAY']}"
								rendered="#{!nomenclaturePojo.nomenclature.isForAllVet}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.readAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{nomenclaturePojo.nomenclature}"
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
