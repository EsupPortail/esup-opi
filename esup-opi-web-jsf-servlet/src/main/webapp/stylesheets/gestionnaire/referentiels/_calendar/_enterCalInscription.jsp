<%@include file="/stylesheets/_include/_include.jsp"%>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:div id="blockFormDate" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['CALENDAR.INS.TITLE']}"
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
		<e:inputText id="libelle" size="41" maxlength="40"
			value="#{calendarController.beanCalendar.calendar.libelle}" />
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}" for="temEnSveCalIns"/>
		<e:selectBooleanCheckbox id="temEnSveCalIns" 
			value="#{calendarController.beanCalendar.calendar.temoinEnService}" />
		
		<t:panelGroup/>
		<t:panelGroup/>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.START_DATE']}"
				for="dateDbt" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="dateDbt" size="10" maxlength="8"
			value="#{calendarController.beanCalendar.calendar.startDate}">
			<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
			<f:validateLength minimum="8"/>
		</e:inputText>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.END_DATE']}" for="dateFin" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:inputText id="dateFin" size="10" maxlength="8"
			value="#{calendarController.beanCalendar.calendar.endDate}">
			<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
			<f:validateLength minimum="8"/>
		</e:inputText>
	</e:panelGrid>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<%@include file="/stylesheets/gestionnaire/referentiels/_commission/_selectCmi.jsp"%>
<t:htmlTag value="br" />
<t:htmlTag value="br" />


