<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['AUTO_LP.MANAGED']}"/>
	<t:div styleClass="messageForUser">
		<e:messages />
	</t:div>
	
	<t:panelGroup id="div_subSectionDelete" styleClass="div_subSection"
		rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.deleteAction}">
		<%@include file="_delete/_deleteAutoLp.jsp"%>
	</t:panelGroup>
	
	<h:form id="seeAutoLpForm" styleClass="div_subSection">
		<t:div styleClass="twoGroupsButton"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.emptyAction}">
			<e:commandButton value="#{msgs['_.BUTTON.ADD']}" immediate="true"
				rendered="#{managedAccess.addAuthorized}"
				action="#{autoLpController.goAddAutoLp}"/>
			<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
				action="#{managedAccess.goDisplayFunction}"/>
		</t:div>
		
		<e:dataTable var="autoLpSelect" value="#{autoLpController.listAutoLp}" 
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.emptyAction
						|| autoLpController.actionEnum.whatAction == autoLpController.actionEnum.deleteAction}"
			styleClass="paginatorData" alternateColors="true">
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}"/>
				</f:facet>
				<e:text value="#{autoLpSelect.autoLp.libelle}"/>
			</t:column>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
				</f:facet>
				<t:graphicImage url="/media/images/check2.gif" 
					rendered="#{autoLpSelect.autoLp.temoinEnService}"/>
				<t:graphicImage url="/media/images/check0.gif" 
					rendered="#{!autoLpSelect.autoLp.temoinEnService}"/>
			</t:column>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.MAIL']}"/>
				</f:facet>
				<e:text value="#{autoLpSelect.autoLp.mail}"/>
			</t:column>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.LIST_CGE_COMMISSION_VET']}"/>
				</f:facet>
				
				<e:text value="#{autoLpSelect.libelleCge}" rendered="#{autoLpSelect.autoLp.codeCge != null}"/>
			
				<t:dataTable var="commissionPojo" value="#{autoLpSelect.listCommissionPojo}"
							rendered="#{not empty autoLpSelect.listCommissionPojo}">
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.CODE']}"/>
						</f:facet>
						<e:text value="#{commissionPojo.commission.code}"/>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.CORRESPONDANT']}"/>
						</f:facet>
						<e:text value="#{commissionPojo.contactCommission.corresponding}"/>
					</t:column>
				</t:dataTable>
				
				<t:dataTable var="vet" value="#{autoLpSelect.listVetAutoLpPojo}"
							rendered="#{not empty autoLpSelect.listVetAutoLpPojo}">
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.CODE_VET']}"/>
						</f:facet>
						<e:text value="#{vet.vetAutoLp.codEtp}"/>
					</t:column>
					<t:column>
						<f:facet name="header">
							<t:outputText value="#{msgs['FIELD_LABEL.LIB_VET']}"/>
						</f:facet>
						<e:text value="#{vet.libWebVet}"/>
					</t:column>
				</t:dataTable>
			</t:column>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.TYPE_DECISION_LP']}"/>
				</f:facet>
				<e:text value="#{autoLpSelect.autoLp.typeDecisionDeLP.libelle}"/>
			</t:column>
			
			<t:column>
				<f:facet name="header">
					<t:outputText value="#{msgs['FIELD_LABEL.TYPE_DECISION_LC']}"/>
				</f:facet>
				
				<t:dataList var="typeDec" value="#{autoLpSelect.autoLp.listTypeOfDecision}">
					<e:text value="#{typeDec.libelle}"/>
				</t:dataList>
			</t:column>
			
			<t:column styleClass="buttonTD"
				rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.emptyAction
							&& managedAccess.updateAuthorized}">
				<e:commandButton image="/media/images/update.png"
					rendered="#{managedAccess.updateAuthorized && autoLpSelect.right}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['BUTTON.UPDATE']}">
					<t:updateActionListener
						value="#{autoLpController.actionEnum.updateAction}"
						property="#{autoLpController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{autoLpSelect}"
						property="#{autoLpController.autoListPrincipale}"/>
				</e:commandButton>
				
				<e:commandButton image="/media/images/cancel.png"
					rendered="#{managedAccess.deleteAuthorized && autoLpSelect.right}"
					styleClass="form-button-image" immediate="true"
					title="#{msgs['_.BUTTON.DELETE']}">
					<t:updateActionListener
						value="#{autoLpController.actionEnum.deleteAction}"
						property="#{autoLpController.actionEnum.whatAction}"/>
					<t:updateActionListener value="#{autoLpSelect}"
						property="#{autoLpController.autoListPrincipale}"/>
				</e:commandButton>
			</t:column>
		</e:dataTable>
		
		<t:panelGroup id="addAutoLp"
			rendered="#{autoLpController.actionEnum.whatAction == autoLpController.actionEnum.addAction
						|| autoLpController.actionEnum.whatAction == autoLpController.actionEnum.updateAction}">
			<%@include file="_add/_enterAutoLp.jsp"%>
		</t:panelGroup>
	</h:form>
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
		hideElement('seeAutoLpForm:selectChoix');
	</script>
</e:page>
