<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CALENDAR.TITLE.LIST']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{calendarController.actionEnum.whatAction == calendarController.actionEnum.deleteAction}">
			<%@include file="_calendar/_deleteCal.jsp"%>
		</t:div>
	
		<t:div id="blockFormSearch" styleClass="blockForm">
			<h:form id="lookForCmiForm" styleClass="opiR1_form">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.CAL']}"
						styleClass="section-smallTitle" />
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
							rendered="#{calendarController.actionEnum.whatAction != calendarController.actionEnum.deleteAction}"
							action="#{calendarController.goSearchCalendars}" />
					</t:panelGroup>
				</e:panelGrid>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
					</t:panelGroup>
					<e:inputText id="code"
						value="#{calendarController.calendarRechPojo.code}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
							for="libelle" />
					</t:panelGroup>
					<e:inputText id="libelle"
						value="#{calendarController.calendarRechPojo.libelle}" />
					<t:panelGroup>
						<e:outputLabel value="#{msgs['CALENDAR.CHOICE.TYP']}"
							for="lesCalendars" />
					</t:panelGroup>
					<t:panelGroup>
						<e:selectOneMenu id="lesCalendars" 
							value="#{calendarController.calendarRechPojo.type}"
							onchange="javascript:{simulateLinkClick('addCalendarForm:submitTypCal');}">
							<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}"
							itemValue="" />
							<f:selectItems value="#{calendarController.typeCalItems}"/>
						</e:selectOneMenu>
					</t:panelGroup>
					
				</e:panelGrid>
			</h:form>
		</t:div>

		<t:htmlTag value="br" />
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeCalendrierForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<t:panelGroup/>
							<t:panelGroup
								rendered="#{calendarController.actionEnum.whatAction != calendarController.actionEnum.deleteAction}">
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									immediate="true"
									action="#{calendarController.goAddCall}"/>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="calendar"
						value="#{calendarController.calendars}"
						styleClass="paginatorData" alternateColors="true">
						<t:column width="14%">
							<f:facet name="header">
								<t:outputText value="Type" />
							</f:facet>
							<e:text value="#{calendarController.calendarType[calendar.type]}" />
						</t:column>
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{calendar.code}" />
						</t:column>
						<t:column width="55%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{calendar.libelle}" />
						</t:column>
						<t:column width="15%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{calendar.temoinEnService}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!calendar.temoinEnService}" />
						</t:column>
						<t:column styleClass="buttonTD"
							rendered="#{calendarController.actionEnum.whatAction != calendarController.actionEnum.deleteAction}">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{calendarController.goSeeOneCal}"
								title="#{msgs['_.BUTTON.DISPLAY']}">
								<t:updateActionListener value="#{calendar}"
									property="#{calendarController.beanCalendar.calendar}" />
								<t:updateActionListener
									value="#{calendar.type}"
									property="#{calendarController.beanCalendar.typeSelected}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD"
							rendered="#{calendarController.actionEnum.whatAction != calendarController.actionEnum.deleteAction}">
							<e:commandButton image="/media/images/update.png"
								immediate="true" styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								action="#{calendarController.goUpdateCal}"
								rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{calendarController.actionEnum.updateAction}"
									property="#{calendarController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{calendar.type}"
									property="#{calendarController.beanCalendar.typeSelected}" />
								<t:updateActionListener value="#{calendar}"
									property="#{calendarController.beanCalendar.calendar}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD"
							rendered="#{calendarController.actionEnum.whatAction != calendarController.actionEnum.deleteAction}">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{managedAccess.deleteAuthorized}"
								action="#{calendarController.goDeleteCal}">
								<t:updateActionListener value="#{calendar}"
									property="#{calendarController.beanCalendar.calendar}" />
							</e:commandButton>
						</t:column>
					</e:dataTable>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
			</h:form>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
</script>
</e:page>
