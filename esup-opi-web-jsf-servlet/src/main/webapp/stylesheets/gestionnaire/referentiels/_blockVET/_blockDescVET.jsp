<%@include file="../../../_include/_include.jsp"%>
<e:panelGrid styleClass="tableWidthMax" columns="4"
	columnClasses="colUnQuart,colUnQuart,colUnQuart,colUnQuart">
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.CODE_VET']}" for="code" />
		
	</t:panelGroup>
	<t:panelGroup>
		<e:text id="code" value="#{nomenclatureController.vetDTO.codEtp}"/>
		
	</t:panelGroup>
	<t:panelGroup>
		
	</t:panelGroup>
	<t:panelGroup>
		
	</t:panelGroup>
	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.LIBELLE']}" for="libelle" />
	</t:panelGroup>
	<t:panelGroup>
		<e:text id="libelle"
			value="#{nomenclatureController.vetDTO.libWebVet}"  />
	</t:panelGroup>
	<t:panelGroup>
		
	</t:panelGroup>
	<t:panelGroup>
		
	</t:panelGroup>

	<t:panelGroup>
		<e:outputLabel value="#{msgs['FIELD_LABEL.REGIME_INSCRIPTION']}"
			for="regimeInscription" />
		<t:outputText value="*" styleClass="etoileForChpObli" />
	</t:panelGroup>
	<t:panelGroup>
		<e:text id="ri"
			value="#{nomenclatureController.regimeInscription}"  />
	</t:panelGroup>
	<t:panelGroup />
	<t:panelGroup />
</e:panelGrid>