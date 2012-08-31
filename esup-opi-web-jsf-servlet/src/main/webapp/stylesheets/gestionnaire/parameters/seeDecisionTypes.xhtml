<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_DECISION.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteNomenclature.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeDecisionTypesForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								action="#{nomenclatureController.goAddTypDecision}"
								rendered="#{managedAccess.addAuthorized}" />
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true" action="#{managedAccess.goDisplayFunction}" />
						</t:panelGroup>
					</t:div>
					<t:div styleClass="blockButton"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction}">
						<t:div>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								action="#{nomenclatureController.update}" />
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								immediate="true" action="#{nomenclatureController.reset}" />
						</t:div>
					</t:div>

					<e:dataTable var="typeDecision"
						value="#{nomenclatureController.typeDecisions}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{typeDecision.code}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_DECISION.CODE_APOGEE']}" />
							</f:facet>
							<e:text value="#{typeDecision.codeApogee}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature}" />
							<e:selectOneMenu id="codeApogee"
								value="#{nomenclatureController.nomenclature.codeApogee}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}">
								<f:selectItem itemLabel="F" itemValue="F" />
								<f:selectItem itemLabel="D" itemValue="D" />
								<f:selectItem itemLabel="E" itemValue="E" />
							</e:selectOneMenu>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{typeDecision.libelle}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature}" />
							<e:inputText
								value="#{nomenclatureController.nomenclature.libelle}" size="31"
								maxlength="30"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.SHORT_LIB']}" />
							</f:facet>
							<e:text value="#{typeDecision.shortLabel}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature}" />
							<e:inputText
								value="#{nomenclatureController.nomenclature.shortLabel}"
								size="11" maxlength="10"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_DECISION.CONVOCATION_TYPE']}" />
							</f:facet>
							<e:selectOneMenu id="typeConvocation" readonly="#{true}"
									rendered="#{!(nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature)}"
								value="#{typeDecision.codeTypeConvocation}">
								<f:selectItems value="#{nomenclatureController.typeConvocationsItems}"/>
							</e:selectOneMenu>
							<e:selectOneMenu id="typeConvocUpdate" rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}"
								value="#{nomenclatureController.nomenclature.codeTypeConvocation}">
								<f:selectItems value="#{nomenclatureController.typeConvocationsItems}"/>
							</e:selectOneMenu>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_CONV.IS_FINAL']}" />
							</f:facet>
							<t:selectBooleanCheckbox
								value="#{nomenclatureController.nomenclature.isFinal}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}" />
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{typeDecision.isFinal
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature)}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!typeDecision.isFinal
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature)}" />
						</t:column>

						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:selectBooleanCheckbox
								value="#{nomenclatureController.nomenclature.temoinEnService}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& typeDecision == nomenclatureController.nomenclature}" />
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{typeDecision.temoinEnService
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature)}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!typeDecision.temoinEnService
								&& (nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| typeDecision != nomenclatureController.nomenclature)}" />
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true" styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction
										&& managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.updateAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{typeDecision}"
									property="#{nomenclatureController.nomenclature}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction
									&& managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.deleteAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{typeDecision}"
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
