<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_selectParam" styleClass="div_subSection"
			rendered="#{mailContentController.actionEnum.whatAction == mailContentController.actionEnum.updateAction}">
			<%@include file="_mailContent/_selectParam.jsp"%>
		</t:div>
		
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="updateMailContentForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormButton" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{mailContentController.mailContent.libelle}" styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{mailContentController.update}"
									rendered="#{managedAccess.updateAuthorized}" />
								<e:commandButton value="#{msgs['MAIL_CONTENT.SELECT_PARAM']}"
									immediate="true" >
									<t:updateActionListener value="#{mailContentController.actionEnum.updateAction}" 
										property="#{mailContentController.actionEnum.whatAction}"/>
								</e:commandButton>
								<e:commandButton value="#{msgs['MAIL_CONTENT.INIT.DEFAULT_VALUE']}"
											action="#{mailContentController.initDefaultValue}" />
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									immediate="true" action="#{mailContentController.goAllMails}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
				</t:div>
				<t:div id="blockForm" styleClass="blockForm">
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
						</t:panelGroup>
						<e:text id="code"
								value="#{mailContentController.mailContent.code}" />
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
								for="libelle" />
						</t:panelGroup>
						<e:inputText id="libelle" size="41" maxlength="40" required="true"
				                      value="#{mailContentController.mailContent.libelle}" />
					</e:panelGrid>
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div styleClass="blockTable">
						<t:panelGroup style="padding-right:25px;">
							<e:outputLabel value="#{msgs['FIELD_LABEL.SUBJECT']}"
								for="subject" />
							<t:outputText value="*" styleClass="etoileForChpObli" />
						</t:panelGroup>
						<t:panelGroup>
							<e:inputText size="100" id="subject"
								value="#{mailContentController.mailContent.subject}" />
						</t:panelGroup>
					</t:div>
					<t:htmlTag value="br" />
					<t:div>
						<t:panelGroup>
							<fck:editor 
								toolbarSet="MailContent"
								value="#{mailContentController.mailContent.body}"/>
						</t:panelGroup>
					</t:div>
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
	highlightInputAndSelect('updateMailContentForm');
</script>
</e:page>
