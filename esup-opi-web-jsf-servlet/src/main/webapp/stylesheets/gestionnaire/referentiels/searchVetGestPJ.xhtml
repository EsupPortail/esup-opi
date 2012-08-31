<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" menuItem="accueil"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['PJ.GEST_PJ_FOR_VET']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<t:div styleClass="maxDivTextRight">
			<t:panelGroup>
				<e:text value="#{msgs['INFO.CHAMP']}" />
				<t:outputText value="*" styleClass="etoileForChpObli" />
			</t:panelGroup>
		</t:div>
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="searchVersEtpForm">
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="div_subSection" styleClass="div_subSection"
					rendered="#{!etapeController.rightOneEtp || (etapeController.rightOneEtp && etapeController.rightOnCge)}">
					<t:div id="blockFormInfo" styleClass="blockForm">
						<t:div styleClass="blockButton">
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.SEARCH']}"
									action="#{etapeController.paginator.lookForVets}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{etapeController.goBackSearchEtape}"/>
							</t:panelGroup>
						</t:div>
						
							<e:panelGrid styleClass="tableWidthMax" columns="4"
								columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart" border="0">
								<t:panelGroup>
									<e:outputLabel value="#{msgs['COMMISSIONS']}" for="lesCommissions" />
									<t:outputText value="*" styleClass="etoileForChpObli" />
								</t:panelGroup>
							
								
								<e:selectOneMenu id="lesCommissions"
								converter="javax.faces.Integer"
								value="#{etapeController.paginator.rvd.idCmi}">
								<f:selectItem itemLabel="" itemValue="" />
								<t:selectItems var="commission"
									value="#{etapeController.commissionsItemsByRight}"
									itemLabel="#{commission.libelle}" itemValue="#{commission.id}" />
								</e:selectOneMenu>
								<t:panelGroup>
									<e:outputLabel value="#{msgs['FIELD_LABEL.CGE']}" for="cge" />
									<t:outputText value="*" styleClass="etoileForChpObli" />
								</t:panelGroup>
							
								<e:selectOneMenu id="oneCge"
									rendered="#{etapeController.rightOnCge}"
									value="#{etapeController.paginator.rvd.codCge}">
									<t:selectItems var="centreGestion"
										value="#{etapeController.centreGestion}"
										itemLabel="#{centreGestion.libCge}"
										itemValue="#{centreGestion.codCge}" />
								</e:selectOneMenu>
								<e:selectOneMenu id="cge"
									rendered="#{!etapeController.rightOnCge}"
									value="#{etapeController.paginator.rvd.codCge}">
									<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}" itemValue="" />
									<t:selectItems var="centreGestion"
										value="#{etapeController.CGEItemsByRight}"
										itemLabel="#{centreGestion.libCge}"
										itemValue="#{centreGestion.codCge}" />
								</e:selectOneMenu>
								
								<t:panelGroup>
								</t:panelGroup>
								<t:panelGroup>
								</t:panelGroup>
								
								<e:outputLabel value="#{msgs['FIELD_LABEL.COD_ETP']}" for="codEtp" />
								<e:inputText id="codEtp" size="10" maxlength="10"
									value="#{etapeController.paginator.rvd.codeVet}"/>
									
								<t:panelGroup>
								</t:panelGroup>
								<t:panelGroup>
								</t:panelGroup>
								
								
								<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}" for="libWebVet" />
								<e:inputText id="libWebVet" size="40" maxlength="60"
									value="#{etapeController.paginator.rvd.libWebVet}"/>
								<t:panelGroup />
								<t:panelGroup />
							</e:panelGrid>
						
					</t:div>
				</t:div>
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />


				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:htmlTag value="br" />
				<t:div id="div_subSectionList" styleClass="div_subSection">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FIELD_LABEL.LIST_ETPS']}"
								styleClass="section-smallTitle" rendered="#{not empty etapeController.paginator.visibleItems}"/>
							<t:panelGroup>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									action="#{trtCmiController.addEtapes}" 
									rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.memberCmiValue}"/>
								<e:commandButton value="#{msgs['_.BUTTON.ADD']}"
									action="#{nomenclatureController.addEtapes}" 
									rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.PJValue
									|| etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.affectPJValue}"/>
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									rendered="#{etapeController.rightOneEtp && !etapeController.rightOnCge}"
									immediate="true" action="#{etapeController.goBackSearchEtape}"/>
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:div style="width:100%;">
						<e:dataTable var="versionEtape" id="versionEtapeData" value="#{etapeController.paginator.visibleItems}"
							styleClass="paginatorData" alternateColors="true" rendered="#{not empty etapeController.paginator.visibleItems}">
							<f:facet name="header">
							<h:panelGroup>
								<e:paginator id="paginatorvet"
									paginator="#{paginatorVET}"
									itemsName="#{msgs['FIELD_LABEL.ETAPES']}"
									onchange="javascript:{simulateLinkClick('searchVersEtpForm:versionEtapeData:submitPageSize');}" />
								<e:commandButton id="submitPageSize"
									action="#{paginatorVET.forceReload}" />
							</h:panelGroup>
						</f:facet>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.COD_ETP']}" />
								</f:facet>
								<e:text value="#{versionEtape.codEtp}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.CODE']} #{msgs['FIELD_LABEL.VRS_VET']}" />
								</f:facet>
								<e:text value="#{versionEtape.codVrsVet}" />
							</t:column>
							<t:column>
								<f:facet name="header">
									<t:outputText value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}" />
								</f:facet>
								<e:text value="#{versionEtape.libWebVet}" />
							</t:column>
							<t:column styleClass="buttonTD">
							<e:commandButton image="/media/images/magnifier.png"
								styleClass="form-button-image" immediate="true"
								action="#{etapeController.goEnterVET}"
								title="#{msgs['_.BUTTON.DISPLAY']}"
								rendered="#{managedAccess.addAuthorized}">
								<t:updateActionListener
									value="#{nomenclatureController.actionEnum.updateAction}"
									property="#{nomenclatureController.actionEnum.whatAction}" />
								<t:updateActionListener
									value="#{versionEtape}"
									property="#{nomenclatureController.vetDTO}" />
								<t:updateActionListener
									value="#{etapeController.codeRI}"
									property="#{nomenclatureController.codeRI}" />
								
								
							</e:commandButton>
						</t:column>
						
							
						</e:dataTable>
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
	highlightInputAndSelect('searchMembersForm');
	hideElement('searchVersEtpForm:versionEtapeData:submitPageSize');
</script>
</e:page>
