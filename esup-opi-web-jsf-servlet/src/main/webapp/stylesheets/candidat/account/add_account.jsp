<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../../_include/_header.jsp"%>

	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navInscription.jsp"%>
	</h:form>
	<%@include file="../../_include/_roadMap.jsp"%>
	<t:div id="maPage" styleClass="div_body">	
		<h:form id="addAccountForm" styleClass="opiR1_form">
			<e:section value="#{msgs['WELCOME.CREATE']}" />
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
				<t:div styleClass="blockForm">
			
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.NEXT']} �"
								action="#{individuController.goAddIndBacOrSituation}" />
						</t:panelGroup>
					</t:div>
					
					<t:div rendered="#{individuController.pojoIndividu.individu.codeEtu == null}">
						<%@include file="_block/_blockEtatCivil.jsp"%>
					</t:div>
					<t:div rendered="#{individuController.pojoIndividu.individu.codeEtu != null}">
						<%@include file="_block/_blockSeeEtatCivil.jsp"%>
					</t:div>
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					
					<%@include file="_block/_blockAdresseFix.jsp"%>
	
					<t:htmlTag value="br" />
					<t:htmlTag value="br" />
					
					
					<t:div styleClass="blockButton">
						<t:panelGroup>
							<e:commandButton value="#{msgs['_.BUTTON.NEXT']} �"
								action="#{individuController.goAddIndBacOrSituation}" />
						</t:panelGroup>
					</t:div>
				</t:div>
			</t:div>
		</h:form>
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navInscription.jsp"%>
		</h:form>
	</t:div>
<script type="text/javascript">	
	highlightChildrenLiTags('navigationHeader:navigationLogin');
	highlightChildrenLiTags('navigationFooter:navigationLogin');
	highlightInputAndSelect('addAccountForm');
	hideElement('addAccountForm:submitSelectCpFix');
	hideElement('addAccountForm:submitSelectCpCurrent');
	hideElement('addAccountForm:submitSelectPay');
	hideElement('addAccountForm:submitSelectPayAdr');
	onblurInput('addAccountForm:codePostalCurrent', 'addAccountForm:submitSelectCpCurrent');
	onblurInput('addAccountForm:codePostalFix', 'addAccountForm:submitSelectCpFix');
</script>
</e:page>
