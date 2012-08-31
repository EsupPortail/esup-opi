<%@include file="../../../_include/_include.jsp"%>
<t:div styleClass="twoGroupsButton">
	<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true">
		<t:updateActionListener
			value="#{consultRdvController.actionEnum.emptyAction}"
			property="#{consultRdvController.actionEnum.whatAction}" />
	</e:commandButton>
</t:div>

<%@include
	file="/stylesheets/gestionnaire/referentiels/_rdv/_lookForIndividu.jsp"%>

<t:panelGroup rendered="#{consultRdvController.inSearch}">
	<e:text value="#{msgs['CALENDAR_RDV.ADD_ENTRY']}"
		rendered="#{not consultRdvController.weekScheduleModel.entrySelected}" />
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
						onchange="javascript:{simulateLinkClick('seeRdvForm:individuData:submitPageSize');}" />
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
			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['_.BUTTON.ADD']}" />
				</f:facet>
				<h:commandButton id="addEtudiant" styleClass="save"
					action="#{consultRdvController.ajouter}" 
					rendered="#{consultRdvController.calendarRdv.dateParCandidat[individuPojo.individu] == null}"
					image="/media/images/add.gif" alt="#{msgs['_.BUTTON.ADD']}">
					<t:updateActionListener value="#{individuPojo.individu}"
						property="#{consultRdvController.candidat}" />
				</h:commandButton>
			</t:column>
			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['CALENDAR_RDV.REMOVE_ENTRY']}" />
				</f:facet>
				<h:commandButton action="#{consultRdvController.supprimer}"
					image="/media/images/delete.gif" alt="#{msgs['CALENDAR_RDV.REMOVE_ENTRY']}"
					rendered="#{consultRdvController.calendarRdv.dateParCandidat[individuPojo.individu] != null}"
					onclick="if (!confirm('#{msgs['CALENDAR_RDV.REMOVE_ENTRY.WARNING']}')) return false;">
					<t:updateActionListener value="#{individuPojo.individu}"
						property="#{consultRdvController.candidat}" />
				</h:commandButton>
			</t:column>
			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['CALENDAR_RDV.MOVE_ENTRY']}" />
				</f:facet>
				<h:commandButton action="#{consultRdvController.moveEntry}"
					rendered="#{consultRdvController.calendarRdv.dateParCandidat[individuPojo.individu] != null}"
					image="/media/images/move.gif" alt="#{msgs['CALENDAR_RDV.MOVE_ENTRY']}">
					<t:updateActionListener value="#{individuPojo.individu}"
						property="#{consultRdvController.candidat}" />
				</h:commandButton>
			</t:column>
			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['CALENDAR_RDV.TEXT.RDV.TEXT']}" />
				</f:facet>
				<h:outputText value="#{consultRdvController.calendarRdv.dateParCandidat[individuPojo.individu]}"
					rendered="#{consultRdvController.calendarRdv.dateParCandidat[individuPojo.individu] != null}">
					<f:convertDateTime pattern="dd/MM HH:mm" timeZone="Europe/Paris" />
				</h:outputText>
			</t:column>
		</e:dataTable>
	</t:div>
