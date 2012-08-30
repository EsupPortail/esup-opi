<%@include file="_include/_include.jsp"%>
<e:page menuItem="acccueil">
	<t:div id="accueilGest" rendered="#{sessionController.whoIsConnectInPortlet == 'manager'}">
		<%@include file="gestionnaire/_block/_accueil.jsp"%>
	</t:div>
	<t:div id="accueilCandidat" rendered="#{sessionController.whoIsConnectInPortlet == 'individu.exist'}">
		<%@include file="candidat/_block/_accueil.jsp"%>
	</t:div>
	<t:div id="lookForEtu" rendered="#{sessionController.whoIsConnectInPortlet == 'individu.not_exist'}">
		<%@include file="candidat/_block/_lookforEtu.jsp"%>
	</t:div>
</e:page>