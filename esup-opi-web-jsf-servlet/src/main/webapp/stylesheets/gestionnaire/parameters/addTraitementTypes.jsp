<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['TYP_TRT.TITLE.ADD']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="addTraitementTypesForm">
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
								action="#{nomenclatureController.goSeeAllTypTrt}" />
						</t:panelGroup>
					</t:div>
					<e:dataTable var="typeTraitement" 
							value="#{nomenclatureController.addNomenclatures}"
							styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{typeTraitement.code}" rendered="#{typeTraitement.id != 0}" />
							<e:inputText id="codeTypTrt" value="#{typeTraitement.code}" size="6" maxlength="5"
								rendered="#{typeTraitement.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{typeTraitement.libelle}" rendered="#{typeTraitement.id != 0}" />
							<e:inputText id="libTypTrt" value="#{typeTraitement.libelle}" size="31" maxlength="30"
								rendered="#{typeTraitement.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.SHORT_LIB']}" />
							</f:facet>
							<e:text value="#{typeTraitement.shortLabel}" rendered="#{typeTraitement.id != 0}" />
							<e:inputText id="licTypTrt" value="#{typeTraitement.shortLabel}" size="11" maxlength="10"
								rendered="#{typeTraitement.id == 0}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:selectBooleanCheckbox value="#{typeTraitement.temoinEnService}" rendered="#{typeTraitement.id == 0}" />
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{typeTraitement.id != 0 && typeTraitement.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{typeTraitement.id != 0 && !typeTraitement.temoinEnService}"/>
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
	highlightInputAndSelect('addTraitementTypesForm');
</script>
</e:page>
