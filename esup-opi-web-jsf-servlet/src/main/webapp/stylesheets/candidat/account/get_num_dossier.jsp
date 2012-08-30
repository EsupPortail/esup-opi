<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp" %>

	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navInscription.jsp" %>
	</h:form>
	
	
	
	<%@include file="../../_include/_roadMap.jsp"%>
	
	<t:div id="maPage" styleClass="div_body">
		<e:section value="#{msgs['INDIVIDU.INIT.CANDI_NUM_DOS']}" />
		
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		
		<h:form id="getNumDosForm" styleClass="opiR1_form">
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
			<t:div style="padding-left:25%;">
				<t:div styleClass="blockTable" style="width:60%;">
					<t:htmlTag value="br"/>
					<t:htmlTag value="br"/>
					<t:htmlTag value="br"/>
					<t:div id="div_subSection" styleClass="div_subSection">
						<t:div style="text-align:center;">
							<e:commandButton value="#{msgs['_.BUTTON.PAGE.CANDI_ACCUEIL']}"
								style="font-size:18px;" immediate="true"
								action="#{accueilController.goWelcomeCandidat}"/>
						</t:div>
						<t:htmlTag value="br"/>
						<t:div style="text-align:center;">
							<e:text value="#{msgs['INDIVIDU.NUM_DOSSIER.IS']}" styleClass="form-field-label" 
								style="font-size:14px;"/>
						</t:div>
						<t:div style="text-align:center;">
							<e:text value="#{individuController.pojoIndividu.individu.numDossierOpi}" style="font-size:16px;" />
						</t:div>
						<t:htmlTag value="br"/>
						<t:div style="text-align:center;">
							<e:text value="#{msgs['INDIVIDU.SEE_MAIL.INFO']}" styleClass="form-field-label" 
								style="font-size:14px;"/>
						</t:div>
					</t:div>
					<t:htmlTag value="br"/>
					<t:htmlTag value="br"/>
					<t:htmlTag value="br"/>
				</t:div>
			</t:div>
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
			<t:htmlTag value="br"/>
		</h:form>
		<t:htmlTag value="br" />
		
		<t:div styleClass="blockImportant">
			<e:panelGrid columns="2" columnClasses="image,text">
				<t:graphicImage url="/media/images/important.png" />
				<t:outputText value="#{msgs['INFO.CANDIDAT.SAVE']}" />
			</e:panelGrid>
		</t:div>
		<t:htmlTag value="br" />
	</t:div>
	
	
	
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navInscription.jsp" %>
		</h:form>
	</t:div>
	<script type="text/javascript">	
		highlightChildrenLiTags('navigationHeader:navigationLogin');
		highlightChildrenLiTags('navigationFooter:navigationLogin');
	</script>
</e:page>
