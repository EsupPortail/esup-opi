<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['GROUPE.MANAGED']}"/>
	<e:messages styleClass="messageForUser"/>
	<t:panelGroup id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.deleteAction}">
		<%@include file="_delete/_deleteGrpTypDip.jsp"%>
	</t:panelGroup>
	<h:form id="seeGrpTypDipForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{paramGrpTypDipController.goAddGrp}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}"/>
		</t:div>
		<t:div styleClass="twoGroupsButton"
			rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.updateAction}">
			<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
				action="#{paramGrpTypDipController.update}"/>
			<e:commandButton value="#{msgs['_.BUTTON.CANCEL']}" immediate="true"
				action="#{paramGrpTypDipController.reset}"/>
		</t:div>
		<e:dataTable var="groupeSelect"
			value="#{paramGrpTypDipController.grpTypDip}"
			styleClass="paginatorData" alternateColors="true">
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.CODE']}"/>
				</f:facet>
				<e:text value="#{groupeSelect.codGrpTpd}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}"/>
				</f:facet>
				<e:text value="#{groupeSelect.libGrpTpd}"
					rendered="#{paramGrpTypDipController.actionEnum.whatAction != paramGrpTypDipController.actionEnum.updateAction
						|| groupeSelect != paramGrpTypDipController.groupe}"/>
				<e:inputText value="#{paramGrpTypDipController.groupe.libGrpTpd}"
					size="60"
					rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.updateAction
						&& groupeSelect == paramGrpTypDipController.groupe}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}"/>
				</f:facet>
				<t:selectOneMenu value="#{paramGrpTypDipController.groupe.temEnSveGrpTpd}"
					rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.updateAction
						&& groupeSelect.codGrpTpd == paramGrpTypDipController.groupe.codGrpTpd}">
						<f:selectItems value="#{paramGrpTypDipController.temEnSveItems}"/>
				</t:selectOneMenu>
				<t:graphicImage url="/media/images/check2.gif"
					rendered="#{groupeSelect.temEnSveGrpTpd == 'O'
					&& (paramGrpTypDipController.actionEnum.whatAction != paramGrpTypDipController.actionEnum.updateAction
						|| groupeSelect.codGrpTpd != paramGrpTypDipController.groupe.codGrpTpd)}"/>
				<t:graphicImage url="/media/images/check0.gif"
					rendered="#{groupeSelect.temEnSveGrpTpd == 'N'
					&& (paramGrpTypDipController.actionEnum.whatAction != paramGrpTypDipController.actionEnum.updateAction
						|| groupeSelect.codGrpTpd != paramGrpTypDipController.groupe.codGrpTpd)}"/>
			</t:column>
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.TYPE_DIPLOME']}"/>
				</f:facet>
				<t:dataList var="corresps"
					value="#{groupeSelect.ren1GrpTypDipCorresps}"
					rendered="#{paramGrpTypDipController.actionEnum.whatAction != paramGrpTypDipController.actionEnum.updateAction
						|| groupeSelect.codGrpTpd != paramGrpTypDipController.groupe.codGrpTpd}">
					<e:text value="#{corresps.codTpdEtb} (#{paramGrpTypDipController.mapCodTpdEtbLib[corresps.codTpdEtb]})"/>
				</t:dataList>
				<e:panelGrid columns="3" id="dips"
						rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.updateAction
						&& groupeSelect.codGrpTpd == paramGrpTypDipController.groupe.codGrpTpd}">
					<t:selectManyMenu styleClass="manyMenu" 
						value="#{paramGrpTypDipController.selectDipsADI}">
						<f:selectItems value="#{paramGrpTypDipController.allDipsItems}"/>
					</t:selectManyMenu>
					<t:panelGrid columns="1">
						<e:commandButton image="/media/images/fleche_gauche.gif"
							styleClass="icone"
							action="#{paramGrpTypDipController.suppDipsItems}"/>
						<e:commandButton image="/media/images/fleche_droite.gif"
							styleClass="icone"
							action="#{paramGrpTypDipController.ajouDipsItems}"/>
					</t:panelGrid>
					<t:selectManyMenu styleClass="manyMenu"
						value="#{paramGrpTypDipController.selectDipsDI}">
						<f:selectItems value="#{paramGrpTypDipController.dipsItems}"/>
					</t:selectManyMenu>
				</e:panelGrid>
			</t:column>
			<t:column styleClass="buttonTD"
				rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{paramGrpTypDipController.actionEnum.updateAction}"
						property="#{paramGrpTypDipController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{groupeSelect}"
						property="#{paramGrpTypDipController.groupe}"/>
				</e:commandButton>
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{paramGrpTypDipController.actionEnum.deleteAction}"
						property="#{paramGrpTypDipController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{groupeSelect}"
						property="#{paramGrpTypDipController.groupe}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		</h:form>
		<t:panelGroup id="addGrp"
			rendered="#{paramGrpTypDipController.actionEnum.whatAction == paramGrpTypDipController.actionEnum.addAction}">
			<%@include file="_add/_enterGrpTypDip.jsp"%>
		</t:panelGroup>
	
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		<%--
			highlightInputAndSelect('seeGrpTypDipForm');
		--%>
	</script>
</e:page>
