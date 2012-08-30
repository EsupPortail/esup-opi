<%@include file="/stylesheets/_include/_include.jsp"%>
<h:form id="navHeaderManager" styleClass="opiR1_form">
	<%@include file="/stylesheets/gestionnaire/_navigation/_navGestionnaire.jsp"%>
</h:form>
<t:div id="maPageGest" styleClass="div_body">
	<t:div id="div_subSectionGest" styleClass="div_subSection">
		<h:form id="accueilGestionnaireForm" styleClass="opiR1_form">
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			
			<%@include file="/stylesheets/gestionnaire/user/_student/_lookForIndividu.jsp"%>

			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<opi:menuDynTag id="menuAccueil" styleClass="displayTraitement">
				<opi:menuDynItems value="#{managedAccess.domainAccueilGest}"
					styleClass="form-button-link" />
			</opi:menuDynTag>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />

		</h:form>
	</t:div>
</t:div>
<t:div id="div_navFooterManager">
	<h:form id="navFooterManager" styleClass="opiR1_form">
		<%@include file="/stylesheets/gestionnaire/_navigation/_navGestionnaire.jsp"%>
	</h:form>
</t:div>
<script type="text/javascript">	
	highlightChildrenLiTags('navigationHeader:navigation');
	highlightChildrenLiTags('navigationFooter:navigation');
	highlightInputAndSelect('accueilGestionnaireForm');
</script>

