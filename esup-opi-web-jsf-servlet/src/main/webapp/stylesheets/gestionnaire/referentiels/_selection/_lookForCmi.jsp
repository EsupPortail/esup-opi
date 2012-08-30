<%@include file="../../../_include/_include.jsp"%>
<t:div id="blockFormSearch" styleClass="blockForm">
	<h:form id="lookForCmi" styleClass="opiR1_form">
		<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
			styleClass="tableJustWidthMax">
			<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.COM']}"
				styleClass="section-smallTitle" />
			<t:panelGroup>
				<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
					action="#{paginatorCommission.forceReload}" />
			</t:panelGroup>
		</e:panelGrid>
		<e:panelGrid styleClass="tableWidthMax" columns="4"
			columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
			</t:panelGroup>
			<e:inputText id="code"
				value="#{commissionController.paginator.commissionRechPojo.code}" />

			<t:panelGroup>
				<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelle" />
			</t:panelGroup>
			<e:inputText id="libelle"
				value="#{commissionController.paginator.commissionRechPojo.libelle}" />
		</e:panelGrid>
	</h:form>
</t:div>