</t:panelGroup>
<t:panelGroup>
	<e:text value="#{msgs['CALENDAR_RDV.SELECT_ENTRY']}"
		rendered="#{not consultRdvController.weekScheduleModel.entrySelected}" />

	<e:panelGrid id="pgAction" columns="1" cellpadding="5px" width="100%"
		rendered="#{consultRdvController.weekScheduleModel.entrySelected}">

		<e:panelGrid id="pgTimeEntry" columns="2">
			<e:outputLabel for="tHDeb"
				value="#{msgs['CALENDAR_RDV.TEXT.RDV.TEXT.SELECTED']} : " />
			<e:text id="tHDeb"
				value="#{consultRdvController.weekScheduleModel.selectedEntry.startTime}">
				<f:convertDateTime pattern="EEEE d MMMM HH:mm"
					timeZone="Europe/Paris" />
			</e:text>
		</e:panelGrid>
		<e:dataTable id="etudiants" renderedIfEmpty="false"
			value="#{consultRdvController.calendarRdv.dateParCandidat.key}" var="etudiant">
			<t:column sortable="true">
					<f:facet name="header">
						<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
					</f:facet>
					<e:text value="#{etudiant.numDossierOpi}" />
			</t:column>
			<t:column sortable="true">
				<f:facet name="header">
					<h:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
				</f:facet>
				<e:text value="#{etudiant.prenom}" />
			</t:column>
			<t:column sortable="true">
				<f:facet name="header">
					<h:outputText value="#{msgs['INDIVIDU.NOM']}" />
				</f:facet>
				<h:outputLink value="mailto:#{etudiant.candidat.emailAnnuaire}">
					<e:text value="#{etudiant.nomPatronymique}" />
				</h:outputLink>
			</t:column>
			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['CALENDAR_RDV.REMOVE_ENTRY']}" />
				</f:facet>
				<h:commandButton action="#{consultRdvController.supprimer}"
					image="/media/images/delete.gif" alt="Supprimer"
					onclick="if (!confirm('#{msgs['CALENDAR_RDV.REMOVE_ENTRY.WARNING']}')) return false;">
					<t:updateActionListener value="#{etudiant}"
						property="#{consultRdvController.candidat}" />
				</h:commandButton>
			</t:column>

			<t:column style="text-align: center;">
				<f:facet name="header">
					<h:outputText value="#{msgs['CALENDAR_RDV.MOVE_ENTRY']}" />
				</f:facet>
				<h:commandButton action="#{consultRdvController.moveEntry}"
					image="/media/images/move.gif" alt="#{msgs['CALENDAR_RDV.MOVE_ENTRY']}">
					<t:updateActionListener value="#{etudiant}"
						property="#{consultRdvController.candidat}" />
				</h:commandButton>
			</t:column>
		</e:dataTable>
		<e:panelGrid columns="2"
			rendered="#{consultRdvController.movedEntryMode}">
			<e:text value="#{consultRdvController.candidat.numDossierOpi}" />
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{consultRdvController.annuler}" />
			<e:text value="#{msgs['CALENDAR_RDV.MOVE_ENTRY']}" />
			<e:commandButton value="#{msgs['_.BUTTON.SAVE']}"
				action="#{consultRdvController.deplacer}" />
		</e:panelGrid>
	</e:panelGrid>
</t:panelGroup>
<e:messages />
<e:panelGrid columns="3" cellpadding="0" cellspacing="1"
	columnClasses="colRDVLeft,colRDVCenter,colRDVRight">
	<t:commandLink id="clmoisprec" immediate="true"
		actionListener="#{consultRdvController.before}">
		<t:graphicImage border="0" value="/media/images/backward.png"
			alt="#{msgs['CALENDAR_RDV.BUTTON.PREC_MONTH']}" />
	</t:commandLink>

	<t:div style="text-align:center; width:850px;">
		<e:subSection style="text-transform: uppercase;"
			value="#{consultRdvController.weekScheduleModel.selectedDate}">
			<f:convertDateTime type="date" pattern="MMMM yyyy"
				timeZone="Europe/Paris" />
		</e:subSection>

		<t:schedule id="weekCalendar"
			value="#{consultRdvController.weekScheduleModel}"
			binding="#{consultRdvController.uiSchedule}" theme="evolution"
			headerDateFormat="EEEE d MMMM yyyy" tooltip="true"
			submitOnClick="true" selectedEntryClass="schedule_selected"
			entryRenderer="#{extendedEntryRenderer}"
			visibleStartHour="#{consultRdvController.startHour}"
			visibleEndHour="#{consultRdvController.endHour}"
			action="#{consultRdvController.scheduleAction}"
			mouseListener="#{consultRdvController.scheduleClicked}" />

		<e:commandButton value="#{msgs['CALENDAR_RDV.EXPORT_LISTE']}"
			rendered="#{consultRdvController.weekScheduleModel.selectedDate != null && managedAccess.updateAuthorized}"
			action="#{consultRdvController.csvGeneration}">
			<f:param value="#{consultRdvController.selectedDay}" />
		</e:commandButton>
	</t:div>

	<t:commandLink id="clmoissuiv" immediate="true"
		actionListener="#{consultRdvController.next}">
		<t:graphicImage border="0" value="/media/images/forward.png"
			alt="#{msgs['CALENDAR_RDV.BUTTON.NEXT_MONTH']}" />
	</t:commandLink>

</e:panelGrid>



