<%@include file="../../../_include/_include.jsp"%>
<t:div style="width:100%;">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
		styleClass="tableJustWidthMax">
		<e:text value="#{msgs['PJ']}"
			styleClass="section-smallTitle" />
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
				rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.readAction
				|| nomenclatureController.wayfEnum.whereAreYouFrom == nomenclatureController.wayfEnum.affectPJValue}"
				action="#{nomenclatureController.goSeePJforVet}" />
		</t:panelGroup>
	</e:panelGrid>
</t:div>

<e:dataTable var="nomenclaturePojo" value="#{nomenclatureController.allPJs}"
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
						<t:column styleClass="buttonTD" style="text-align: right;">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								action="#{nomenclatureController.removeTrtPJ}" >
								<t:updateActionListener value="#{nomenclaturePojo}"
									property="#{nomenclatureController.PJTraitee}" />
							</e:commandButton>
						</t:column>
</e:dataTable>
					