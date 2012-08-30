<%@include file="../../../_include/_include.jsp"%>
<h:form id="formAddParamJourHo">
	<e:text value="#{msgs['CALENDAR_RDV.JOUR_SELECTED']}" styleClass="section-header">
		<f:param value="#{paramRdvSupController.jourSelectedString}" />
	</e:text>
	
	<e:panelGrid columns="4" 
		width="100%"
		cellspacing="0" 
		cellpadding="1"
		border="0"
		columnClasses="center"
		rowClasses="center" 
		style="margin-top:5px">	
	
		<e:outputLabel for="jourDebutAM"
			styleClass="panelgrid_header"
			value="#{msgs['CALENDAR_RDV.TEXT.DEBUT_AM']} "/>
		<e:outputLabel for="jourFinAM"
			styleClass="panelgrid_header" 
			value="#{msgs['CALENDAR_RDV.TEXT.FIN_AM']} "/>
		<e:outputLabel for="jourDebutPM"
			styleClass="panelgrid_header"
			value="#{msgs['CALENDAR_RDV.TEXT.DEBUT_PM']} "/>
		<e:outputLabel for="jourFinPM"
			styleClass="panelgrid_header" 
			value="#{msgs['CALENDAR_RDV.TEXT.FIN_PM']} "/>
		
		<e:selectOneMenu id="jourDebutAM"
			validator="#{paramRdvSupController.validateDebutAMHourJourHoraire}" 
			value="#{paramRdvSupController.dateDebutAMJourHoraire}"
			onchange="submit();">
			<f:selectItems id="debutAMItems" value="#{paramRdvSupController.AMHours}" />
			<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
		</e:selectOneMenu>
		
		<e:selectOneMenu id="jourFinAM"
			validator="#{paramRdvSupController.validateFinAMHourJourHoraire}" 
			value="#{paramRdvSupController.dateFinAMJourHoraire}"
			onchange="submit();" >
			<f:selectItems id="finAMItems" value="#{paramRdvSupController.AMHours}"/>					
			<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
		</e:selectOneMenu>
		
		<e:selectOneMenu id="jourDebutPM"
			validator="#{paramRdvSupController.validateDebutPMHourJourHoraire}"
			value="#{paramRdvSupController.dateDebutPMJourHoraire}"
			onchange="submit();" >
			<f:selectItems id="debutPMItems" value="#{paramRdvSupController.PMHours}" />		
			<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
		</e:selectOneMenu>
		
		<e:selectOneMenu id="jourFinPM"
			validator="#{paramRdvSupController.validateFinPMHourJourHoraire}"
			value="#{paramRdvSupController.dateFinPMJourHoraire}"
			onchange="submit();" >
			<f:selectItems id="finPMItems" value="#{paramRdvSupController.PMHours}" />
			<f:convertDateTime type="date" pattern="k:mm" timeZone="Europe/Paris"/>
		</e:selectOneMenu>
	</e:panelGrid>

	<e:commandButton rendered="#{paramRdvSupController.actionEntry.whatAction == paramRdvSupController.actionEntry.addAction}"
		styleClass="form-button-image" immediate="true"
		action="#{paramRdvSupController.validerAjout}"
		value="#{msgs['_.BUTTON.VALIDATE']}" />
		
	<e:commandButton rendered="#{paramRdvSupController.actionEntry.whatAction == paramRdvSupController.actionEntry.updateAction}"
		styleClass="form-button-image" immediate="true"
		action="#{paramRdvSupController.validerModif}"
		value="#{msgs['_.BUTTON.VALIDATE']}" />
		
	<e:commandButton rendered="#{paramRdvSupController.actionEntry.whatAction == paramRdvSupController.actionEntry.updateAction}"
		styleClass="form-button-image" immediate="true"
		action="#{paramRdvSupController.supprimer}"
		value="#{msgs['_.BUTTON.DELETE']}" />
		
	<e:commandButton styleClass="form-button-image" immediate="true"
		action="#{paramRdvSupController.annulerModif}"
		value="#{msgs['_.BUTTON.CANCEL']}" />
	
	<e:message for="formAddParamJourHo"/>
</h:form>
