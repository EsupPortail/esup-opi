<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs"
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PROFIL.TITLE.MANAGED']}" />
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{profilController.actionEnum.whatAction == profilController.actionEnum.deleteAction}">
			<%@include file="_delete/_deleteProfil.jsp"%>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeProfilsForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="Profils"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									immediate="true"
									rendered="#{managedAccess.addAuthorized}"
									action="#{profilController.goEnterProfil}">
									<t:updateActionListener
									value="#{profilController.actionEnum.addAction}"
									property="#{profilController.actionEnum.whatAction}" />
								</e:commandButton>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true"
									action="#{managedAccess.goDisplayFunction}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="beanProfil" value="#{profilController.beanProfile}"
						styleClass="displayInfo" alternateColors="false">
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{beanProfil.profile.code}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{beanProfil.profile.libelle}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}" />
							</f:facet>
							<e:selectOneMenu id="regimeInscription" readonly="true"
								value="#{beanProfil.profile.codeRI}">
								<f:selectItems value="#{nomenclatureController.regimeInscriptionsItems}"/>
							</e:selectOneMenu>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.NB_USERS']}" />
							</f:facet>
							<e:text value="#{beanProfil.nbGest}" />
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif" 
								rendered="#{beanProfil.profile.temoinEnService}"/>
							<t:graphicImage url="/media/images/check0.gif" 
								rendered="#{!beanProfil.profile.temoinEnService}"/>
						</t:column>
						<t:column styleClass="blockButton">
							<e:commandButton image="/media/images/magnifier.png" 
								styleClass="form-button-image"
								immediate="true"
								action="#{profilController.goSeeProfil}"
								title="#{msgs['_.BUTTON.DISPLAY']}">
								<t:updateActionListener
									value="#{beanProfil.profile}"
									property="#{profilController.profil}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="blockButton">
							<e:commandButton image="/media/images/update.png"
								immediate="true"
								rendered="#{managedAccess.updateAuthorized}"
								action="#{profilController.goEnterProfil}"
								styleClass="form-button-image" title="#{msgs['BUTTON.UPDATE']}" >
								<t:updateActionListener
									value="#{profilController.actionEnum.updateAction}"
									property="#{profilController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{beanProfil.profile}"
									property="#{profilController.profil}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="blockButton">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image"
								immediate="true"
								rendered="#{managedAccess.deleteAuthorized 
									&& beanProfil.nbGest == 0}"
								title="#{msgs['_.BUTTON.DELETE']}" >
								<t:updateActionListener
									value="#{profilController.actionEnum.deleteAction}"
									property="#{profilController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{beanProfil.profile}"
									property="#{profilController.profil}" />
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

