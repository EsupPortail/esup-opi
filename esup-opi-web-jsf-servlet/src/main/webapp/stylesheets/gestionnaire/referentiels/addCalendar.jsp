<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}" 
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CALENDAR.TITLE.ADD']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div styleClass="maxDivTextRight">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CHAMP']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="addCalendarForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormTypCal" styleClass="blockForm">
					<e:panelGrid styleClass="tableWidthMax" columns="2"
						columnClasses="col1UnDemi,col2UnDemi">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['CALENDAR.CHOICE.TYP']}"
								for="typCal" />
						</t:panelGroup>
						<t:panelGroup>
							<e:selectOneMenu
								valueChangeListener="#{calendarController.selectType}"
								value="#{calendarController.beanCalendar.typeSelected}"
								onchange="javascript:{simulateLinkClick('addCalendarForm:submitTypCal');}">
								<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}"
								itemValue="" />
								<f:selectItems value="#{calendarController.typeCalItems}"/>
							</e:selectOneMenu>
							<e:commandButton id="submitTypCal" action="#{calendarController.selectType}"/>
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
				<t:div id="calTypInscription" rendered="#{calendarController.beanCalendar.typeSelected == calendarController.typCalInscription}">
					<%@include file="_calendar/_enterCalInscription.jsp"%>
				</t:div>
				<t:div id="calTypCommission" rendered="#{calendarController.beanCalendar.typeSelected == calendarController.typCalCommission}">
					<e:text value="#{msgs['CALENDAR.CMI.INFO.EXIST']}">
						<f:param value="#{calendarController.dateBackDefault}"/>
					</e:text>
				</t:div>
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
		hideElement('addCalendarForm:submitTypCal');
		highlightInputAndSelect('addCalendarForm');
		hideElement('submitCmi');
	</script>
</e:page>