<%@include file="../../../_include/_include.jsp"%>
<h:form id="formaddMailContent">
	<t:div styleClass="messageForUser">
		<e:paragraph value="#{msgs['MAIL_CONTENT.INFO.CREATE_BEAN']}" styleClass="msg-info"/>
	</t:div>
	<t:div id="blockAddMailContent" styleClass="blockForm">
		<t:div style="width:100%;text-align:right;">
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
					action="#{mailContentController.add}" />
				<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
					action="#{mailContentController.reset}" />
			</t:panelGroup>
		</t:div>
		<e:panelGrid styleClass="tableWidthMax" columns="4"
			columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="code" size="16" maxlength="15" required="true"
				value="#{mailContentController.mailContent.code}" />

			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelle" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
			<e:inputText id="libelle" size="41" maxlength="40" required="true"
				value="#{mailContentController.mailContent.libelle}" />

		</e:panelGrid>

	</t:div>
</h:form>