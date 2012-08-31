<%@include file="../../../_include/_include.jsp"%>
<h:form id="formAddClef">
	<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi" styleClass="tableJustWidthMax">
		<e:text value="#{msgs['CLEF.TITLE.ENTER']}" styleClass="section-smallTitle"/>
		<t:panelGroup>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.addAction}"
				action="#{paramClefFormationController.add}"/>
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				rendered="#{paramClefFormationController.actionEnum.whatAction == paramClefFormationController.actionEnum.updateAction}"
				action="#{paramClefFormationController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}"
				action="#{paramClefFormationController.reset}"/>
		</t:panelGroup>
	</e:panelGrid>
	<t:panelGrid columns="5" styleClass="displayInfo">
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}"
			styleClass="form-field-label-inHeader-validator" for="code"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.IN_USE']}"
			styleClass="form-field-label-inHeader-validator" for="inUse"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.DOMAINE']}"
			styleClass="form-field-label-inHeader-validator" for="doms"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_LIB_LANGUE']}"
			styleClass="form-field-label-inHeader-validator" for="lib"/>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIST_DIPLOME']}"
			styleClass="form-field-label-inHeader-validator" for="dips"/>

		<e:inputText id="code" size="10"
			value="#{paramClefFormationController.cles.ren1ClesAnnuForm.codCles}"/>
		<t:selectOneMenu id="inUse"
			value="#{paramClefFormationController.cles.ren1ClesAnnuForm.temSveCles}">
			<f:selectItems value="#{paramClefFormationController.temEnSveItems}"/>
		</t:selectOneMenu>
		<t:panelGroup rendered="#{paramClefFormationController.actionDom.whatAction != paramClefFormationController.actionDom.updateAction
								&& paramClefFormationController.cles.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm != null}">
			<e:commandButton image="/media/images/update.png" styleClass="espaceIcone"
				action="#{paramClefFormationController.updateDomaines}">
				<t:updateActionListener
					value="#{paramClefFormationController.cles.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm.codDom}"
					property="#{paramClefFormationController.selectDoms}"/>
			</e:commandButton>
			<e:text id="doms"
				value="#{paramClefFormationController.cles.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm.codDom}"/>
		</t:panelGroup>
		<t:panelGrid columns="2"
				rendered="#{paramClefFormationController.actionDom.whatAction == paramClefFormationController.actionDom.updateAction
						|| paramClefFormationController.cles.ren1DomaineAnnuFormPojo.ren1DomaineAnnuForm == null}">
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
		<t:panelGrid columns="3" id="dips">
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
		</t:panelGrid>
	</t:panelGrid>
	<e:message for="formAddClef"/>
</h:form>
