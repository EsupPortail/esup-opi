<%@include file="../../_include/_include.jsp"%>
<h:form id="navHeaderSearchEtu" styleClass="opiR1_form">
	<%@include file="/stylesheets/candidat/_navigation/_navGlobal.jsp"%>
</h:form>
<t:div id="maPageSearchEtu" styleClass="div_body">

	<t:div styleClass="messageForUser">
		<e:messages />
	</t:div>
	<t:htmlTag value="br" />
	<t:htmlTag value="br" />
	<t:div id="div_subSectionSearchEtu" styleClass="div_subSection">
		<h:form id="searchEtuR1Form" styleClass="opiR1_form">
			<t:htmlTag value="br" />
			<t:div styleClass="blockImportant">
				<e:panelGrid columns="2" columnClasses="image,text">
					<t:graphicImage url="/media/images/important.png" />
					<t:outputText
						value="#{msgs['INFO.CANDIDAT.FIND.ETU_R1.IN_ENT']}"
						escape="false" />
				</e:panelGrid>
			</t:div>
			<t:htmlTag value="br" />
			<t:div style="padding-left:25%;">
				<t:div styleClass="blockTable" style="width:60%;">
					<t:div id="blockFormSearchEtuR1">
						<t:div>
							<e:text value="#{msgs['INDIVIDU.VERIF.COD_ETU']} :"
								styleClass="form-field-label" style="font-size:18px;" />
						</t:div>

						<t:div styleClass="containerPopup"
							style="width:60%;margin-left:20%;">
							<h:panelGrid columns="1">
								<t:panelGroup>
									<t:outputText styleClass="libellesAide"
										value="#{msgs['INFO.CANDIDAT.CREATE_NUM_DOS']}" />
								</t:panelGroup>
							</h:panelGrid>

						</t:div>
						<t:htmlTag value="br" />
						<t:div style="text-align:center;">
							<t:panelGroup>
								<t:panelGroup>
									<e:outputLabel value="#{msgs['INDIVIDU.NUM_ETU']} :   " for="email" />
								</t:panelGroup>
								<e:inputText id="email" value="#{sessionController.codEtu}"
									size="10" maxlength="8" />
							</t:panelGroup>
						</t:div>
						<t:div style="text-align:center;">
							<e:text value="#{msgs['INFO.CANDIDAT.LOOK_FOR.INE']}"
								styleClass="span-text-small" />
						</t:div>
						<t:htmlTag value="br" />
						<t:htmlTag value="br" />
						<t:div style="text-align:center;">
							<e:commandButton value="#{msgs['_.BUTTON.VALIDATE']}"
								action="#{individuController.goFindEtuR1}" />
						</t:div>
					</t:div>
					<t:htmlTag value="br" />
				</t:div>
			</t:div>
			<t:htmlTag value="br" />
			<t:htmlTag value="br" />
		</h:form>
	</t:div>






</t:div>
<t:div id="div_navFooterSearchEtu">
	<h:form id="navFooterSearchEtu" styleClass="opiR1_form">
		<%@include file="/stylesheets/candidat/_navigation/_navGlobal.jsp"%>
	</h:form>
</t:div>
<script type="text/javascript">
	highlightChildrenLiTags('navHeaderSearchEtu:navigationLogin');
	highlightChildrenLiTags('navFooterSearchEtu:navigationLogin');
	highlightInputAndSelect('searchEtuR1Form');
	highlightInputAndSelect('navFooterSearchEtu');
	highlightInputAndSelect('navHeaderSearchEtu');
</script>