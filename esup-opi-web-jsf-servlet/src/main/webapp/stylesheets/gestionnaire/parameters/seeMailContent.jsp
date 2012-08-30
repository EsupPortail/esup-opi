<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{managedAccess.currentTraitement.libelle}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_addMailContent" styleClass="div_subSection"
			rendered="#{mailContentController.actionEnum.whatAction == mailContentController.actionEnum.addAction}">
			<%@include file="_mailContent/_addMailContent.jsp"%>
		</t:div>

		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="mailContentForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								immediate="true"
								rendered="#{managedAccess.addAuthorized}" >
								<t:updateActionListener value="#{mailContentController.actionEnum.addAction}" 
										property="#{mailContentController.actionEnum.whatAction}"/>
							</e:commandButton>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true" action="#{managedAccess.goDisplayFunction}" />
						</t:panelGroup>
					</t:div>

					<e:dataTable var="mailContent"
						value="#{mailContentController.mailContents}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{mailContent.code}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{mailContent.libelle}" />
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								action="#{mailContentController.goUpdate}"
								immediate="true" styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener value="#{mailContent}"
									property="#{mailContentController.mailContent}" />
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
