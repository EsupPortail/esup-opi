<%@include file="../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	<%@include file="../_include/_headerException.jsp" %>

	<h:form id="navigationHeader">
		<%@include file="../_include/_navigationException.jsp" %>
	</h:form>
	<t:div id="maPage" styleClass="div_body">
		<t:div styleClass="messageForUser">
			<e:messages />
		</t:div>
		<h:form id="exceptionForm" styleClass="opiR1_form">
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
								<e:text value="#{msgs['EXCEPTION.EXPORT.TITLE']}" styleClass="form-field-label" 
										style="font-size:18px;"/>
						</t:div>
						<t:div style="text-align:center;">
							<e:text value="#{msgs['EXCEPTION.EXPORT.EXPLAIN.1']}" style="font-size:22px;" />
							<e:text value="#{msgs['EXCEPTION.EXPORT.EXPLAIN.2']}" style="font-size:22px;" />
						</t:div>
						<t:htmlTag value="br"/>
						<t:div style="text-align:center;">
							<e:commandButton value="#{msgs['_.BUTTON.BACK.WELCOME']}"
								immediate="true"
								action="#{sessionController.restart}"/>
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
		<t:htmlTag value="br" />
	</t:div>
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_include/_navigationException.jsp" %>
		</h:form>
	</t:div>
	<script type="text/javascript">	
		highlightChildrenLiTags('navigationHeader:navigation');
		highlightChildrenLiTags('navigationFooter:navigation');
	</script>
</e:page>
