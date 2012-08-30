<%@include file="_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="_include/_navigation.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		
		<e:section value="#{msgs['LOGIN_CANDIDAT.TITLE.ID']}" />
		<e:subSection value="#{msgs['ETUDIANT']} :" />
		<h:form id="loginCandidatForm" styleClass="opiR1_form">
			<t:div id="div_subSection" styleClass="div_subSection">
				<e:paragraph value="#{msgs['LOGIN_CANDIDAT.ETU_NOT_R1']}" escape="false" />
				<t:div styleClass="messageForUser">
					<e:messages />
				</t:div>
				<t:div id="blockForm" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['WELCOME.TITLE.HAVE_NUM_DOS']}"
								styleClass="section-smallTitle" />
							<e:commandButton value="#{msgs['NAVIGATION.TEXT.LOGIN']}"
								action="#{accueilController.goLoginCandidat}" />
						</e:panelGrid>

					</t:div>
					<e:panelGrid styleClass="tableWidthMax" columns="4"
						columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
						<e:outputLabel value="#{msgs['INDIVIDU.NUM_DOSSIER']}" for="numDossier" />
						<e:inputText id="numDossier" size="12" maxlength="8" 
							value="#{sessionController.numDossier}" />
						<e:outputLabel value="#{msgs['INDIVIDU.DATE_NAI']}" for="dateNaissance" />
						<e:inputText id="dateNaissance" size="10" maxlength="8" 
							value="#{sessionController.dateNaissance}">
							<f:convertDateTime pattern="ddMMyyyy" timeZone="#{sessionController.timezone}"/>
							<f:validateLength minimum="8"/>
						</e:inputText>
					</e:panelGrid>
				</t:div>
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
		</h:form>
		<h:form id="welcomeCandidatForm" styleClass="opiR1_form">
			<t:div id="div_subSection" styleClass="div_subSection">
				<t:div id="blockForm" styleClass="blockForm">
					<t:div styleClass="blockButton">
						<e:panelGrid columns="2" columnClasses="col1UnDemi,col2UnDemi"
							styleClass="tableJustWidthMax">
							<e:text value="#{msgs['WELCOME.TITLE.INIT_CANDIDAT']}"
								styleClass="section-smallTitle" />
							<e:commandButton value="#{msgs['_.BUTTON.GET.NUM_DOS']}"
								action="#{individuController.goSearchEtuR1}" />
						</e:panelGrid>

					</t:div>
					<t:div style="text-align:center;" styleClass="blockTable">
						<e:text value="#{msgs['LOGIN_CANDIDAT.GET_NUM_DOS']}" />
					</t:div>
				</t:div>
			</t:div>
			
		</h:form>
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
		<t:div styleClass="blockImportant">
			<e:panelGrid columns="2" columnClasses="image,text">
				<t:graphicImage url="/media/images/important.png" />
				<t:outputText value="#{msgs['INFO.CANDIDAT.RENNES1']}"
					escape="false" />
			</e:panelGrid>
		</t:div>
		<t:htmlTag value="br" />
		<t:htmlTag value="br" />
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="_include/_navigation.jsp"%>
		</h:form>
	</t:div>
	<script type="text/javascript">	
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	highlightInputAndSelect('welcomeCandidatForm');
	highlightInputAndSelect('loginCandidatForm');
</script>
</e:page>
