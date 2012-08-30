<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="Gestion des pièces justificatives" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteNomenclature.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeDocumentssForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormAdresse" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								action="#{nomenclatureController.goAddTypConv}" />
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
						</t:panelGroup>
					</t:div>
					<e:dataTable var="typeConvocation" value="#{nomenclatureController.typeConvocations}"
						styleClass="paginatorData" alternateColors="true">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{typeConvocation.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{typeConvocation.libelle}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{typeConvocation.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!typeConvocation.temoinEnService}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{commissionController.goSeeOneCmi}"
								title="#{msgs['_.BUTTON.DISPLAY']}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.readAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" 
								rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.updateAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{typeConvocation}"
									property="#{nomenclatureController.nomenclature}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.deleteAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{typeConvocation}"
									property="#{nomenclatureController.nomenclature}" />
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
