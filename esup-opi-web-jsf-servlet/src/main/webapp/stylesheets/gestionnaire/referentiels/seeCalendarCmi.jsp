<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}" 
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CALENDAR.TITLE.CONSULT']}" />
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
			<h:form id="seeOneCalendarForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div style="width:100%;">
					<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
						styleClass="tableJustWidthMax">
						<e:text value="#{msgs['CALENDAR.CMI.TITLE']}"
							styleClass="section-smallTitle" />
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.UPDATE']}"
									immediate="true"
									action="#{calendarController.goUpdateCal}"
									rendered="#{managedAccess.updateAuthorized}" >
									<t:updateActionListener
											value="#{calendarController.actionEnum.updateAction}"
											property="#{calendarController.actionEnum.whatAction}" />
							</e:commandButton>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
								action="#{commissionController.goSeeOneCmi}" />
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
				<%@include file="_calendar/_seeCalCommission.jsp"%>
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