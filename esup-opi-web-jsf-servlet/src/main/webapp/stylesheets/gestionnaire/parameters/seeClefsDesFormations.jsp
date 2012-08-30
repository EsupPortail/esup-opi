<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['CLEF.MANAGED']}"/>
	<e:messages styleClass="messageForUser"/>
	<t:panelGroup id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.deleteAction}">
		<%@include file="_delete/_deleteClefsDesFormations.jsp"%>
	</t:panelGroup>
	<h:form id="seeClefsForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{paramClefFormationController.goAddKeys}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}"/>
		</t:div>
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction}">
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				action="#{paramClefFormationController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
				action="#{paramClefFormationController.reset}"/>
		</t:div>
		<e:dataTable var="clesSelect"
			value="#{paramClefFormationController.listCles}"
			styleClass="paginatorData" alternateColors="true">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.CODE']}"/>
				</f:facet>
				<e:text value="#{clesSelect.ren1ClesAnnuForm.codCles}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}"/>
				</f:facet>
				<t:selectOneMenu value="#{paramClefFormationController.cles.ren1ClesAnnuForm.temSveCles}"
					rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction
						&& clesSelect.ren1ClesAnnuForm.codCles == paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
						<f:selectItems value="#{paramClefFormationController.temEnSveItems}"/>
				</t:selectOneMenu>
				<t:graphicImage url="/media/images/check2.gif"
					rendered="#{clesSelect.ren1ClesAnnuForm.temSveCles == 'O'
					&& (paramClefFormationController.actionEnum.whatAction != paramClefFormationController.actionEnum.updateAction
						|| clesSelect.ren1ClesAnnuForm.codCles != paramClefFormationController.cles.ren1ClesAnnuForm.codCles)}"/>
				<t:graphicImage url="/media/images/check0.gif"
					rendered="#{clesSelect.ren1ClesAnnuForm.temSveCles == 'N'
					&& (paramClefFormationController.actionEnum.whatAction != paramClefFormationController.actionEnum.updateAction
						|| clesSelect.ren1ClesAnnuForm.codCles != paramClefFormationController.cles.ren1ClesAnnuForm.codCles)}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.DOMAINE']}"/>
				</f:facet>
				<t:panelGroup 
						rendered="#{paramClefFormationController.actionEnum.whatAction != paramClefFormationController.actionEnum.updateAction
						|| clesSelect.ren1ClesAnnuForm.codCles != paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<e:text id="domsText"
							value="#{clesSelect.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm.codDom}"/>
				</t:panelGroup>
				<t:panelGroup
						rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction
						&& clesSelect.ren1ClesAnnuForm.codCles == paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<t:panelGroup rendered="#{paramClefFormationController.actionDom.whatAction != paramClefFormationController.actionDom.updateAction
											&& clesSelect.ren1DomaineAnnuFormPojo != null}">
						<e:commandButton image="/media/images/update.png" styleClass="espaceIcone"
							action="#{paramClefFormationController.updateDomaines}">
							<t:updateActionListener
								value="#{paramClefFormationController.cles.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm.codDom}"
								property="#{paramClefFormationController.selectDoms}"/>
							<t:updateActionListener
								value="#{clesSelect}"
								property="#{paramClefFormationController.cles}"/>
						</e:commandButton>
						<e:text id="domsUpdate"
							value="#{clesSelect.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm.codDom}"/>
					</t:panelGroup>
					<t:panelGrid columns="2" 
							rendered="#{paramClefFormationController.actionDom.whatAction == paramClefFormationController.actionDom.updateAction
									|| clesSelect.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm == null}">
						<t:selectOneMenu id="inDoms"
							value="#{paramClefFormationController.selectDoms}">
							<f:selectItems value="#{paramClefFormationController.itemDomaine}"/>
						</t:selectOneMenu>
						<t:div styleClass="twoGroupsButton">
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								action="#{paramClefFormationController.validDomaines}"/>
							<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
								action="#{paramClefFormationController.annulDomaines}"/>
						</t:div>
					</t:panelGrid>
				</t:panelGroup>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIST_LIB_LANGUE']}"/>
				</f:facet>
				<t:dataList var="langueCleSelect"
					value="#{clesSelect.ren1Cles2AnnuForm}"
					rendered="#{paramClefFormationController.actionEnum.whatAction != paramClefFormationController.actionEnum.updateAction
						|| clesSelect.ren1ClesAnnuForm.codCles != paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<e:text value="#{langueCleSelect.libCles} (#{langueCleSelect.id.codLang})"/>
				</t:dataList>
				<t:panelGroup
						rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction
						&& clesSelect.ren1ClesAnnuForm.codCles == paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<t:panelGroup rendered="#{paramClefFormationController.actionLang.whatAction != paramClefFormationController.actionLang.addAction}">
						<t:dataList var="langueCleSelect" value="#{paramClefFormationController.cles.ren1Cles2AnnuForm}">
							
							<t:panelGrid columns="3" 
									rendered="#{paramClefFormationController.actionLang.whatAction == paramClefFormationController.actionLang.emptyAction
											|| (paramClefFormationController.actionLang.whatAction == paramClefFormationController.actionLang.updateAction
											&& paramClefFormationController.langueSelected != langueCleSelect.id.codLang)}">
								<e:commandButton image="/media/images/update.png" styleClass="espaceIcone"
									action="#{paramClefFormationController.updateLangLib}">
									<t:updateActionListener
										value="#{langueCleSelect.id.codLang}"
										property="#{paramClefFormationController.langueSelected}"/>
									<t:updateActionListener
										value="#{langueCleSelect.libCles}"
										property="#{paramClefFormationController.libSaisi}"/>
								</e:commandButton>
								<e:commandButton image="/media/images/cancel.png" styleClass="espaceIcone"
									action="#{paramClefFormationController.suppLangLib}">
									<t:updateActionListener
										value="#{langueCleSelect.id.codLang}"
										property="#{paramClefFormationController.langueSelected}"/>
								</e:commandButton>
								<e:text styleClass="textLangLib" value="#{langueCleSelect.libCles} (#{langueCleSelect.id.codLang})"/>
							</t:panelGrid>
							
							<t:panelGrid columns="3" 
									rendered="#{paramClefFormationController.actionLang.whatAction == paramClefFormationController.actionLang.updateAction
											&& paramClefFormationController.langueSelected == langueCleSelect.id.codLang}">
								<e:outputLabel value="#{msgs['FIELD_LABEL.LANGUE']}"
									styleClass="form-field-label-inHeader-validator" for="langue"/>
								<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
									styleClass="form-field-label-inHeader-validator" for="libele"/>
								<e:outputLabel value=""
									styleClass="form-field-label-inHeader-validator" for="bouton"/>
								
								<e:text styleClass="textLangLib" value="#{langueCleSelect.id.codLang}"/>
								<e:inputText id="ModifLib" size="50" value="#{paramClefFormationController.libSaisi}"/>
								<t:div styleClass="twoGroupsButton">
									<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
										action="#{paramClefFormationController.validModLangLib}"/>
									<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
										action="#{paramClefFormationController.annulLangLib}"/>
								</t:div>
							</t:panelGrid>
							
						</t:dataList>
						<e:commandButton image="/media/images/add.png" styleClass="espaceIcone"
								rendered="#{not empty paramClefFormationController.allLangue}"
								action="#{paramClefFormationController.addLangLib}"/>
					</t:panelGroup>
					<t:panelGroup rendered="#{paramClefFormationController.actionLang.whatAction == paramClefFormationController.actionLang.addAction}">
						<t:panelGrid columns="3">
							<e:outputLabel value="#{msgs['FIELD_LABEL.LANGUE']}"
								styleClass="form-field-label-inHeader-validator" for="langue"/>
							<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
								styleClass="form-field-label-inHeader-validator" for="libele"/>
							<e:outputLabel value=""
								styleClass="form-field-label-inHeader-validator" for="bouton"/>
							
							<t:selectOneMenu id="choixLang"
								value="#{paramClefFormationController.langueSelected}">
								<f:selectItems value="#{paramClefFormationController.allLangue}"/>
							</t:selectOneMenu>
							<e:inputText id="choixlib" size="50"
								value="#{paramClefFormationController.libSaisi}"/>
							<t:div styleClass="twoGroupsButton">
								<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
									action="#{paramClefFormationController.validAddLangLib}"/>
								<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
									action="#{paramClefFormationController.annulLangLib}"/>
							</t:div>
						</t:panelGrid>
					</t:panelGroup>
				</t:panelGroup>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIST_DIPLOME']}"/>
				</f:facet>
				<t:dataList var="diplome"
					value="#{clesSelect.ren1ClesDiplomeAnnuForm}"
					rendered="#{paramClefFormationController.actionEnum.whatAction != paramClefFormationController.actionEnum.updateAction
						|| clesSelect.ren1ClesAnnuForm.codCles != paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<e:text value="#{diplome.codDip} (#{paramClefFormationController.mapCodeDipLib[diplome.codDip]})"/>
				</t:dataList>
				<e:panelGrid columns="3" id="dips"
						rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction
						&& clesSelect.ren1ClesAnnuForm.codCles == paramClefFormationController.cles.ren1ClesAnnuForm.codCles}">
					<t:selectManyMenu styleClass="manyMenu" 
						value="#{paramClefFormationController.selectDipsADI}">
						<f:selectItems value="#{paramClefFormationController.allDipsItems}"/>
					</t:selectManyMenu>
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/fleche_gauche.gif"
							styleClass="icone"
							action="#{paramClefFormationController.suppDipsItems}"/>
						<e:commandButton image="/media/images/fleche_droite.gif"
							styleClass="icone"
							action="#{paramClefFormationController.ajouDipsItems}"/>
					</t:panelGrid>
					<t:selectManyMenu styleClass="manyMenu"
						value="#{paramClefFormationController.selectDipsDI}">
						<f:selectItems value="#{paramClefFormationController.dipsItems}"/>
					</t:selectManyMenu>
				</e:panelGrid>
			</t:column>
			<t:column styleClass="buttonTD"
				rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{paramClefFormationController.actionEnum.updateAction}"
						property="#{paramClefFormationController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{clesSelect}"
						property="#{paramClefFormationController.cles}"/>
				</e:commandButton>
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{paramClefFormationController.actionEnum.deleteAction}"
						property="#{paramClefFormationController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{clesSelect}"
						property="#{paramClefFormationController.cles}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		</h:form>
		<t:panelGroup id="addClef"
			rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.addAction}">
			<%@include file="_add/_enterClefsDesFormations.jsp"%>
		</t:panelGroup>
	
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		<%--
			highlightInputAndSelect('seeClefsForm');
		--%>
	</script>
</e:page>
