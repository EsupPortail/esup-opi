<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" 
	authorized="#{managedAccess.readAuthorized}"
	locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['STAT.MANAGED_SEE']}"/>
	<t:div styleClass="messageForUser">
		<e:messages />
	</t:div>
	
	<h:form id="seeResultStatForm" styleClass="div_subSection">
		<t:div id="resultatStat" styleClass="div_body">
			<t:div styleClass="blockButton">
				<e:commandButton value="#{msgs['_.BUTTON.BACK']}" immediate="true"
					action="#{parametreStatController.goReturnSeeParamStat}"/>
				<e:commandButton value="#{msgs['_.BUTTON.EXPORT']}" 
					action="#{seeStatController.makeCsvStatistique}"/>
				<e:commandButton value="#{msgs['OPINION.EDIT.VALIDATION']}" 
					action="#{seeStatController.printPDFStatistique}"/>
			</t:div>
			<t:htmlTag value="table" styleClass="displayInfo">
				<t:htmlTag value="tr" styleClass="trCampagne">
					<t:htmlTag value="th" styleClass="thCampagne"/>
					<t:dataList value="#{seeStatController.resultCampagne}" var="campagne" layout="simple">
						<e:text value="#{msgs['STAT.RESULT_CAMP_TAB']}" escape="false">
							<f:param value="#{seeStatController.tailleAbscisse}"/>
							<f:param value="#{campagne}"/>
						</e:text>
					</t:dataList>
				</t:htmlTag>
				<t:htmlTag value="tr" styleClass="trAbscisse">
					<t:dataList value="#{seeStatController.resultAbscisse}" var="abscisse" layout="simple">
						<t:htmlTag value="th" styleClass="thAbscisse">
							<h:outputText value="#{abscisse}"/>
						</t:htmlTag>
					</t:dataList>
				</t:htmlTag>
				<t:dataList value="#{seeStatController.resultKey}" var="key" layout="simple">
					<t:htmlTag value="tr">
						<t:htmlTag value="th" styleClass="thOrdonne">
							<h:outputText value="#{key}"/>
						</t:htmlTag>
						<t:dataList value="#{seeStatController.result[key]}" var="result" layout="simple">
							<t:htmlTag value="td">
								<h:outputText value="#{result}"/>
							</t:htmlTag>
						</t:dataList>
					</t:htmlTag>
				</t:dataList>
			</t:htmlTag>
		</t:div>
	</h:form>
	
	<h:form id="navigationFooter" styleClass="opiR1_form">
		<%@include file="../_navigation/_navGestionnaire.jsp"%>
	</h:form>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
	</script>
</e:page>
