<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" menuItem="accueil"
	locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['COMMISSION.SEARCH_ETP.TITLE']}" 
		rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.memberCmiValue}"/>
		<e:section value="#{msgs['PJ.SEARCH_ETP.TITLE']}" 
		rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.PJValue
		|| etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.affectPJValue}"/>
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
									action="#{etapeController.searchEtape}" />
								<e:commandButton value="#{msgs['_.BUTTON.BACK']}"
									immediate="true" action="#{etapeController.goBackSearchEtape}"/>
							</t:panelGroup>
						</t:div>
						
							<e:panelGrid styleClass="tableWidthMax" columns="4"
								columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
								<t:panelGroup>
									<e:outputLabel value="#{msgs['FIELD_LABEL.CAMPAGNES']}" for="campagnes" />
									<t:outputText value="*" styleClass="etoileForChpObli" />
								</t:panelGroup>
							
								<e:selectOneMenu id="campagnes"
									value="#{etapeController.codAnu}">
									<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}" itemValue="" />
									<t:selectItems var="campagne"
										value="#{etapeController.campagnes}"
										itemLabel="#{campagne.code} - #{campagne.libelle}"
										itemValue="#{campagne.codAnu}" />
								</e:selectOneMenu>
								<t:panelGroup>
									<e:outputLabel value="#{msgs['FIELD_LABEL.CGE']}" for="cge" />
									<t:outputText value="*" styleClass="etoileForChpObli" />
								</t:panelGroup>
							
								<e:selectOneMenu id="oneCge"
									rendered="#{etapeController.rightOnCge}"
									value="#{etapeController.codCge}">
									<t:selectItems var="centreGestion"
										value="#{etapeController.centreGestion}"
										itemLabel="#{centreGestion.libCge}"
										itemValue="#{centreGestion.codCge}" />
								</e:selectOneMenu>
								<e:selectOneMenu id="cge"
									rendered="#{!etapeController.rightOnCge}"
									value="#{etapeController.codCge}">
									<f:selectItem itemLabel="#{msgs['FIELD_LABEL.SELECT']}" itemValue="" />
									<t:selectItems var="centreGestion"
										value="#{etapeController.centreGestion}"
										itemLabel="#{centreGestion.libCge}"
										itemValue="#{centreGestion.codCge}" />
								</e:selectOneMenu>
								<e:outputLabel value="#{msgs['FIELD_LABEL.COD_ETP']}" for="codEtp" />
								<e:inputText id="codEtp" size="10" maxlength="10"
									value="#{etapeController.codEtp}"/>
								
								
								<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']} #{msgs['FIELD_LABEL.VRS_VET']}" for="libWebVet" />
								<e:inputText id="libWebVet" size="40" maxlength="60"
									value="#{etapeController.libWebVet}"/>
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
				<t:div id="div_subSectionList" styleClass="div_subSection"
					rendered="#{not empty etapeController.etapes}">
					<t:div style="width:100%;">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['FIELD_LABEL.LIST_ETPS']}"
								styleClass="section-smallTitle" />
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
						<e:dataTable var="versionEtape" id="versionEtapeData"
							value="#{etapeController.etapes}"
							styleClass="paginatorData" alternateColors="true">
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
							<t:column>
								<f:facet name="header">
									<t:selectBooleanCheckbox value="#{etapeController.allChecked}" 
									onclick="checkAllInElement('searchVersEtpForm:versionEtapeData' ,this.checked)" />
								</f:facet>
								<jdt:multipleRowsSelector
									selectionList="#{etapeController.objectToAdd}" 
									rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.memberCmiValue}"/>
								<jdt:multipleRowsSelector
									selectionList="#{nomenclatureController.objectToAdd}" 
									rendered="#{etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.PJValue
									|| etapeController.wayfEnum.whereAreYouFrom == etapeController.wayfEnum.affectPJValue}"/>
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
</script>
</e:page>
