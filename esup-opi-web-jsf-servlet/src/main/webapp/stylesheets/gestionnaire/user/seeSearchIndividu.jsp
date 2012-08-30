<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['INDIVIDU.FOUND']}" />
		<h:form id="seeManagersForm">
			
			<%@include file="/stylesheets/gestionnaire/user/_student/_lookForIndividu.jsp"%>

			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:div id="blockFormListDom" styleClass="blockForm">
				<e:dataTable var="individu" id="managerData"
					value="#{paginatorIndividu.visibleItems}"
					styleClass="paginatorData" alternateColors="true"
					renderedIfEmpty="false">
					<f:facet name="header">
						<h:panelGroup >
							<e:paginator id="paginatorIndividu"
								paginator="#{paginatorIndividu}"
								itemsName="#{msgs['INDIVIDUS']}"
								onchange="javascript:{simulateLinkClick('seeManagersForm:managerData:submitPageSize');}" />
							<e:commandButton id="submitPageSize"
								action="#{paginatorIndividu.forceReload}" />
						</h:panelGroup>
					</f:facet>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NUM_DOSSIER']}" />
						</f:facet>
						<e:text value="#{individu.numDossierOpi}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.NOM']}" />
						</f:facet>
						<e:text value="#{individu.nomPatronymique}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.PRENOM']}" />
						</f:facet>
						<e:text value="#{individu.prenom}" />
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['INDIVIDU.DATE_NAI_COURT']}" />
						</f:facet>
						<e:text value="#{individu.dateNaissance}" >
							<f:convertDateTime pattern="dd/MM/yyyy"
								timeZone="#{sessionController.timezone}" />
						</e:text>
					</t:column>


					<t:column>
						<e:commandButton image="/media/images/magnifier.png"
							styleClass="form-button-image" immediate="true"
							title="#{msgs['_.BUTTON.DISPLAY']}" 
							action="#{individuController.goSeeOneIndividu}">
							<t:updateActionListener
									value="#{individu}"
									property="#{individuController.pojoIndividu.individu}" />
						</e:commandButton>
					</t:column>
				</e:dataTable>
			</t:div>
		</h:form>

		<e:text value="#{msgs['INDIVIDU.NOT.FOUND']}" 
		rendered="#{empty paginatorIndividu.visibleItems}" />

		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGestionnaire.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	hideElement('seeManagersForm:managerData:submitPageSize');
</script>
</e:page>
