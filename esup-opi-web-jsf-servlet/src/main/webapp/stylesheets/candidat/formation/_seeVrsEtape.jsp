<%@include file="../../_include/_include.jsp"%>
<t:div id="div_sectionSelectEtape"
	rendered="#{not empty formationController.searchFormationPojo.versionEtapes}">
	<h:form id="selectEtapeForm" styleClass="opiR1_form">
		<t:div id="blockFormEtape" styleClass="blockForm">
			<t:div style="width:100%;" styleClass="tableJustWidthMax">
				<e:text value="#{msgs['FORMATION.TITLE.LIST']}"
					styleClass="section-smallTitle" />
			</t:div>
			<e:dataTable var="versionEtapePojo" id="versionEtapeData"
				value="#{formationController.searchFormationPojo.versionEtapes}"
				styleClass="paginatorData" alternateColors="true">
				<t:column>
					<f:facet name="header">
						<t:outputText
							value="#{msgs['FIELD_LABEL.INTITULE_FORMA']}" />
					</f:facet>
					<e:text value="#{versionEtapePojo.versionEtape.libWebVet}" />
				</t:column>
				<t:column>
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.CANDI_PERIOD']}" />
					</f:facet>
					<t:panelGroup 
						rendered="#{versionEtapePojo.calPeriod != null}">
		                <e:text value="#{versionEtapePojo.shortCalPeriod}" />
		                <t:popup styleClass="containerPopup"
		                    style="width:200px;"
		                    closePopupOnExitingElement="true"
		                    closePopupOnExitingPopup="true" displayAtDistanceX="-250"
		                    displayAtDistanceY="-100"
		                    rendered="#{versionEtapePojo.isShortCalPeriod}">
		                    <t:graphicImage url="../../../media/images/informationSmall.png"
		                        style="cursor:pointer;" id="infoAnneeUni" />
		                    <f:facet name="popup">
		                        <h:panelGroup>
		                            <h:panelGrid columns="1">
		                                <t:outputText styleClass="titrePopupAide" value="#{msgs['FIELD_LABEL.CANDI_PERIOD']}"/>
		                                <t:outputText styleClass="libellesAide" value="#{versionEtapePojo.calPeriod}"/>
		                            </h:panelGrid>
		                        </h:panelGroup>
		                    </f:facet>
		                </t:popup>
		            </t:panelGroup>
					
					<e:text value="#{msgs['FIELD_LABEL.CAL_INDISPO']}"
						rendered="#{versionEtapePojo.calPeriod == null}" />
				</t:column>
			</e:dataTable>
		</t:div>
	</h:form>
</t:div>

<t:div id="div_sectionEtpEmpty"
	rendered="#{empty formationController.searchFormationPojo.versionEtapes
	&& formationController.searchFormationPojo.versionEtapes != null}">
	<t:div id="blockFormEtpEmpty" styleClass="blockForm">
		<t:div style="width:100%;">
			<e:text value="#{msgs['FORMATION.TITLE.LIST']}"
				styleClass="section-smallTitle" />
		</t:div>
		<t:div styleClass="blockTable">
			<e:text escape="false"
				value="#{msgs['FORMATION.NOT_EXIST.IN_CMI']}" >
				<f:param value="#{formationController.searchFormationPojo.vrsDipSelected.libWebVdi}"/>
			</e:text>
		</t:div>
	</t:div>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
