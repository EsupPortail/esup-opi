<%@include file="/stylesheets/_include/_include.jsp"%>
<t:div>	
	<e:panelGrid styleClass="tableWidthMax" columns="4"
		columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="codeIns" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:text id="codeIns" 
			value="#{calendarController.beanCalendar.calendar.code}" />
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelleIns" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:text id="libelleIns" 
			value="#{calendarController.beanCalendar.calendar.libelle}" />
		
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}" />
		<t:panelGroup>
			<t:graphicImage url="/media/images/check2.gif"
						rendered="#{calendarController.beanCalendar.calendar.temoinEnService}" />
			<t:graphicImage url="/media/images/check0.gif"
						rendered="#{!calendarController.beanCalendar.calendar.temoinEnService}" />
		</t:panelGroup>
		<t:panelGroup/>
		<t:panelGroup/>
		
		
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.START_DATE']}"
				for="dateDbtIns" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:text id="dateDbtIns"
			value="#{calendarController.beanCalendar.calendar.startDate}">
			<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
		</e:text>
		<t:panelGroup>
			<e:outputLabel value="#{msgs['FIELD_LABEL.END_DATE']}" for="dateFinIns" />
			<t:outputText value="*" styleClass="etoileForChpObli" />
		</t:panelGroup>
		<e:text id="dateFinIns" 
			value="#{calendarController.beanCalendar.calendar.endDate}">
			<f:convertDateTime pattern="dd/MM/yyyy" timeZone="#{sessionController.timezone}"/>
		</e:text>
	</e:panelGrid>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />
<t:htmlTag value="br" />

<t:div id="blockCmiSelected" styleClass="blockForm">
	<t:div style="width:100%;">
		<e:text value="#{msgs['COMMISSIONS']}"
				styleClass="section-smallTitle" />
	</t:div>
	<e:dataTable var="commission"
		value="#{calendarController.beanCalendar.calendar.commissions}"
		styleClass="displayInfo" alternateColors="false">
		<t:column width="30%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
			</f:facet>
			<e:text value="#{commission.code}" />
		</t:column>
		<t:column width="70%">
			<f:facet name="header">
				<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
			</f:facet>
			<e:text value="#{commission.libelle}" />
		</t:column>
	</e:dataTable>
</t:div>
<t:htmlTag value="br" />
<t:htmlTag value="br" />


