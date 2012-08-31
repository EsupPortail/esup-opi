<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['FORMATION.TITLE.SEARCH']}" />
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<%@include file="_blockConfirmContinu.jsp"%>
		<%@include file="_blockFormationSelect.jsp"%>
		
		
		<t:div id="div_subSection" styleClass="div_subSection">
			<h:form id="searchFormationForm" styleClass="opiR1_form">
				<t:div id="div_subSection" styleClass="div_subSection">
					<t:div id="blockFormTypeDip" styleClass="blockForm">
						<t:div style="width:100%;">
							<e:text value="#{msgs['FORMATION.ACCESS.TYP_DIPLOME']}"
								styleClass="section-smallTitle" />
						</t:div>
						<t:div styleClass="blockTable">
							<t:div styleClass="blockCenterWidthMax">
								<e:commandButton image="/media/images/LMD-Licence.png"
									rendered="#{formationController.licence != null}"
									immediate="true">
									<t:updateActionListener value="#{formationController.licence}"
										property="#{formationController.searchFormationPojo.groupTypSelected}" />
								</e:commandButton>
								<e:commandButton image="/media/images/LMD-Master.png"
									rendered="#{formationController.master != null}"
									immediate="true">
									<t:updateActionListener value="#{formationController.master}"
										property="#{formationController.searchFormationPojo.groupTypSelected}" />
								</e:commandButton>
								<e:commandButton image="/media/images/LMD-Doctorat.png"
										rendered="#{formationController.doctorat != null}"
										immediate="true">
									<t:updateActionListener value="#{formationController.doctorat}"
										property="#{formationController.searchFormationPojo.groupTypSelected}" />
								</e:commandButton>
							</t:div>
							<t:div styleClass="blockCenterWidthMax">
								<t:htmlTag value="ul" styleClass="listeTypeDiplome">
									<t:dataList var="grpTypDip" value="#{formationController.groupTypeDip}">
										<e:li>
											<e:commandButton value="#{grpTypDip.libGrpTpd}"
												immediate="true"
												styleClass="form-button-link-small">
												<t:updateActionListener value="#{grpTypDip}"
													property="#{formationController.searchFormationPojo.groupTypSelected}" />
											</e:commandButton>
										</e:li>
									</t:dataList>
								</t:htmlTag>
							</t:div>
						</t:div>

					</t:div>
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div id="blockFormMotClef" styleClass="blockForm"
						rendered="#{formationController.searchFormationPojo.groupTypSelected != null}">
						<t:div style="width:100%;">
							<e:text value="#{msgs['FORMATION.ACCESS.KEY_WORD']}" styleClass="section-smallTitle" />
						</t:div>

						<e:panelGrid styleClass="tableWidthMax" columns="2"
							columnClasses="col1UnDemi,col2UnDemi">
							<t:panelGroup>
								<e:outputLabel value="#{msgs['FORMATION.SELECT.KEY_WORD']}" for="motClef" />
							</t:panelGroup>
							<t:panelGroup>
								<e:selectOneMenu
									valueChangeListener="#{formationController.selectKeyWord}"
									value="#{formationController.searchFormationPojo.codKeyWordSelected}"
									onchange="javascript:{simulateLinkClick('searchFormationForm:submitKeyWord');}">
									<f:selectItems value="#{formationController.keyWordItems}" />
								</e:selectOneMenu>
								<e:commandButton id="submitKeyWord" action="#{formationController.selectKeyWord}" />
							</t:panelGroup>
						</e:panelGrid>
					</t:div>
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:div id="blockFormFormation" styleClass="blockForm"
						rendered="#{formationController.searchFormationPojo.versionDiplomes != null 
						&& formationController.searchFormationPojo.versionEtapes == null}">
						<%@include file="_displayFormation.jsp"%>
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
					</t:div>
				</t:div>
			</h:form>
			<%@include file="_selectVrsEtape.jsp"%>
		</t:div>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">
	highlightChildrenLiTags('navigationHeader:navigationLogin');
	highlightChildrenLiTags('navigationFooter:navigationLogin');
	highlightInputAndSelect('searchFormationForm');
	hideButton('searchFormationForm:submitKeyWord');
	hideButton('searchFormationForm:submitTypeDiplome');
</script>
</e:page>
