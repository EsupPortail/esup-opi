<%@include file="../../_include/_include.jsp"%>
<t:div style="width:100%;">
	<e:text value="#{msgs['FORMATION.LIST_DIPLOMES']}" styleClass="section-smallTitle" />
</t:div>
<t:div id="blockFormVdi" styleClass="blockForm">
	<t:dataList var="versionDiplome" value="#{formationController.searchFormationPojo.versionDiplomes}" layout="unorderedList">
		<e:commandButton value="#{versionDiplome.libWebVdi}" styleClass="form-button-link"
				action="#{formationController.selectEtape}">
			<t:updateActionListener value="#{versionDiplome}" 
						property="#{formationController.searchFormationPojo.vrsDipSelected}"/>
		</e:commandButton>
	</t:dataList>
	<t:div id="div_sectionEtpEmpty"
	rendered="#{empty formationController.searchFormationPojo.versionDiplomes
	&& formationController.searchFormationPojo.versionDiplomes != null}">
	<t:div id="blockFormEtpEmpty" styleClass="blockForm">
		<t:div styleClass="blockTable">
			<e:text escape="false"
				rendered="#{!sessionController.currentInd.regimeInscription.displayInfoFC}"
				value="#{msgs['FORMATION.DIP.NOT_EXIST.IN_CMI']}" >
			</e:text>
			<e:text escape="false"
				rendered="#{sessionController.currentInd.regimeInscription.displayInfoFC}"
				value="#{msgs['FORMATION.DIP.NOT_EXIST.IN_CMI_FC']}" >
			</e:text>
		</t:div>
	</t:div>
</t:div>
</t:div>

