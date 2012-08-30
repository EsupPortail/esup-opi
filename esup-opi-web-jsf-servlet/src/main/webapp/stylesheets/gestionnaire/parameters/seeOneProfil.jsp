<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs"
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PROFIL.TITLE.SEE']}" />
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="updateProfilForm">
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<t:panelGroup />
							<t:panelGroup>
								<e:commandButton value="#{msgs['BUTTON.UPDATE']}"
									immediate="true"
									action="#{profilController.goEnterProfil}">
									<t:updateActionListener
										value="#{profilController.actionEnum.updateAction}"
										property="#{profilController.actionEnum.whatAction}" />
								</e:commandButton>
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" 
									action="#{profilController.goSeeAllProfil}"
									immediate="true"/>
							</t:panelGroup>
						</e:panelGrid>

					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="2"
						columnClasses="col1UnDemi,col2UnDemi">
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}  " for="code" />
							<e:text id="code"  
								value="#{profilController.profil.code}"/>
						</t:panelGroup>
						<t:panelGroup>
							<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}  " for="libelle" />
							<e:text id="libelle"
								value="#{profilController.profil.libelle}"/>
						</t:panelGroup>
					</e:panelGrid>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormFonction" styleClass="blockForm">
					<e:dataTable id="functionData"  var="beanAccess" value="#{profilController.allAccess }"
						styleClass="paginatorData" alternateColors="true"
						rowIndexVar="variable" >
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['DOMAIN']}" />
							</f:facet>
							<e:text value="#{beanAccess.traitement.domain.libelle}" rendered="#{beanAccess.isFunction}"/>
							<e:text value="#{beanAccess.traitement.libelle}" rendered="#{!beanAccess.isFunction}"/>
						</t:column>
						<t:column>
							<f:facet name="header">
								<t:outputText value="#{msgs['FUNCTION']}" />
							</f:facet>
							<e:text value="#{beanAccess.traitement.libelle }" rendered="#{beanAccess.isFunction}"/>
						</t:column>
						<t:columns var="codeAccess" value="#{accessRController.codeAccess}" style="width:5%;">
							<f:facet name="header">
								<t:outputText value="#{accessRController.accessTypes[codeAccess].libelle} " />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{beanAccess.theDroits[accessRController.accessTypes[codeAccess]] != null
									&& beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}"/>
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{beanAccess.theDroits[accessRController.accessTypes[codeAccess]] != null
									&& !beanAccess.theDroits[accessRController.accessTypes[codeAccess]]}"/>
						</t:columns>
					</e:dataTable>
				</t:div>
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
