<%@include file="../../_include/_include.jsp"%>
<e:page stringsVar="msgs" locale="#{sessionController.locale}">
	
	<%@include file="../../_include/_header.jsp"%>
	
	<h:form id="navigationHeader">
		<%@include file="../_navigation/_navGlobal.jsp"%>
	</h:form>
	
	<e:section value="#{msgs['CALENDAR_RDV.ACCUEIL.TITRE_RDV']}"/>
	
	<e:messages styleClass="messageForUser"/>
	
	<e:dataTable var="dateRdv"
		value="#{saisieRdvEtuController.keyMapAllRdvEtu}"
		alternateColors="false" renderedIfEmpty="false"
		styleClass="displayInfo">
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.DATE']}"/>
			</f:facet>
			<e:text value="#{dateRdv}">
				<f:convertDateTime pattern="dd/MM/yyyy HH:mm"
				timeZone="#{sessionController.timezone}" />
			</e:text>
		</t:column>
		
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.MESSAGE']}"/>
			</f:facet>
			<e:text escape="false" value="#{saisieRdvEtuController.allRdvEtu[dateRdv][0].messageRdv}"/>
		</t:column>
		
		<t:column>
			<f:facet name="header">
				<t:outputText value="#{msgs['CALENDAR_RDV.ACCUEIL.TITRE_VOEU']}"/>
			</f:facet>
			<t:dataList var="individuDatePojo" value="#{saisieRdvEtuController.allRdvEtu[dateRdv]}">
				<e:text value="#{individuDatePojo.voeuRdv.vrsEtape.libWebVet}"/>
			</t:dataList>
		</t:column>
	</e:dataTable>
	
	<t:div id="div_navigationFooter">
		<h:form id="navigationFooter" styleClass="opiR1_form">
			<%@include file="../_navigation/_navGlobal.jsp"%>
		</h:form>
	</t:div>
	
	<script type="text/javascript">
		highlightChildrenLiTags('navigationHeader:navigationLogin');
		highlightChildrenLiTags('navigationFooter:navigationLogin');
		highlightInputAndSelect('seeVoeuxForm');
	</script>
</e:page>
