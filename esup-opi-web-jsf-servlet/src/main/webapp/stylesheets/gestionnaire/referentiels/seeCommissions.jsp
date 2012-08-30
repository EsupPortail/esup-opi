<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['COMMISSION.TITLE.LIST']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionDelete" styleClass="div_subSection"
			rendered="#{commissionController.actionEnum.whatAction == commissionController.actionEnum.deleteAction}">
			<%@include file="_commission/_deleteCmi.jsp"%>
		</t:div>


		<t:div id="blockFormSearch" styleClass="blockForm">
			<h:form id="lookForCmiForm" styleClass="opiR1_form">
				<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
					styleClass="tableJustWidthMax">
					<e:text value="#{msgs['FIELD_LABEL.LOOK_FOR.COM']}"
						styleClass="section-smallTitle" />
					<t:panelGroup>
						<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
							action="#{paginatorCommission.lookForCommissions}" />
					</t:panelGroup>
				</e:panelGrid>
				<e:panelGrid styleClass="tableWidthMax" columns="4"
					columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.CODE']}" for="code" />
					</t:panelGroup>
					<e:inputText id="code"
						value="#{commissionController.paginator.commissionRechPojo.code}" />

					<t:panelGroup>
						<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}"
							for="libelle" />
					</t:panelGroup>
					<e:inputText id="libelle"
						value="#{commissionController.paginator.commissionRechPojo.libelle}" />
				</e:panelGrid>
			</h:form>
		</t:div>

		<t:htmlTag value="br" />



		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="seeCommissionForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfo" styleClass="blockForm">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSIONS']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									rendered="#{managedAccess.addAuthorized}"
									action="#{commissionController.goAddCmi}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{managedAccess.goDisplayFunction}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>

					<e:dataTable id="dataCommission" rowIndexVar="variable"
						value="#{paginatorCommission.visibleItems}"
						var="commissionPojo" styleClass="paginatorData" alternateColors="true">
						<f:facet name="header">
							<h:panelGroup>
								<e:paginator id="paginatorCmi"
									paginator="#{paginatorCommission}"
									itemsName="#{msgs['COMMISSIONS']}"
									onchange="javascript:{simulateLinkClick('seeCommissionForm:dataCommission:submitPageSize');}" />
								<e:commandButton id="submitPageSize"
									action="#{paginatorCommission.forceReload}" />
							</h:panelGroup>
						</f:facet>
						<t:column width="20%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{commissionPojo.commission.code}" />
						</t:column>
						<t:column width="64%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{commissionPojo.commission.libelle}" />
						</t:column>
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{commissionPojo.commission.temoinEnService}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!commissionPojo.commission.temoinEnService}" />
						</t:column>
						<t:column width="10%">
							<f:facet name="header">
								<t:popup styleClass="containerPopup" style="width:400px;"
									closePopupOnExitingElement="true"
									closePopupOnExitingPopup="true" displayAtDistanceX="-200"
									displayAtDistanceY="0">
									<t:graphicImage url="/media/images/informationSmall.png"
										style="cursor:pointer;" id="infoAnneeUni" />

									<f:facet name="popup">
										<h:panelGroup>
											<h:panelGrid columns="1">
												<t:outputText styleClass="titrePopupAide"
													value="#{msgs['COMMISSIONS']}" />
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_green.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['COMMISSION.INFO.ALL_ACTIVE_ETP']}" />
												</t:panelGroup>
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_orange.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['COMMISSION.INFO.SOME_INACTIVE_ETP']}" />
												</t:panelGroup>
												<t:panelGroup>
													<t:graphicImage url="/media/images/flag_red.png" />
													<t:outputText styleClass="libellesAide"
														value="#{msgs['COMMISSION.INFO.NO_ACTIVE_ETP']}" />
												</t:panelGroup>
											</h:panelGrid>
										</h:panelGroup>
									</f:facet>
								</t:popup>
							</f:facet>
							<t:panelGroup>
								<t:graphicImage url="/media/images/flag_green.png"
									rendered="#{!commissionPojo.flagWithoutTrtActive && !commissionPojo.flagWithSomeTrtInactive}"
									style="cursor:pointer;" id="allActiveEtp" />
								<t:graphicImage url="/media/images/flag_orange.png"
									rendered="#{commissionPojo.flagWithSomeTrtInactive}"
									style="cursor:pointer;" id="someInactiveEtp" />
								<t:graphicImage url="/media/images/flag_red.png"
									rendered="#{commissionPojo.flagWithoutTrtActive}"
									style="cursor:pointer;" id="noActiveEtp" />
		
							</t:panelGroup>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{commissionController.goSeeOneCmi}"
								title="#{msgs['_.BUTTON.DISPLAY']}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.readAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commissionPojo.commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true" styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								action="#{commissionController.goUpdateCmi}"
								rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.updateAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commissionPojo.commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.deleteAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commissionPojo.commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
					</e:dataTable>

				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="blockFormInfoWithoutTrt" styleClass="blockForm"
					rendered="#{not empty commissionController.currentGest.rightOnCmi || commissionController.currentGest.codeCge != null}">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['COMMISSIONS.WITHOUT_TRT']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									rendered="#{managedAccess.addAuthorized}"
									action="#{commissionController.goAddCmi}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{managedAccess.goDisplayFunction}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<e:dataTable var="commission"
						value="#{commissionController.commissionsItemsWithoutTrt}"
						styleClass="displayInfo" alternateColors="false">
						<t:column width="20%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.CODE']}" />
							</f:facet>
							<e:text value="#{commission.code}" />
						</t:column>
						<t:column width="64%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']}" />
							</f:facet>
							<e:text value="#{commission.libelle}" />
						</t:column>
						<t:column width="10%">
							<f:facet name="header">
								<t:outputText value="#{msgs['FIELD_LABEL.IN_USE']}" />
							</f:facet>
							<t:graphicImage url="/media/images/check2.gif"
								rendered="#{commission.temoinEnService}" />
							<t:graphicImage url="/media/images/check0.gif"
								rendered="#{!commission.temoinEnService}" />
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{commissionController.goSeeOneCmi}"
								title="#{msgs['_.BUTTON.DISPLAY']}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.readAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/update.png"
								immediate="true" styleClass="form-button-image"
								title="#{msgs['BUTTON.UPDATE']}"
								action="#{commissionController.goUpdateCmi}"
								rendered="#{managedAccess.updateAuthorized}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.updateAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
						<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/cancel.png"
								styleClass="form-button-image" immediate="true"
								title="#{msgs['_.BUTTON.DELETE']}"
								rendered="#{managedAccess.deleteAuthorized}">
								<t:updateActionListener
									value="#{commissionController.actionEnum.deleteAction}"
									property="#{commissionController.actionEnum.whatAction}" />
								<t:updateActionListener value="#{commission}"
									property="#{commissionController.commission}" />
							</e:commandButton>
						</t:column>
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
	hideElement('seeCommissionForm:dataCommission:submitPageSize');
</script>
</e:page>
