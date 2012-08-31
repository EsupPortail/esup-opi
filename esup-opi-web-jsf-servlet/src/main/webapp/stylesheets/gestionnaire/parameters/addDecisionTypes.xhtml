<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_DECISION.TITLE.ADD']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="addDecisionTypesForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								action="#{nomenclatureController.add}" />
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								immediate="true"
								action="#{nomenclatureController.goSeeAllTypDecision}" />
						</t:panelGroup>
					</t:div>
					<e:dataTable var="typeDecision" 
							value="#{nomenclatureController.addNomenclatures}"
							styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{typeDecision.code}" rendered="#{typeDecision.id != 0}" />
							<e:inputText id="codeTypDecision" value="#{typeDecision.code}" size="6" maxlength="5"
								rendered="#{typeDecision.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_DECISION.CODE_APOGEE']}" />
							</f:facet>
							<e:text value="#{typeDecision.codeApogee}" 
								rendered="#{typeDecision.id != 0}"/>
							<e:selectOneMenu id="codeApogee" value="#{typeDecision.codeApogee}"
								rendered="#{typeDecision.id == 0}">
								<f:selectItem itemLabel="F" itemValue="F"/>
								<f:selectItem itemLabel="D" itemValue="D"/>
								<f:selectItem itemLabel="E" itemValue="E"/>
							</e:selectOneMenu>
						</t:column>						
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{typeDecision.libelle}" rendered="#{typeDecision.id != 0}" />
							<e:inputText id="libTypDecision" value="#{typeDecision.libelle}" size="31" maxlength="30"
								rendered="#{typeDecision.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.SHORT_LIB']}" />
							</f:facet>
							<e:text value="#{typeDecision.shortLabel}" rendered="#{typeDecision.id != 0}" />
							<e:inputText id="licTypDecision" value="#{typeDecision.shortLabel}" size="11" maxlength="10"
								rendered="#{typeDecision.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_DECISION.CONVOCATION_TYPE']}" />
							</f:facet>
							<e:selectOneMenu id="typeConvocation" readonly="#{typeDecision.id != 0}"
								value="#{typeDecision.codeTypeConvocation}">
								<f:selectItems value="#{nomenclatureController.typeConvocationsItems}"/>
							</e:selectOneMenu>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['TYP_CONV.IS_FINAL']}" />
							</f:facet>
							<t:selectBooleanCheckbox value="#{typeDecision.isFinal}" rendered="#{typeDecision.id == 0}" />
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{typeDecision.id != 0 && typeDecision.isFinal}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{typeDecision.id != 0 && !typeDecision.isFinal}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:selectBooleanCheckbox value="#{typeDecision.temoinEnService}" rendered="#{typeDecision.id == 0}" />
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{typeDecision.id != 0 && typeDecision.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{typeDecision.id != 0 && !typeDecision.temoinEnService}"/>
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
	highlightInputAndSelect('addDecisionTypesForm');
</script>
</e:page>
