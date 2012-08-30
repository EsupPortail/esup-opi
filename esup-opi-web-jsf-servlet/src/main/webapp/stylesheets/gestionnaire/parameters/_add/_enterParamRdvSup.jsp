<%@include file="../../../_include/_include.jsp"%>
<h:form id="formAddParamRdvSup">
	<e:section value="#{msgs['CALENDAR_RDV.CALENDAR_SELECTED']}">
		<f:param value="#{paramRdvController.calendarRDV.titre}"/>
	</e:section>
	
	<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
		rendered="#{managedAccess.updateAuthorized}"
		actionListener="#{paramRdvSupController.initAction}"
		styleClass="form-button-image" immediate="true">
		<t:updateActionListener
			value="#{paramRdvController.actionEnum.emptyAction}"
			property="#{paramRdvController.actionEnum.whatAction}"/>
		<t:updateActionListener
			value="#{paramRdvSupController.actionEnum.emptyAction}"
			property="#{paramRdvSupController.actionEnum.whatAction}"/>
	</e:commandButton>
	
	<e:panelGrid columns="3" cellpadding="0" cellspacing="1" styleClass="calendrierRdv"
		columnClasses="colRDVLeft,colRDVCenter,colRDVRight">
		<t:commandLink id="clmoisprec" immediate="true"
			actionListener="#{paramRdvSupController.precMonth}">
			<t:graphicImage border="0"
				value="/media/images/backward.png"
				alt="#{msgs['CALENDAR_RDV.BUTTON.PREC_MONTH']}"/>
		</t:commandLink>
		
		<t:div style="text-align:center; width:850px;" >
			<e:subSection	
				rendered="#{paramRdvSupController.scheduleModel != null}"
				style="text-transform: uppercase;" 
				value="#{paramRdvSupController.monthSelected}">
				<f:convertDateTime type="date" pattern="MMMM yyyy" timeZone="Europe/Paris"/>
			</e:subSection>
			
			<t:schedule id="scalendar"
				value="#{paramRdvSupController.scheduleModel}"
				headerClass="schedule_header"
				entryRenderer="#{extendedEntryRenderer}"
				compactMonthRowHeight="80"
				theme="default"
				headerDateFormat="EEEE d MMMM yy"
				tooltip="false"
				readonly="false"				
				submitOnClick="true"
				action="#{paramRdvSupController.scheduleAction}"/>
				
			<e:panelGrid columns="4" 
				width="100%"
				cellspacing="0" 
				cellpadding="1"
				border="0"
				columnClasses="center"
				rowClasses="center" 
				style="margin-top:5px">	
			
				<e:outputLabel for="debutAM"
					styleClass="panelgrid_header"
					value="#{msgs['CALENDAR_RDV.TEXT.DEBUT_AM']} "/>
				<e:outputLabel for="finAM"
					styleClass="panelgrid_header" 
					value="#{msgs['CALENDAR_RDV.TEXT.FIN_AM']} "/>
				<e:outputLabel for="debutPM"
					styleClass="panelgrid_header"
					value="#{msgs['CALENDAR_RDV.TEXT.DEBUT_PM']} "/>
				<e:outputLabel for="finPM"
					styleClass="panelgrid_header" 
					value="#{msgs['CALENDAR_RDV.TEXT.FIN_PM']} "/>
				
				<e:selectOneMenu id="debutAM"
					immediate="true"
					validator="#{paramRdvSupController.validateDebutAMHour}" 
					value="#{paramRdvSupController.dateDebutAM}"
					onchange="submit();">
					<f:selectItems id="debutAMItems" value="#{paramRdvSupController.AMHours}" />
					<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
				</e:selectOneMenu>
				
				<e:selectOneMenu id="finAM"
					immediate="true"
					validator="#{paramRdvSupController.validateFinAMHour}" 
					value="#{paramRdvSupController.dateFinAM}"
					onchange="submit();" >
					<f:selectItems id="finAMItems" value="#{paramRdvSupController.AMHours}"/>					
					<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
				</e:selectOneMenu>
				
				<e:selectOneMenu id="debutPM"
					immediate="true"
					validator="#{paramRdvSupController.validateDebutPMHour}"
					value="#{paramRdvSupController.dateDebutPM}"
					onchange="submit();" >
					<f:selectItems id="debutPMItems" value="#{paramRdvSupController.PMHours}" />		
					<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
				</e:selectOneMenu>
				
				<e:selectOneMenu id="finPM"
					immediate="true" 
					validator="#{paramRdvSupController.validateFinPMHour}"
					value="#{paramRdvSupController.dateFinPM}"
					onchange="submit();" >
					<f:selectItems id="finPMItems" value="#{paramRdvSupController.PMHours}" />
					<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
				</e:selectOneMenu>
			</e:panelGrid>
		</t:div>
		
		<t:commandLink id="clmoissuiv" immediate="true"
			actionListener="#{paramRdvSupController.nextMonth}">
			<t:graphicImage border="0"
				value="/media/images/forward.png" 
				alt="#{msgs['CALENDAR_RDV.BUTTON.NEXT_MONTH']}"/>
		</t:commandLink>
	</e:panelGrid>
				
	<e:message for="formAddParamRdvSup"/>
</h:form>

<t:panelGroup id="addParamJourHo"
	rendered="#{paramRdvSupController.actionEntry.whatAction == paramRdvSupController.actionEntry.addAction
				|| paramRdvSupController.actionEntry.whatAction == paramRdvSupController.actionEntry.updateAction}">
	<%@include file="_enterParamJourHoraire.jsp"%>
</t:panelGroup>
