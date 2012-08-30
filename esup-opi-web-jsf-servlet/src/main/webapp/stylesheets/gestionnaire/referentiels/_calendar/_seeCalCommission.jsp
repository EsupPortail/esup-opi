<%@include file="../../../_include/_include.jsp"%>
<e:panelGrid styleClass="tableWidthMax" columns="4"
	columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="codeCmi" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<e:text id="codeCmi"
		value="#{calendarController.beanCalendar.calendar.code}" />
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelleCmi" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<e:text id="libelleCmi"
		value="#{calendarController.beanCalendar.calendar.libelle}" />

	
	<t:panelGroup>
		<e:outputLabel value="#{msgs['CALENDAR.CMI.END_DAT_CONF_RES']}"
			for="dateFin"/>
	</t:panelGroup>
	<e:text id="dateFin"
		value="#{calendarController.beanCalendar.calendar.endDatConfRes}">
		<f:convertDateTime pattern="dd/MM/yyyy"
			timeZone="#{sessionController.timezone}" />
	</e:text>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['COMMISSION.DAT_END_BACK_DOS_SHORT']}"
			for="dateBack" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<e:text id="dateBack"
		value="#{calendarController.beanCalendar.calendar.datEndBackDossier}">
		<f:convertDateTime pattern="dd/MM/yyyy"
			timeZone="#{sessionController.timezone}" />
	</e:text>
	<t:panelGroup/>
	<t:panelGroup/>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['CALENDAR.CMI.COMM_DATE_END_BACK']}"
			for="commDateBack" />
	</t:panelGroup>
	<e:text id="commDateBack"
		value="#{calendarController.beanCalendar.calendar.commDatEndBack}"/>
	<e:outputLabel value="#{msgs['COMMISSIONS']}" for="commission" />
	<e:text id="commission"
		value="#{calendarController.beanCalendar.calendar.commission.libelle}" />
	<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
		for="temEnSveCalCmi" />
	<t:panelGroup>
		<t:graphicImage id="temEnSveCalCmi" url="/media/images/check2.gif"
			rendered="#{calendarController.beanCalendar.calendar.temoinEnService}" />
		<t:graphicImage url="/media/images/check0.gif"
			rendered="#{!calendarController.beanCalendar.calendar.temoinEnService}" />
	</t:panelGroup>

</e:panelGrid>

<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:div id="blockFormDateReunion" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:text value="#{msgs['REUNION_CMI.SEANCES']}"
			styleClass="section-smallTitle" />
	</t:div>
	<e:dataTable var="reunion" styleClass="displayInfo"
		alternateColors="false" value="#{calendarController.reunions}">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.DATE']}" />
			</f:facet>
			<e:text id="dateReunion" value="#{reunion.date}">
				<f:convertDateTime pattern="dd/MM/yyyy"
					timeZone="#{sessionController.timezone}" />
			</e:text>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.HOUR']}" />
			</f:facet>
			<e:text id="hhReunion" value="#{reunion.heure}">
				<f:convertDateTime pattern="HH:mm" timeZone="#{sessionController.timezone}" />
			</e:text>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.PLACE']}" />
			</f:facet>
			<e:text id="libelle" value="#{reunion.lieu}" />
		</t:column>
	</e:dataTable>
</t:div>