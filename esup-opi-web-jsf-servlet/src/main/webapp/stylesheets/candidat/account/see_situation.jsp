<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<h:form id="addSituationForm" styleClass="opiR1_form">
			<e:section value="#{msgs['INDIVIDU.SITUATION']}" />
			<t:div styleClass="messageForUser">
				<e:messages />
			</t:div>

			<t:div id="div_subSection" styleClass="div_subSection">
				
				<%@include file="_block/_blockSituation.jsp"%>

				<t:htmlTag value="br" />
				<t:htmlTag value="br" />

			</t:div>
		</h:form>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
<script type="text/javascript">	
	highlightChildrenLiTags('navigationHeader:navigationLogin');
	highlightChildrenLiTags('navigationFooter:navigationLogin');
	highlightInputAndSelect('addSituationForm');
	hideElement('addSituationForm:submitSelectType');
	hideElement('addSituationForm:submitSelectPayAdr');
	hideElement('addSituationForm:submitSelectCpEmpl');
	onblurInput('addSituationForm:codePostalEmpl', 'addSituationForm:submitSelectCpEmpl');
</script>
</e:page>
