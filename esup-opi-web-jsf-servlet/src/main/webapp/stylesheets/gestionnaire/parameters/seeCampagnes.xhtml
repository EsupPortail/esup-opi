<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CAMPAGNES.TITLE.MANAGED']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionEnter" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.addAction}">
			<%@include file="_campagne/_enterCampagnes.jsp"%>
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteNomenclature.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeCampagnesForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormCampagnes" styleClass="blockForm">
					<t:div styleClass="blockButton"
						rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
								 immediate="true" action="#{nomenclatureController.goAddCampagne}"
								 rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}"/>
							<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
								immediate="true" action="#{managedAccess.goDisplayFunction}"
								 rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction}"/>
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

					<e:dataTable var="nomenclaturePojo" value="#{nomenclatureController.campagnes}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.code}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ANNEE_UNI']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.codAnu}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LONG_LIB']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.libelle}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature}"/>
							<e:inputText id="libelleLong"
								value="#{nomenclatureController.nomenclature.libelle}" size="50" maxlength="500" 
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.DATE_DEBUT']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.dateDebCamp}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature}">
								<f:convertDateTime pattern="dd/MM/yyyy"
									timeZone="#{sessionController.timezone}" />
							</e:text>
							<e:inputText id="dateDeb" size="10" maxlength="8"
								value="#{nomenclatureController.nomenclature.dateDebCamp}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}">
								<f:convertDateTime pattern="ddMMyyyy"
									timeZone="#{sessionController.timezone}" />
								<f:validateLength minimum="8"/>
							</e:inputText>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.DATE_FIN']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.nomenclature.dateFinCamp}"
								rendered="#{nomenclatureController.actionEnum.whatAction != nomenclatureController.actionEnum.updateAction
									|| nomenclaturePojo.nomenclature != nomenclatureController.nomenclature}">
								<f:convertDateTime pattern="dd/MM/yyyy"
									timeZone="#{sessionController.timezone}" />
							</e:text>
							<e:inputText id="dateFin" size="10" maxlength="8"
								value="#{nomenclatureController.nomenclature.dateFinCamp}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.updateAction
									&& nomenclaturePojo.nomenclature == nomenclatureController.nomenclature}">
								<f:convertDateTime pattern="ddMMyyyy"
									timeZone="#{sessionController.timezone}" />
								<f:validateLength minimum="8"/>
							</e:inputText>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.temoinEnService}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" />
							</f:facet>
							<e:text value="#{nomenclaturePojo.regimeInscription.label}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.ARCHIVE.LABEL']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{nomenclaturePojo.nomenclature.isArchived}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!nomenclaturePojo.nomenclature.isArchived}"/>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								rendered="#{nomenclatureController.actionEnum.whatAction == nomenclatureController.actionEnum.emptyAction
										&& managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.updateAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{nomenclaturePojo.nomenclature}"
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
