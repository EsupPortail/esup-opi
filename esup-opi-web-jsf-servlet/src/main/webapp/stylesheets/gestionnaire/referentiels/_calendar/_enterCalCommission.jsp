<%@include file="../../../_include/_include.jsp"%>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:div id="blockFormDate" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['CALENDAR.CMI.TITLE']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{calendarController.add}"
					rendered="#{managedAccess.addAuthorized 
						&& calendarController.actionEnum.whatAction == calendarController.actionEnum.addAction}" />
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{calendarController.update}"
					rendered="#{managedAccess.updateAuthorized 
						&& calendarController.actionEnum.whatAction == calendarController.actionEnum.updateAction}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
					action="#{calendarController.goSeeAllCal}" />
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:panelGrid styleClass="tableWidthMax" columns="4"
		columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="code" size="12" maxlength="11"
			value="#{calendarController.beanCalendar.calendar.code}" />
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelle" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="libelle" size="81" maxlength="80"
			value="#{calendarController.beanCalendar.calendar.libelle}" />
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['CALENDAR.CMI.END_DAT_CONF_RES']}"
				for="dateFin" />
		</t:panelGroup>
		<e:inputText id="dateFin" size="10" maxlength="8"
			value="#{calendarController.beanCalendar.calendar.endDatConfRes}">
			<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
			<f:validateLength minimum="8"/>
		</e:inputText>
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['COMMISSION.DAT_END_BACK_DOS_SHORT']}"
				for="dateBack" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="dateBack" size="10" maxlength="8"
			value="#{calendarController.beanCalendar.calendar.datEndBackDossier}">
			<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
			<f:validateLength minimum="8"/>
		</e:inputText>
		<t:panelGroup/>
		<t:panelGroup/>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['CALENDAR.CMI.COMM_DATE_END_BACK']}"
				for="commDateBack" />
		</t:panelGroup>
		<e:inputText id="commDateBack" size="80" maxlength="100"
			value="#{calendarController.beanCalendar.calendar.commDatEndBack}"/>
		<e:outputLabel value="#{msgs['COMMISSIONS']}" for="commission" />
		<e:text id="commission"
			value="#{calendarController.beanCalendar.calendar.commission.libelle}" />
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}" for="temEnSveCalIns"/>
		<e:selectBooleanCheckbox id="temEnSveCalIns" 
			value="#{calendarController.beanCalendar.calendar.temoinEnService}" />
	</e:panelGrid>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:div id="blockFormDateReunion" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['REUNION_CMI.SEANCES']}" styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.ADD']}" 
					action="#{calendarController.addReunion}"/>
			</t:panelGroup>
		</e:panelGrid>
	</t:div>
	<e:dataTable var="reunion"
		styleClass="displayInfo" alternateColors="false"
		value="#{calendarController.reunions}">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.DATE']}" />
			</f:facet>
			<e:inputText id="dateReunion" size="10" maxlength="8"
					value="#{reunion.date}">
				<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
				<f:validateLength minimum="8"/>
			</e:inputText>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.HOUR']}" />
			</f:facet>
			<e:inputText id="hhReunion" size="6" maxlength="5"
					value="#{reunion.heure}">
				<f:convertDateTime pattern="HH:mm" timeZone="#{sessionController.timezone}"/>
			</e:inputText>
		</t:column>
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['REUNION_CMI.PLACE']}" />
			</f:facet>
			<e:inputText id="libelle" size="61" maxlength="255"
					value="#{reunion.lieu}" />
		</t:column>
		<t:column>
			<e:commandButton image="/media/images/cancel.png"
				action="#{calendarController.removeReunion}"
				styleClass="form-button-image" title="#{msgs['_.BUTTON.DELETE']}" >
				<t:updateActionListener value="#{reunion}" property="#{calendarController.reunionToRemove}"/>
			</e:commandButton>
		</t:column>
	
	</e:dataTable>
</t:div>