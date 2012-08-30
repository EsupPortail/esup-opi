<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<e:section value="#{managedAccess.currentTraitement.libelle}" />

	<h:form id="seeRdvForm" styleClass="div_subSection">

		<t:panelGroup
			rendered="#{consultRdvController.actionEnum.emptyAction == consultRdvController.actionEnum.whatAction}">
			<t:div styleClass="twoGroupsButton"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.emptyAction}">
				<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
					action="#{managedAccess.goDisplayFunction}">
					<t:updateActionListener
						value="#{paramRdvSupController.actionEnum.emptyAction}"
						property="#{paramRdvSupController.actionEnum.whatAction}" />
				</e:commandButton>
			</t:div>
			<t:div styleClass="twoGroupsButton"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}">
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{paramRdvController.update}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
					action="#{paramRdvController.reset}" />
			</t:div>
			<e:dataTable var="rdvSelect"
				value="#{paramRdvController.listCalendarRdv}"
				styleClass="paginatorData" alternateColors="true">

				<t:column rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.LIB_RDV']}" />
					</f:facet>
					<e:text value="#{rdvSelect.calendarRDV.titre}" />
				</t:column>


				<t:column rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.LIST_CGE_COMMISSION_VET']}"/>
					</f:facet>
					
					<e:text value="#{rdvSelect.calendarRDV.codeCge}" rendered="#{rdvSelect.calendarRDV.codeCge != null}"/>
				
					<t:dataTable var="commissionPojo" value="#{rdvSelect.listCommissionPojo}"
								rendered="#{not empty rdvSelect.listCommissionPojo}">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}"/>
							</f:facet>
							<e:text value="#{commissionPojo.commission.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CORRESPONDANT']}"/>
							</f:facet>
							<e:text value="#{commissionPojo.contactCommission.corresponding}"/>
						</t:column>
					</t:dataTable>
					
					<t:dataTable var="vetCalendarPojo" value="#{rdvSelect.listVetCalendarPojo}"
								rendered="#{not empty rdvSelect.listVetCalendarPojo}">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE_VET']}"/>
							</f:facet>
							<e:text value="#{vetCalendarPojo.vetCalendar.codEtp}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIB_VET']}"/>
							</f:facet>
							<e:text value="#{vetCalendarPojo.libWebVet}"/>
						</t:column>
					</t:dataTable>
				</t:column>

				<t:column rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.DATE_DEBUT']}" />
					</f:facet>

					<e:text value="#{rdvSelect.calendarRDV.dateDebutInsc}"
						rendered="#{rdvSelect.calendarRDV.participeOK}">
						<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR"
							type="date" dateStyle="short" pattern="dd/MM/yy" />
					</e:text>
				</t:column>


				<t:column rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:facet name="header">
						<t:outputText value="#{msgs['FIELD_LABEL.DATE_FIN']}" />
					</f:facet>

					<e:text value="#{rdvSelect.calendarRDV.dateFinInsc}"
						rendered="#{rdvSelect.calendarRDV.participeOK}">
						<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR"
							type="date" dateStyle="short" pattern="dd/MM/yy" />
					</e:text>
				</t:column>

				<t:column styleClass="buttonTD">
					<e:commandButton image="/media/images/update.png" immediate="true"
						styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}"
						action="#{consultRdvController.goSeeAllConsultRdv}"
						rendered="#{managedAccess.readAuthorized}">
						<t:updateActionListener
							value="#{consultRdvController.actionEnum.readAction}"
							property="#{consultRdvController.actionEnum.whatAction}" />
						<t:updateActionListener value="#{rdvSelect.calendarRDV}"
							property="#{consultRdvController.calendarRdv}" />
					</e:commandButton>
				</t:column>
				<t:column styleClass="buttonTD">
					<e:commandButton value="#{msgs['CALENDAR_RDV.EXPORT_ALL_LISTE']}"
						rendered="#{managedAccess.updateAuthorized}"
						action="#{consultRdvController.csvGeneration}">
						<t:updateActionListener value="#{rdvSelect.calendarRDV}"
							property="#{consultRdvController.calendarRdv}" />
					</e:commandButton>
				</t:column>
			</e:dataTable>
		</t:panelGroup>
		<t:panelGroup id="addParamRdv"
			rendered="#{consultRdvController.actionEnum.whatAction == consultRdvController.actionEnum.readAction
						|| consultRdvController.actionEnum.whatAction == consultRdvController.actionEnum.updateAction}">
			<%@include file="_rdv/_consultRdv.jsp"%>
		</t:panelGroup>
	</h:form>


	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		hideElement('seeRdvForm:individuData:submitPageSize');
	</script>

</e:page>
