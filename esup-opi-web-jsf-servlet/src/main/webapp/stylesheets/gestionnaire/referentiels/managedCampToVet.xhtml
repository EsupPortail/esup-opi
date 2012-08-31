<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['CAMPAGNES.TITLE.MANAGED_COMMISSION']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div id="div_subSectionFirstForm" styleClass="div_subSection">
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<h:form id="campToVetForm">

				<t:div id="blockFormMotClef" styleClass="blockForm">
					<t:div style="width:100%;" 
						rendered="#{trtCmiController.actionEnum.whatAction 
							!= trtCmiController.actionEnum.addAction}">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FIELD_LABEL.ETAPES']}"
								styleClass="section-smallTitle" />
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									action="#{trtCmiController.goBackManagedTrtCmi}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:div rendered="#{trtCmiController.actionEnum.whatAction 
							!= trtCmiController.actionEnum.addAction}">
						<e:dataTable id="trtCmi" var="beanTrtCmi"
							value="#{trtCmiController.allTraitementCmi}"
							styleClass="displayInfo">
							<t:column width="35%">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.ETP']}" />
								</f:facet>
								<e:text value="#{beanTrtCmi.etape.libWebVet}" />
							</t:column>
							<t:column styleClass="col1UnDemi">
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.CAMPAGNES']}" />
								</f:facet>
								<t:dataList var="link"
									value="#{beanTrtCmi.traitementCmi.linkTrtCmiCamp}"
									layout="unorderedList">
									<t:column width="90%">
										<e:text value="#{link.campagne.code}"
											styleClass="span-text-small" />
										<e:text value=" : " styleClass="span-text-small" />
										<e:text value="#{link.campagne.libelle}"
											styleClass="span-text-small" />
									</t:column>
									<t:column width="10%">
										<e:commandButton image="/media/images/cancel.png"
											styleClass="form-button-image" immediate="true"
											action="#{trtCmiController.removeLinkTrtCmiCamp}"
											title="#{msgs['_.BUTTON.DELETE']}"
											rendered="#{not link.campagne.isArchived && 
														link.temoinEnService}">
											<t:updateActionListener value="#{link}"
												property="#{trtCmiController.linkToDel}" />
										</e:commandButton>
									</t:column>
								</t:dataList>
	
							</t:column>
						</e:dataTable>
						
					</t:div>
					<t:div>
	
						<%@include file="_commission/_enterTrtCmiOff.jsp"%>
					</t:div>
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
	highlightInputAndSelect('campToVetForm');
</script>
</e:page>