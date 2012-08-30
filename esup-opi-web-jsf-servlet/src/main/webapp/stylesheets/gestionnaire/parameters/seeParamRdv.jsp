<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['CALENDAR_RDV.MANAGED']}"/>
	<t:div styleClass="messageForUser">
		<e:messages />
	</t:div>
	<t:panelGroup id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.deleteAction}">
		<%@include file="_delete/_deleteParamRdv.jsp"%>
	</t:panelGroup>
	<h:form id="seeRdvForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{paramRdvController.goAddParamRdv}">
				<t:updateActionListener
					value="#{paramRdvSupController.actionEnum.emptyAction}"
					property="#{paramRdvSupController.actionEnum.whatAction}"/>
			</e:commandButton>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}">
				<t:updateActionListener
					value="#{paramRdvSupController.actionEnum.emptyAction}"
					property="#{paramRdvSupController.actionEnum.whatAction}"/>
			</e:commandButton>
		</t:div>
		
		<e:dataTable var="rdvSelect" value="#{paramRdvController.listCalendarRdv}" 
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.emptyAction
						|| paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.deleteAction}"
			styleClass="paginatorData" alternateColors="true">
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIB_RDV']}"/>
				</f:facet>
				<e:text value="#{rdvSelect.calendarRDV.titre}"/>
			</t:column>
			
			
			<t:column>
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
			
			<% /*
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.MSG_ETUDIANT']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.msgEtudiant}" 
						escape="false" 
						style="text-align: left;"/>
			</t:column>
			*/ %>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.PARTICIPE']}"/>
				</f:facet>
				
				<e:text rendered="#{rdvSelect.calendarRDV.participeOK == true}"
					value="#{msgs['CALENDAR_RDV.PARTICIPE_YES']}"/>
				<e:text rendered="#{rdvSelect.calendarRDV.participeOK == false}"
					value="#{msgs['CALENDAR_RDV.PARTICIPE_NO']}"/>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.DATE_DEBUT']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.calendarRDV.dateDebutInsc}"
					rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR" type="date" dateStyle="short" pattern="dd/MM/yy" />
				</e:text>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.DATE_FIN']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.calendarRDV.dateFinInsc}"
					rendered="#{rdvSelect.calendarRDV.participeOK}">
					<f:convertDateTime timeZone="Europe/Paris" locale="fr_FR" type="date" dateStyle="short" pattern="dd/MM/yy" />
				</e:text>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.NB_ETUDIANT_PAR_HEURE']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.calendarRDV.nbreMaxEtud}"
					rendered="#{rdvSelect.calendarRDV.participeOK}"/>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.NB_JOURS_OUVRES']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.calendarRDV.nbJoursAvantPro}"
					rendered="#{rdvSelect.calendarRDV.participeOK}"/>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.NB_JOURS_PROPOSES']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.calendarRDV.nbJoursApresPro}"
					rendered="#{rdvSelect.calendarRDV.participeOK}"/>
			</t:column>
			
			
			<% /*
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.MSG_MAIL']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.msgMailConfirmation}"
					escape="false"
					style="text-align: left;"
					rendered="#{rdvSelect.participeOK}"/>
			</t:column>
			
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.MSG_VALIDATION']}"/>
				</f:facet>
				
				<e:text value="#{rdvSelect.msgValidation}"
					escape="false"
					style="text-align: left;"
					rendered="#{rdvSelect.participeOK}"/>
			</t:column>
			*/
			%>
			
			<t:column styleClass="buttonTD"
				rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/Preferences.gif"
					rendered="#{managedAccess.updateAuthorized}"
					actionListener="#{paramRdvSupController.initAction}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.PREFERENCES']}">
					<t:updateActionListener
						value="#{paramRdvController.actionEnum.confirmAction}"
						property="#{paramRdvController.actionEnum.whatAction}"/>
					<t:updateActionListener
						value="#{paramRdvSupController.actionEnum.updateAction}"
						property="#{paramRdvSupController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{rdvSelect.calendarRDV}"
						property="#{paramRdvController.calendarRDV}"/>
				</e:commandButton>
				
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{paramRdvController.actionEnum.updateAction}"
						property="#{paramRdvController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{rdvSelect.calendarRDV}"
						property="#{paramRdvController.calendarRDV}"/>
					<t:updateActionListener
						value="#{paramRdvSupController.actionEnum.emptyAction}"
						property="#{paramRdvSupController.actionEnum.whatAction}"/>
				</e:commandButton>
				
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{paramRdvController.actionEnum.deleteAction}"
						property="#{paramRdvController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{rdvSelect.calendarRDV}"
						property="#{paramRdvController.calendarRDV}"/>
					<t:updateActionListener
						value="#{paramRdvSupController.actionEnum.emptyAction}"
						property="#{paramRdvSupController.actionEnum.whatAction}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		
		<t:panelGroup id="addParamRdv"
			rendered="#{paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.addAction
						|| paramRdvController.actionEnum.whatAction == paramRdvController.actionEnum.updateAction}">
			<%@include file="_add/_enterParamRdv.jsp"%>
		</t:panelGroup>
	</h:form>
		
	<t:panelGroup id="addParamRdvSup"
		rendered="#{paramRdvSupController.actionEnum.whatAction == paramRdvSupController.actionEnum.updateAction}">
		<%@include file="_add/_enterParamRdvSup.jsp"%>
	</t:panelGroup>
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
	</script>
</e:page>
